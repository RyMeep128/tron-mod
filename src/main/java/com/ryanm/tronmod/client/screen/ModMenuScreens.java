package com.ryanm.tronmod.client.screen;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.registry.ModMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid=TronMod.MOD_ID,value=Dist.CLIENT)
public final class ModMenuScreens {
    private ModMenuScreens(){}
    @SubscribeEvent public static void register(RegisterMenuScreensEvent event){event.register(ModMenus.IDENTITY_TERMINAL.get(),IdentityTerminalScreen::new);}
}
