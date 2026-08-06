package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.item.IdentityDiscItem;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TronMod.MOD_ID);

    public static final DeferredItem<IdentityDiscItem> IDENTITY_DISC = ITEMS.registerItem(
            "identity_disc",
            IdentityDiscItem::new,
            properties -> properties.sword(ToolMaterial.IRON, 2.0F, -2.2F)
    );
    public static final DeferredItem<?> GRID_ACCESS_DEVICE = ITEMS.registerSimpleBlockItem(ModBlocks.GRID_ACCESS_DEVICE);

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
