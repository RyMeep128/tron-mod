package com.ryanm.tronmod.world;

public enum GridRegion {
    CENTRAL_GRID, URBAN_FRINGE, CIRCUIT_PLAINS, OUTLANDS, DIGITAL_SEA, DATA_STORM,
    ISO_SANCTUARY, CORRUPTED_EXPANSE, DELETED_SECTOR;

    private static final int MACRO_SIZE = 2_048;

    public static GridRegion at(int x, int z) {
        int cityDistance = GridDowntownPlan.distanceToCity(x, z);
        if (cityDistance < GridDowntownPlan.RADIUS) return CENTRAL_GRID;
        if (cityDistance < 900) return URBAN_FRINGE;

        long cellX = Math.floorDiv(x, MACRO_SIZE), cellZ = Math.floorDiv(z, MACRO_SIZE);
        int localX = Math.floorMod(x, MACRO_SIZE), localZ = Math.floorMod(z, MACRO_SIZE);
        int edge = Math.min(Math.min(localX, MACRO_SIZE - 1 - localX), Math.min(localZ, MACRO_SIZE - 1 - localZ));
        if (edge < 24) return DATA_STORM;

        long hash = mix(cellX, cellZ);
        int roll = Math.floorMod(hash, 100);
        if (roll < 45) return DIGITAL_SEA;
        if (roll < 75) return OUTLANDS;
        if (roll < 90) return CIRCUIT_PLAINS;
        if (roll < 95) return CORRUPTED_EXPANSE;
        if (roll < 99) return DELETED_SECTOR;
        return ISO_SANCTUARY;
    }

    private static long mix(long x, long z) {
        long value = x * 341873128712L + z * 132897987541L + 0x5DEECE66DL;
        value ^= value >>> 30; value *= 0xbf58476d1ce4e5b9L;
        value ^= value >>> 27; value *= 0x94d049bb133111ebL;
        return value ^ value >>> 31;
    }
}
