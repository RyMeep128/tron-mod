package com.ryanm.tronmod.client.render;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.registry.ModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = TronMod.MOD_ID, value = Dist.CLIENT)
public final class IdentityDiscProjectileRenderer {
    private IdentityDiscProjectileRenderer() {
    }

    @SubscribeEvent
    public static void register(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                ModEntities.IDENTITY_DISC_PROJECTILE.get(),
                context -> new ThrownItemRenderer<>(context, 1.25F, true)
        );
    }
}
