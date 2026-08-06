package com.ryanm.tronmod.block;

import com.mojang.serialization.MapCodec;
import com.ryanm.tronmod.block.entity.IdentityTerminalBlockEntity;
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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public final class IdentityTerminalBlock extends BaseEntityBlock {
    public static final MapCodec<IdentityTerminalBlock> CODEC = simpleCodec(IdentityTerminalBlock::new);
    public IdentityTerminalBlock(BlockBehaviour.Properties properties) { super(properties); }
    @Override public MapCodec<? extends IdentityTerminalBlock> codec() { return CODEC; }
    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new IdentityTerminalBlockEntity(pos, state); }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return open(level, pos, player);
    }
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        return open(level, pos, player);
    }
    private static InteractionResult open(Level level, BlockPos pos, Player player) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof IdentityTerminalBlockEntity terminal) player.openMenu(terminal);
        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }

    public static boolean runProgramAction(Player player, Level level, BlockPos pos, boolean remove) {
        ItemStack disk = player.getMainHandItem().has(ModDataComponents.DISC_IDENTITY.get()) ? player.getMainHandItem() : player.getOffhandItem();
        ItemStack protocolStack = player.getMainHandItem().getItem() instanceof ProtocolItem ? player.getMainHandItem() : player.getOffhandItem();
        if (!disk.has(ModDataComponents.DISC_IDENTITY.get()) || !(protocolStack.getItem() instanceof ProtocolItem protocol)) return false;
        DiscPrograms programs=disk.getOrDefault(ModDataComponents.DISC_PROGRAMS.get(),DiscPrograms.EMPTY);
        int current=programs.level(protocol.program());
        if (!programs.compatible(protocol.program())) { player.sendOverlayMessage(Component.translatable("message.tronmod.terminal.incompatible")); return false; }
        int cost=remove?1:current+1;
        if ((!remove && current>=DiscPrograms.MAX_LEVEL) || (remove && current==0) || countShards(player.getInventory())<cost) return false;
        consumeShards(player.getInventory(),cost);
        disk.set(ModDataComponents.DISC_PROGRAMS.get(),remove?programs.remove(protocol.program()):programs.upgrade(protocol.program()));
        if (!remove && !player.hasInfiniteMaterials()) protocolStack.shrink(1);
        level.playSound(null,pos,remove?SoundEvents.BEACON_DEACTIVATE:SoundEvents.BEACON_POWER_SELECT,SoundSource.BLOCKS,1,remove?1.2F:1.4F);
        Component programName = Component.translatable("program.tronmod." + protocol.program().getSerializedName());
        player.sendOverlayMessage(remove
                ? Component.translatable("message.tronmod.terminal.removed", programName)
                : Component.translatable("message.tronmod.terminal.installed", programName, current + 1));
        return true;
    }
    private static int countShards(Inventory inventory) { int n=0; for(int i=0;i<inventory.getContainerSize();i++) if(inventory.getItem(i).is(ModItems.GRID_SHARD.get())) n+=inventory.getItem(i).getCount(); return n; }
    private static void consumeShards(Inventory inventory,int amount) { for(int i=0;i<inventory.getContainerSize()&&amount>0;i++){ItemStack s=inventory.getItem(i);if(s.is(ModItems.GRID_SHARD.get())){int n=Math.min(amount,s.getCount());s.shrink(n);amount-=n;}} inventory.setChanged(); }
}
