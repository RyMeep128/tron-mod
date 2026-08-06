package com.ryanm.tronmod.menu;

import com.ryanm.tronmod.block.IdentityTerminalBlock;
import com.ryanm.tronmod.registry.ModBlocks;
import com.ryanm.tronmod.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public final class IdentityTerminalMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    public IdentityTerminalMenu(int id, Inventory inventory) { this(id,inventory,ContainerLevelAccess.NULL); }
    public IdentityTerminalMenu(int id, Inventory inventory, ContainerLevelAccess access) { super(ModMenus.IDENTITY_TERMINAL.get(),id); this.access=access; this.addStandardInventorySlots(inventory,8,84); }
    @Override public boolean stillValid(Player player) { return stillValid(this.access,player,ModBlocks.IDENTITY_TERMINAL.get()); }
    @Override public boolean clickMenuButton(Player player,int id) { if(id!=0&&id!=1)return false; this.access.execute((level,pos)->IdentityTerminalBlock.runProgramAction(player,level,pos,id==1)); return true; }
    @Override public ItemStack quickMoveStack(Player player,int index) { if(index<0||index>=this.slots.size())return ItemStack.EMPTY; var slot=this.slots.get(index); if(!slot.hasItem())return ItemStack.EMPTY; ItemStack copy=slot.getItem().copy(); int mainEnd=27; if(index<mainEnd){if(!this.moveItemStackTo(slot.getItem(),mainEnd,36,false))return ItemStack.EMPTY;}else if(!this.moveItemStackTo(slot.getItem(),0,mainEnd,false))return ItemStack.EMPTY; if(slot.getItem().isEmpty())slot.setByPlayer(ItemStack.EMPTY);else slot.setChanged(); return copy; }
}
