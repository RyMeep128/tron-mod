package com.ryanm.tronmod.block.entity;

import com.ryanm.tronmod.menu.IdentityTerminalMenu;
import com.ryanm.tronmod.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class IdentityTerminalBlockEntity extends BaseContainerBlockEntity {
    public static final int SLOT_DISC = 0;
    public static final int SLOT_PROTOCOL = 1;
    public static final int SLOT_OUTPUT = 2;
    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    public IdentityTerminalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.IDENTITY_TERMINAL.get(), pos, state);
    }

    @Override public int getContainerSize() { return this.items.size(); }
    @Override protected NonNullList<ItemStack> getItems() { return this.items; }
    @Override protected void setItems(NonNullList<ItemStack> items) { this.items = items; }
    @Override protected Component getDefaultName() { return Component.translatable("container.tronmod.identity_terminal"); }
    @Override protected AbstractContainerMenu createMenu(int id, Inventory inventory) { return new IdentityTerminalMenu(id, inventory, this); }
}
