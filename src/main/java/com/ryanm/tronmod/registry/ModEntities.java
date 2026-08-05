package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.entity.IdentityDiscProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    private static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(TronMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<IdentityDiscProjectile>> IDENTITY_DISC_PROJECTILE =
            ENTITIES.registerEntityType(
                    "identity_disc",
                    IdentityDiscProjectile::new,
                    MobCategory.MISC,
                    builder -> builder
                            .sized(0.55F, 0.18F)
                            .clientTrackingRange(8)
                            .updateInterval(1)
                            .noLootTable()
            );

    private ModEntities() {
    }

    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }
}
