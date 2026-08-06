package com.ryanm.tronmod.client.screen;

import com.ryanm.tronmod.menu.IdentityTerminalMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class IdentityTerminalScreen extends AbstractContainerScreen<IdentityTerminalMenu> {
    public IdentityTerminalScreen(IdentityTerminalMenu menu, Inventory inventory, Component title){super(menu,inventory,title);}
    @Override public void extractBackground(GuiGraphicsExtractor graphics,int mouseX,int mouseY,float partialTick){
        super.extractBackground(graphics,mouseX,mouseY,partialTick);
        graphics.fill(this.leftPos,this.topPos,this.leftPos+this.imageWidth,this.topPos+this.imageHeight,0xEE061116);
        graphics.fill(this.leftPos+8,this.topPos+18,this.leftPos+168,this.topPos+76,0xFF092B35);
        graphics.text(this.font,Component.translatable("screen.tronmod.terminal.instructions"),this.leftPos+14,this.topPos+25,0xFF7DEBFF);
        drawButton(graphics,0,this.leftPos+18,this.topPos+48,62,20,Component.translatable("screen.tronmod.terminal.install"));
        drawButton(graphics,1,this.leftPos+96,this.topPos+48,62,20,Component.translatable("screen.tronmod.terminal.remove"));
    }
    private void drawButton(GuiGraphicsExtractor graphics,int id,int x,int y,int w,int h,Component label){graphics.fill(x,y,x+w,y+h,id==0?0xFF087F8C:0xFF59405E);graphics.text(this.font,label,x+8,y+6,0xFFFFFFFF);}
    @Override public boolean mouseClicked(MouseButtonEvent event,boolean doubleClick){
        int id=event.x()>=this.leftPos+18&&event.x()<this.leftPos+80&&event.y()>=this.topPos+48&&event.y()<this.topPos+68?0:
                event.x()>=this.leftPos+96&&event.x()<this.leftPos+158&&event.y()>=this.topPos+48&&event.y()<this.topPos+68?1:-1;
        if(id>=0&&this.menu.clickMenuButton(this.minecraft.player,id)){Minecraft.getInstance().gameMode.handleInventoryButtonClick(this.menu.containerId,id);return true;}
        return super.mouseClicked(event,doubleClick);
    }
}
