package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.block.GridAccessDeviceBlock;
import com.ryanm.tronmod.block.IdentityTerminalBlock;
import com.ryanm.tronmod.block.GridEnergyFieldBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.WallBlock;
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

    public static final DeferredBlock<Block> DARK_PANEL = material("dark_panel", 0);
    public static final DeferredBlock<Block> POLISHED_PANEL = material("polished_panel", 0);
    public static final DeferredBlock<Block> REINFORCED_PANEL = material("reinforced_panel", 0);
    public static final DeferredBlock<Block> RIBBED_PANEL = material("ribbed_panel", 0);
    public static final DeferredBlock<Block> VENT_PANEL = material("vent_panel", 0);
    public static final DeferredBlock<Block> FLOOR_PANEL = material("floor_panel", 0);
    public static final DeferredBlock<Block> PLATFORM_PANEL = material("platform_panel", 2);
    public static final DeferredBlock<Block> PORTAL_ALLOY = material("portal_alloy", 4);
    public static final DeferredBlock<Block> CYAN_CIRCUIT_PANEL = material("cyan_circuit_panel", 8);
    public static final DeferredBlock<Block> WHITE_CIRCUIT_PANEL = material("white_circuit_panel", 10);
    public static final DeferredBlock<Block> ORANGE_CIRCUIT_PANEL = material("orange_circuit_panel", 7);
    public static final DeferredBlock<RotatedPillarBlock> CYAN_LINE_TILE = directional("cyan_line_tile", 9);
    public static final DeferredBlock<RotatedPillarBlock> WHITE_LINE_TILE = directional("white_line_tile", 11);
    public static final DeferredBlock<RotatedPillarBlock> ORANGE_LINE_TILE = directional("orange_line_tile", 8);
    public static final DeferredBlock<RotatedPillarBlock> CYAN_LIGHT_PANEL = directional("cyan_light_panel", 14);
    public static final DeferredBlock<RotatedPillarBlock> WHITE_LIGHT_PANEL = directional("white_light_panel", 15);
    public static final DeferredBlock<RotatedPillarBlock> ORANGE_LIGHT_PANEL = directional("orange_light_panel", 12);
    public static final DeferredBlock<TransparentBlock> CYAN_GRID_GLASS = glass("cyan_grid_glass");
    public static final DeferredBlock<TransparentBlock> WHITE_GRID_GLASS = glass("white_grid_glass");
    public static final DeferredBlock<TransparentBlock> DARK_GRID_GLASS = glass("dark_grid_glass");
    public static final DeferredBlock<RotatedPillarBlock> CYAN_TOWER_FACADE = directional("cyan_tower_facade", 6);
    public static final DeferredBlock<RotatedPillarBlock> WHITE_TOWER_FACADE = directional("white_tower_facade", 7);
    public static final DeferredBlock<Block> GRID_ROAD = material("grid_road", 1);
    public static final DeferredBlock<RotatedPillarBlock> DATA_CONDUIT = directional("data_conduit", 7);

    public static final DeferredBlock<SlabBlock> DARK_PANEL_SLAB = slab("dark_panel_slab");
    public static final DeferredBlock<StairBlock> DARK_PANEL_STAIRS = stairs("dark_panel_stairs", DARK_PANEL);
    public static final DeferredBlock<WallBlock> DARK_PANEL_WALL = wall("dark_panel_wall");
    public static final DeferredBlock<SlabBlock> POLISHED_PANEL_SLAB = slab("polished_panel_slab");
    public static final DeferredBlock<StairBlock> POLISHED_PANEL_STAIRS = stairs("polished_panel_stairs", POLISHED_PANEL);
    public static final DeferredBlock<WallBlock> POLISHED_PANEL_WALL = wall("polished_panel_wall");
    public static final DeferredBlock<SlabBlock> REINFORCED_PANEL_SLAB = slab("reinforced_panel_slab");
    public static final DeferredBlock<StairBlock> REINFORCED_PANEL_STAIRS = stairs("reinforced_panel_stairs", REINFORCED_PANEL);
    public static final DeferredBlock<WallBlock> REINFORCED_PANEL_WALL = wall("reinforced_panel_wall");
    public static final DeferredBlock<SlabBlock> PLATFORM_PANEL_SLAB = slab("platform_panel_slab");
    public static final DeferredBlock<StairBlock> PLATFORM_PANEL_STAIRS = stairs("platform_panel_stairs", PLATFORM_PANEL);
    public static final DeferredBlock<WallBlock> PLATFORM_PANEL_WALL = wall("platform_panel_wall");

    private static BlockBehaviour.Properties legacyProperties(int light) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(3.0F, 10.0F).sound(SoundType.METAL).lightLevel(state -> light);
    }
    private static DeferredBlock<Block> material(String name, int light) { return BLOCKS.registerSimpleBlock(name, () -> legacyProperties(light)); }
    private static DeferredBlock<RotatedPillarBlock> directional(String name, int light) { return BLOCKS.registerBlock(name, RotatedPillarBlock::new, () -> legacyProperties(light)); }
    private static DeferredBlock<TransparentBlock> glass(String name) { return BLOCKS.registerBlock(name, TransparentBlock::new, () -> legacyProperties(3).noOcclusion()); }
    private static DeferredBlock<SlabBlock> slab(String name) { return BLOCKS.registerBlock(name, SlabBlock::new, () -> legacyProperties(0)); }
    private static DeferredBlock<WallBlock> wall(String name) { return BLOCKS.registerBlock(name, WallBlock::new, () -> legacyProperties(0)); }
    private static DeferredBlock<StairBlock> stairs(String name, DeferredBlock<Block> base) { return BLOCKS.registerBlock(name, properties -> new StairBlock(base.get().defaultBlockState(), properties), () -> legacyProperties(0)); }

    private ModBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
