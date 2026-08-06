package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.menu.IdentityTerminalMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenus {
    private static final DeferredRegister<MenuType<?>> MENUS=DeferredRegister.create(Registries.MENU,TronMod.MOD_ID);
    public static final DeferredHolder<MenuType<?>,MenuType<IdentityTerminalMenu>> IDENTITY_TERMINAL=MENUS.register("identity_terminal",()->IMenuTypeExtension.create((id,inventory,data)->new IdentityTerminalMenu(id,inventory)));
    private ModMenus(){}
    public static void register(IEventBus bus){MENUS.register(bus);}
}
