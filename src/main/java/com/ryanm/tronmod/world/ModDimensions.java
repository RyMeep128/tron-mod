package com.ryanm.tronmod.world;

import com.ryanm.tronmod.TronMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;

public final class ModDimensions {
    public static final ResourceKey<Level> GRID = ResourceKey.create(
            Registries.DIMENSION,
            Identifier.fromNamespaceAndPath(TronMod.MOD_ID, "grid")
    );
    public static final ResourceKey<LevelStem> GRID_STEM = ResourceKey.create(
            Registries.LEVEL_STEM,
            Identifier.fromNamespaceAndPath(TronMod.MOD_ID, "grid")
    );

    private ModDimensions() {
    }
}
