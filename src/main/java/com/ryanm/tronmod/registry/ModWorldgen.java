package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.world.GridAccessStructureFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModWorldgen {
    private static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, TronMod.MOD_ID);

    public static final DeferredHolder<Feature<?>, GridAccessStructureFeature> GRID_ACCESS_STRUCTURE = FEATURES.register(
            "grid_access_structure",
            () -> new GridAccessStructureFeature(NoneFeatureConfiguration.CODEC)
    );

    private ModWorldgen() {
    }

    public static void register(IEventBus modEventBus) {
        FEATURES.register(modEventBus);
    }
}
