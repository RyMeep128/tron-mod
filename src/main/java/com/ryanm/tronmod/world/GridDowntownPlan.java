package com.ryanm.tronmod.world;

import com.ryanm.tronmod.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;

public final class GridDowntownPlan {
    public static final int CENTER_X = 8192;
    public static final int CENTER_Z = 8192;
    public static final int GROUND_Y = 64;
    public static final int RADIUS = 96;
    public static final int CITY_SPACING = 10_000;

    private static final ResourceKey<LootTable> FACILITY_LOOT = ResourceKey.create(
            Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath("tronmod", "chests/grid_facility"));
    private static final Tower[] TOWERS = {
            new Tower(-62, -62, 21, 112, false), new Tower(0, -66, 19, 144, true),
            new Tower(62, -62, 23, 96, false), new Tower(-66, 0, 19, 128, false),
            new Tower(66, 0, 21, 160, true), new Tower(-62, 62, 23, 104, false),
            new Tower(0, 66, 19, 136, false), new Tower(62, 62, 21, 120, true)
    };

    private GridDowntownPlan() {}

    public static BlockPos arrival() { return new BlockPos(CENTER_X, GROUND_Y + 1, CENTER_Z); }
    public static int towerCount() { return TOWERS.length; }
    public static int towerHeight(int index) { return TOWERS[index].height; }

    public static BlockPos cityCenterFor(int x, int z) {
        return new BlockPos(nearestCenter(x, CENTER_X), GROUND_Y, nearestCenter(z, CENTER_Z));
    }

    private static int nearestCenter(int coordinate, int anchor) {
        long cell = Math.floorDiv((long) coordinate - anchor + CITY_SPACING / 2, CITY_SPACING);
        return Math.toIntExact(anchor + cell * CITY_SPACING);
    }

    public static int distanceToCity(int x, int z) {
        BlockPos center = cityCenterFor(x, z);
        return Math.max(Math.abs(x - center.getX()), Math.abs(z - center.getZ()));
    }

    public static boolean contains(int x, int z) { return distanceToCity(x, z) < RADIUS; }

    public static boolean isRoad(int x, int z) {
        BlockPos center = cityCenterFor(x, z);
        int dx = x - center.getX(), dz = z - center.getZ();
        return contains(x, z) && (Math.abs(dx) < 7 || Math.abs(dz) < 7);
    }

    public static int towerAt(int x, int z) {
        BlockPos center = cityCenterFor(x, z);
        for (int i = 0; i < TOWERS.length; i++) {
            Tower tower = TOWERS[i];
            int half = tower.size / 2;
            if (Math.abs(x - (center.getX() + tower.dx)) <= half
                    && Math.abs(z - (center.getZ() + tower.dz)) <= half) return i;
        }
        return -1;
    }

    public static boolean generateChunk(WorldGenLevel level, BlockPos origin) {
        int minX = origin.getX() & ~15, minZ = origin.getZ() & ~15;
        BlockPos center = cityCenterFor(minX + 8, minZ + 8);
        if (minX > center.getX() + RADIUS || minX + 15 < center.getX() - RADIUS
                || minZ > center.getZ() + RADIUS || minZ + 15 < center.getZ() - RADIUS) return false;
        for (int x = minX; x < minX + 16; x++) for (int z = minZ; z < minZ + 16; z++)
            generateColumn(level, x, z, center.getX(), center.getZ());
        return true;
    }

    private static void generateColumn(WorldGenLevel level, int x, int z, int centerX, int centerZ) {
        int dx = x - centerX, dz = z - centerZ;
        if (Math.abs(dx) >= RADIUS || Math.abs(dz) >= RADIUS) return;
        BlockState surface = (Math.abs(dx) < 7 || Math.abs(dz) < 7)
                ? ModBlocks.GRID_ROAD.get().defaultBlockState() : ModBlocks.FLOOR_PANEL.get().defaultBlockState();
        if (Math.abs(dx) <= 25 && Math.abs(dz) <= 25) surface =
                (Math.abs(dx) % 6 == 0 || Math.abs(dz) % 6 == 0 ? ModBlocks.CYAN_LINE_TILE.get() : ModBlocks.POLISHED_PANEL.get()).defaultBlockState();
        for (int y = 60; y < GROUND_Y; y++) level.setBlock(new BlockPos(x, y, z), ModBlocks.REINFORCED_PANEL.get().defaultBlockState(), 2);
        level.setBlock(new BlockPos(x, GROUND_Y, z), surface, 2);
        if ((Math.abs(dx) == 10 && Math.abs(dz) < RADIUS - 8) || (Math.abs(dz) == 10 && Math.abs(dx) < RADIUS - 8))
            level.setBlock(new BlockPos(x, GROUND_Y + 1, z), ModBlocks.WHITE_LIGHT_PANEL.get().defaultBlockState(), 2);
        for (int i = 0; i < TOWERS.length; i++) generateTowerColumn(level, x, z, centerX, centerZ, TOWERS[i], i);
        generateBridgeColumn(level, x, z, dx, dz);
    }

    private static void generateTowerColumn(WorldGenLevel level, int x, int z, int centerX, int centerZ, Tower tower, int index) {
        int cx = centerX + tower.dx, cz = centerZ + tower.dz, half = tower.size / 2, lx = x - cx, lz = z - cz;
        if (Math.abs(lx) > half || Math.abs(lz) > half) return;
        boolean edge = Math.abs(lx) == half || Math.abs(lz) == half;
        for (int y = GROUND_Y + 1; y <= GROUND_Y + tower.height; y++) {
            int localY = y - GROUND_Y, floorNumber = localY / 6;
            boolean floor = localY % 6 == 0;
            BlockPos pos = new BlockPos(x, y, z);
            boolean entrance = localY <= 4 && ((Math.abs(lx) <= 2 && lz == -half) || (Math.abs(lz) <= 2 && lx == -half));
            if (entrance) { level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2); continue; }
            if (edge) {
                boolean lit = localY % 12 >= 2 && localY % 12 <= 4 && (x + z) % 4 == 0;
                BlockState facade = lit ? (tower.authority ? ModBlocks.ORANGE_CIRCUIT_PANEL.get()
                        : index % 2 == 0 ? ModBlocks.CYAN_TOWER_FACADE.get() : ModBlocks.WHITE_TOWER_FACADE.get()).defaultBlockState()
                        : ModBlocks.DARK_PANEL.get().defaultBlockState();
                level.setBlock(pos, facade, 2);
            } else if (floor) {
                if (!(lx == 1 && lz == 0)) level.setBlock(pos, ModBlocks.PLATFORM_PANEL.get().defaultBlockState(), 2);
            } else if (lx == 0 && lz == 0) level.setBlock(pos, ModBlocks.REINFORCED_PANEL.get().defaultBlockState(), 2);
            else if (lx == 1 && lz == 0) level.setBlock(pos, Blocks.LADDER.defaultBlockState().setValue(LadderBlock.FACING, Direction.EAST), 2);
            else if (localY % 6 == 1 && lx == 2 && lz == 2 && floorNumber % 7 == 0) {
                level.setBlock(pos, Blocks.BARREL.defaultBlockState(), 2);
                if (level.getBlockEntity(pos) instanceof RandomizableContainerBlockEntity container) {
                    container.setLootTable(FACILITY_LOOT); container.setLootTableSeed(index * 1009L + floorNumber);
                }
            } else if (localY % 6 == 1 && lx == -2 && lz == 2 && floorNumber % 5 == 0)
                level.setBlock(pos, ModBlocks.IDENTITY_TERMINAL.get().defaultBlockState(), 2);
            else if (localY % 6 != 5 && moduleWall(index, floorNumber, lx, lz))
                level.setBlock(pos, ModBlocks.DARK_GRID_GLASS.get().defaultBlockState(), 2);
        }
        int roof = GROUND_Y + tower.height + 1;
        if (Math.abs(lx) <= half - 2 && Math.abs(lz) <= half - 2 && !(lx == 1 && lz == 0))
            level.setBlock(new BlockPos(x, roof, z), ModBlocks.POLISHED_PANEL.get().defaultBlockState(), 2);
        if ((Math.abs(lx) == half - 1 || Math.abs(lz) == half - 1) && ((x + z) & 3) == 0)
            level.setBlock(new BlockPos(x, roof + 1, z), ModBlocks.CYAN_LIGHT_PANEL.get().defaultBlockState(), 2);
    }

    private static boolean moduleWall(int tower, int floor, int x, int z) {
        return switch (Math.floorMod(tower + floor, 6)) {
            case 0 -> x == 0 && Math.abs(z) > 1; case 1 -> z == 0 && Math.abs(x) > 1;
            case 2 -> Math.abs(x) == 3 && Math.abs(z) > 1; case 3 -> Math.abs(z) == 3 && Math.abs(x) > 1;
            case 4 -> x == z && Math.abs(x) > 1; default -> x == -z && Math.abs(x) > 1;
        };
    }

    private static void generateBridgeColumn(WorldGenLevel level, int x, int z, int dx, int dz) {
        boolean eastWest = Math.abs(dz - 38) <= 2 && Math.abs(dx) < 70;
        boolean northSouth = Math.abs(dx + 38) <= 2 && Math.abs(dz) < 70;
        if (!eastWest && !northSouth) return;
        int y = GROUND_Y + 24;
        level.setBlock(new BlockPos(x, y, z), ModBlocks.PLATFORM_PANEL.get().defaultBlockState(), 2);
        if ((eastWest ? dx : dz) % 5 == 0) level.setBlock(new BlockPos(x, y + 1, z), ModBlocks.CYAN_LIGHT_PANEL.get().defaultBlockState(), 2);
    }

    private record Tower(int dx, int dz, int size, int height, boolean authority) {}
}
