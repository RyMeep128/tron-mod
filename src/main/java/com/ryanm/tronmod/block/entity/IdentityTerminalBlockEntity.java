package com.ryanm.tronmod.block.entity;

import com.ryanm.tronmod.menu.IdentityTerminalMenu;
import com.ryanm.tronmod.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class IdentityTerminalBlockEntity extends BlockEntity implements MenuProvider {
    public IdentityTerminalBlockEntity(BlockPos pos, BlockState state) { super(ModBlockEntities.IDENTITY_TERMINAL.get(),pos,state); }
    @Override public Component getDisplayName() { return Component.translatable("container.tronmod.identity_terminal"); }
    @Override public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) { return new IdentityTerminalMenu(id,inventory,ContainerLevelAccess.create(this.level,this.worldPosition)); }
}
