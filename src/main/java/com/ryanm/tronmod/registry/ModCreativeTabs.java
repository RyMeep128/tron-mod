package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TronMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TRON_TAB = TABS.register(
            "tron",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tronmod.tron"))
                    .icon(() -> Items.LIGHT_BLUE_DYE.getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        // Registered Tron Mod items will be added here as they are introduced.
                    })
                    .build()
    );

    private ModCreativeTabs() {
    }

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }
}
