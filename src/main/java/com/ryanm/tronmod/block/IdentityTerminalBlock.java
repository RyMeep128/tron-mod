package com.ryanm.tronmod.block;

import com.mojang.serialization.MapCodec;
import com.ryanm.tronmod.component.DiscPrograms;
import com.ryanm.tronmod.item.ProtocolItem;
import com.ryanm.tronmod.registry.ModDataComponents;
import com.ryanm.tronmod.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public final class IdentityTerminalBlock extends Block {
    public static final MapCodec<IdentityTerminalBlock> CODEC = simpleCodec(IdentityTerminalBlock::new);

    public IdentityTerminalBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends IdentityTerminalBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack disk,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult
    ) {
        if (!disk.has(ModDataComponents.DISC_IDENTITY.get())) {
            return InteractionResult.PASS;
        }
        ItemStack protocolStack = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
        if (!(protocolStack.getItem() instanceof ProtocolItem protocol)) {
            if (!level.isClientSide()) {
                player.sendOverlayMessage(Component.translatable("message.tronmod.terminal.protocol_required"));
            }
            return InteractionResult.FAIL;
        }

        DiscPrograms programs = disk.getOrDefault(ModDataComponents.DISC_PROGRAMS.get(), DiscPrograms.EMPTY);
        int currentLevel = programs.level(protocol.program());
        if (currentLevel >= DiscPrograms.MAX_LEVEL) {
            if (!level.isClientSide()) {
                player.sendOverlayMessage(Component.translatable("message.tronmod.terminal.max_level"));
            }
            return InteractionResult.FAIL;
        }

        int cost = currentLevel + 1;
        if (countShards(player.getInventory()) < cost) {
            if (!level.isClientSide()) {
                player.sendOverlayMessage(Component.translatable("message.tronmod.terminal.shards_required", cost));
            }
            return InteractionResult.FAIL;
        }
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        consumeShards(player.getInventory(), cost);
        disk.set(ModDataComponents.DISC_PROGRAMS.get(), programs.upgrade(protocol.program()));
        if (!player.hasInfiniteMaterials()) {
            protocolStack.shrink(1);
        }
        level.playSound(null, pos, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 1.0F, 1.4F);
        player.sendOverlayMessage(Component.translatable(
                "message.tronmod.terminal.installed",
                Component.translatable("program.tronmod." + protocol.program().getSerializedName()),
                currentLevel + 1
        ));
        return InteractionResult.SUCCESS_SERVER;
    }

    private static int countShards(Inventory inventory) {
        int count = 0;
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (stack.is(ModItems.GRID_SHARD.get())) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private static void consumeShards(Inventory inventory, int amount) {
        for (int slot = 0; slot < inventory.getContainerSize() && amount > 0; slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (stack.is(ModItems.GRID_SHARD.get())) {
                int consumed = Math.min(amount, stack.getCount());
                stack.shrink(consumed);
                amount -= consumed;
            }
        }
        inventory.setChanged();
    }
}
