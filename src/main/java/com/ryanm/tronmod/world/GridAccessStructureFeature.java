package com.ryanm.tronmod.world;

import com.mojang.serialization.Codec;
import com.ryanm.tronmod.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class GridAccessStructureFeature extends Feature<NoneFeatureConfiguration> {
    public GridAccessStructureFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos center = context.origin();
        if (center.getY() <= level.getMinY() + 2 || center.getY() >= level.getMaxY() - 6) {
            return false;
        }

        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                BlockPos floor = center.offset(x, -1, z);
                level.setBlock(floor, ((Math.abs(x) + Math.abs(z)) % 3 == 0
                        ? Blocks.CYAN_CONCRETE
                        : Blocks.BLACK_CONCRETE).defaultBlockState(), 2);
                for (int y = 0; y <= 3; y++) {
                    level.setBlock(center.offset(x, y, z), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }

        for (int x : new int[]{-3, 3}) {
            for (int z : new int[]{-3, 3}) {
                for (int y = 0; y <= 4; y++) {
                    level.setBlock(center.offset(x, y, z), Blocks.POLISHED_BLACKSTONE.defaultBlockState(), 2);
                }
                level.setBlock(center.offset(x, 4, z), Blocks.SEA_LANTERN.defaultBlockState(), 2);
            }
        }

        level.setBlock(center, ModBlocks.GRID_ACCESS_DEVICE.get().defaultBlockState(), 2);
        level.setBlock(center.above(), Blocks.CYAN_STAINED_GLASS.defaultBlockState(), 2);
        return true;
    }
}
