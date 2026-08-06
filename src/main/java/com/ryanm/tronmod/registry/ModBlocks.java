package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.block.GridAccessDeviceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TronMod.MOD_ID);

    public static final DeferredBlock<GridAccessDeviceBlock> GRID_ACCESS_DEVICE = BLOCKS.registerBlock(
            "grid_access_device",
            GridAccessDeviceBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(5.0F, 1200.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(state -> 12)
    );

    private ModBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
