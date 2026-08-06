package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.component.DiscIdentity;
import com.ryanm.tronmod.component.DiscPrograms;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModDataComponents {
    private static final DeferredRegister.DataComponents COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, TronMod.MOD_ID);

    public static final Supplier<DataComponentType<DiscIdentity>> DISC_IDENTITY =
            COMPONENTS.registerComponentType("disc_identity", builder -> builder.persistent(DiscIdentity.CODEC));
    public static final Supplier<DataComponentType<DiscPrograms>> DISC_PROGRAMS =
            COMPONENTS.registerComponentType("disc_programs", builder -> builder.persistent(DiscPrograms.CODEC));

    private ModDataComponents() {
    }

    public static void register(IEventBus modEventBus) {
        COMPONENTS.register(modEventBus);
    }
}
