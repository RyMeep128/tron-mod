package com.ryanm.tronmod.menu;

import com.ryanm.tronmod.block.IdentityTerminalBlock;
import com.ryanm.tronmod.block.entity.IdentityTerminalBlockEntity;
import com.ryanm.tronmod.item.ProtocolItem;
import com.ryanm.tronmod.registry.ModBlocks;
import com.ryanm.tronmod.registry.ModDataComponents;
import com.ryanm.tronmod.registry.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public final class IdentityTerminalMenu extends AbstractContainerMenu {
    private static final int MACHINE_SLOTS = 3;
    private final ContainerLevelAccess access;
    private final Container terminal;

    public IdentityTerminalMenu(int id, Inventory inventory) {
        this(id, inventory, new SimpleContainer(MACHINE_SLOTS), ContainerLevelAccess.NULL);
    }

    public IdentityTerminalMenu(int id, Inventory inventory, IdentityTerminalBlockEntity terminal) {
        this(id, inventory, terminal, ContainerLevelAccess.create(terminal.getLevel(), terminal.getBlockPos()));
    }

    private IdentityTerminalMenu(int id, Inventory inventory, Container terminal, ContainerLevelAccess access) {
        super(ModMenus.IDENTITY_TERMINAL.get(), id);
        checkContainerSize(terminal, MACHINE_SLOTS);
        this.terminal = terminal;
        this.access = access;
        terminal.startOpen(inventory.player);
        this.addSlot(new Slot(terminal, IdentityTerminalBlockEntity.SLOT_DISC, 26, 43) {
            @Override public boolean mayPlace(ItemStack stack) { return stack.has(ModDataComponents.DISC_IDENTITY.get()); }
            @Override public int getMaxStackSize() { return 1; }
        });
        this.addSlot(new Slot(terminal, IdentityTerminalBlockEntity.SLOT_PROTOCOL, 76, 43) {
            @Override public boolean mayPlace(ItemStack stack) { return stack.getItem() instanceof ProtocolItem; }
            @Override public int getMaxStackSize() { return 1; }
        });
        this.addSlot(new Slot(terminal, IdentityTerminalBlockEntity.SLOT_OUTPUT, 134, 43) {
            @Override public boolean mayPlace(ItemStack stack) { return false; }
        });
        this.addStandardInventorySlots(inventory, 8, 84);
    }

    @Override public boolean stillValid(Player player) { return stillValid(this.access, player, ModBlocks.IDENTITY_TERMINAL.get()); }

    @Override public boolean clickMenuButton(Player player, int id) {
        if (id != 0 && id != 1) return false;
        this.access.execute((level, pos) -> IdentityTerminalBlock.runProgramAction(player, level, pos, this.terminal, id == 1));
        return true;
    }

    @Override public ItemStack quickMoveStack(Player player, int index) {
        if (index < 0 || index >= this.slots.size()) return ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack source = slot.getItem();
        ItemStack copy = source.copy();
        if (index < MACHINE_SLOTS) {
            if (!this.moveItemStackTo(source, MACHINE_SLOTS, this.slots.size(), true)) return ItemStack.EMPTY;
        } else if (source.has(ModDataComponents.DISC_IDENTITY.get())) {
            if (!this.moveItemStackTo(source, 0, 1, false)) return ItemStack.EMPTY;
        } else if (source.getItem() instanceof ProtocolItem) {
            if (!this.moveItemStackTo(source, 1, 2, false)) return ItemStack.EMPTY;
        } else {
            return ItemStack.EMPTY;
        }
        if (source.isEmpty()) slot.setByPlayer(ItemStack.EMPTY); else slot.setChanged();
        return copy;
    }

    @Override public void removed(Player player) {
        super.removed(player);
        this.terminal.stopOpen(player);
    }
}
