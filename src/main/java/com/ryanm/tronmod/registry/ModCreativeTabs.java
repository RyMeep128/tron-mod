package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
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
                    .icon(() -> ModItems.IDENTITY_DISC.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.IDENTITY_DISC.get());
                        output.accept(ModItems.GRID_ACCESS_DEVICE.get());
                        output.accept(ModItems.IDENTITY_TERMINAL.get());
                        output.accept(ModItems.GRID_SHARD.get());
                        output.accept(ModItems.GRID_SHARD_ORE.get());
                        output.accept(ModItems.GRID_STONE.get());
                        output.accept(ModItems.CIRCUIT_TILES.get());
                        output.accept(ModItems.GRID_ENERGY_FIELD.get());
                        output.accept(ModItems.GRID_ALLOY.get());
                        output.accept(ModItems.DARK_PANEL.get());
                        output.accept(ModItems.POLISHED_PANEL.get());
                        output.accept(ModItems.REINFORCED_PANEL.get());
                        output.accept(ModItems.RIBBED_PANEL.get());
                        output.accept(ModItems.VENT_PANEL.get());
                        output.accept(ModItems.FLOOR_PANEL.get());
                        output.accept(ModItems.PLATFORM_PANEL.get());
                        output.accept(ModItems.PORTAL_ALLOY.get());
                        output.accept(ModItems.CYAN_CIRCUIT_PANEL.get());
                        output.accept(ModItems.WHITE_CIRCUIT_PANEL.get());
                        output.accept(ModItems.ORANGE_CIRCUIT_PANEL.get());
                        output.accept(ModItems.CYAN_LINE_TILE.get());
                        output.accept(ModItems.WHITE_LINE_TILE.get());
                        output.accept(ModItems.ORANGE_LINE_TILE.get());
                        output.accept(ModItems.CYAN_LIGHT_PANEL.get());
                        output.accept(ModItems.WHITE_LIGHT_PANEL.get());
                        output.accept(ModItems.ORANGE_LIGHT_PANEL.get());
                        output.accept(ModItems.CYAN_GRID_GLASS.get());
                        output.accept(ModItems.WHITE_GRID_GLASS.get());
                        output.accept(ModItems.DARK_GRID_GLASS.get());
                        output.accept(ModItems.CYAN_TOWER_FACADE.get());
                        output.accept(ModItems.WHITE_TOWER_FACADE.get());
                        output.accept(ModItems.GRID_ROAD.get());
                        output.accept(ModItems.DATA_CONDUIT.get());
                        output.accept(ModItems.DARK_PANEL_SLAB.get());
                        output.accept(ModItems.DARK_PANEL_STAIRS.get());
                        output.accept(ModItems.DARK_PANEL_WALL.get());
                        output.accept(ModItems.POLISHED_PANEL_SLAB.get());
                        output.accept(ModItems.POLISHED_PANEL_STAIRS.get());
                        output.accept(ModItems.POLISHED_PANEL_WALL.get());
                        output.accept(ModItems.REINFORCED_PANEL_SLAB.get());
                        output.accept(ModItems.REINFORCED_PANEL_STAIRS.get());
                        output.accept(ModItems.REINFORCED_PANEL_WALL.get());
                        output.accept(ModItems.PLATFORM_PANEL_SLAB.get());
                        output.accept(ModItems.PLATFORM_PANEL_STAIRS.get());
                        output.accept(ModItems.PLATFORM_PANEL_WALL.get());
                        output.accept(ModItems.REBOUND_PROTOCOL.get());
                        output.accept(ModItems.VELOCITY_PROTOCOL.get());
                        output.accept(ModItems.IMPACT_PROTOCOL.get());
                        output.accept(ModItems.RICOCHET_PROTOCOL.get());
                        output.accept(ModItems.RECALL_PROTOCOL.get());
                        output.accept(ModItems.SEEKING_PROTOCOL.get());
                        output.accept(ModItems.PIERCING_PROTOCOL.get());
                        output.accept(ModItems.SPLIT_CIRCUIT_PROTOCOL.get());
                        output.accept(ModItems.DISRUPTION_PROTOCOL.get());
                        output.accept(ModItems.PERFECT_RETURN_PROTOCOL.get());
                    })
                    .build()
    );

    private ModCreativeTabs() {
    }

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }
}
