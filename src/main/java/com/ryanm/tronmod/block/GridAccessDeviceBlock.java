package com.ryanm.tronmod.block;

import com.mojang.serialization.MapCodec;
import com.ryanm.tronmod.registry.ModDataComponents;
import com.ryanm.tronmod.registry.ModBlocks;
import com.ryanm.tronmod.world.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class GridAccessDeviceBlock extends Block {
    public static final MapCodec<GridAccessDeviceBlock> CODEC = simpleCodec(GridAccessDeviceBlock::new);
    private static final BlockPos GRID_ARRIVAL = new BlockPos(0, 65, 0);

    public GridAccessDeviceBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends GridAccessDeviceBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult
    ) {
        if (!stack.has(ModDataComponents.DISC_IDENTITY.get())) {
            if (!level.isClientSide()) {
                player.sendOverlayMessage(Component.translatable("message.tronmod.grid.requires_bound_disk"));
            }
            return InteractionResult.FAIL;
        }
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if (!(level instanceof ServerLevel currentLevel) || !(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.PASS;
        }

        boolean leavingGrid = currentLevel.dimension().equals(ModDimensions.GRID);
        ServerLevel destination = currentLevel.getServer().getLevel(leavingGrid ? Level.OVERWORLD : ModDimensions.GRID);
        if (destination == null) {
            player.sendOverlayMessage(Component.translatable("message.tronmod.grid.unavailable"));
            return InteractionResult.FAIL;
        }

        BlockPos arrival = leavingGrid
                ? destination.getRespawnData().pos().above()
                : GRID_ARRIVAL;
        prepareArrival(destination, arrival);
        serverPlayer.teleport(new TeleportTransition(
                destination,
                arrival.getCenter(),
                Vec3.ZERO,
                serverPlayer.getYRot(),
                serverPlayer.getXRot(),
                TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET)
        ));
        return InteractionResult.SUCCESS_SERVER;
    }

    private static void prepareArrival(ServerLevel level, BlockPos arrival) {
        BlockPos floor = arrival.below();
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                level.setBlockAndUpdate(floor.offset(x, 0, z), Blocks.BLACK_CONCRETE.defaultBlockState());
                for (int y = 0; y <= 2; y++) {
                    level.setBlockAndUpdate(arrival.offset(x, y, z), Blocks.AIR.defaultBlockState());
                }
            }
        }
        level.setBlockAndUpdate(floor.offset(0, 0, 2), ModBlocks.GRID_ACCESS_DEVICE.get().defaultBlockState());
        level.playSound(null, arrival, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0F, 1.35F);
    }
}
