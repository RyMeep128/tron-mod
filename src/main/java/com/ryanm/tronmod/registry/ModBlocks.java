package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.block.GridAccessDeviceBlock;
import com.ryanm.tronmod.block.IdentityTerminalBlock;
import com.ryanm.tronmod.block.GridEnergyFieldBlock;
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
    public static final DeferredBlock<IdentityTerminalBlock> IDENTITY_TERMINAL = BLOCKS.registerBlock(
            "identity_terminal",
            IdentityTerminalBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(4.0F, 8.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(state -> 10)
    );
    public static final DeferredBlock<net.minecraft.world.level.block.Block> GRID_SHARD_ORE = BLOCKS.registerSimpleBlock(
            "grid_shard_ore",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .strength(3.0F, 4.0F)
                    .sound(SoundType.STONE)
                    .lightLevel(state -> 5)
    );
    public static final DeferredBlock<net.minecraft.world.level.block.Block> GRID_STONE = BLOCKS.registerSimpleBlock(
            "grid_stone", () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(2.2F, 6.0F).sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<net.minecraft.world.level.block.Block> CIRCUIT_TILES = BLOCKS.registerSimpleBlock(
            "circuit_tiles", () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(2.5F, 8.0F).sound(SoundType.METAL).lightLevel(state -> 8));
    public static final DeferredBlock<GridEnergyFieldBlock> GRID_ENERGY_FIELD = BLOCKS.registerBlock(
            "grid_energy_field", GridEnergyFieldBlock::new,
            () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).strength(1.0F, 3.0F).sound(SoundType.GLASS).lightLevel(state -> 14).noOcclusion().noCollision());

    private ModBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
