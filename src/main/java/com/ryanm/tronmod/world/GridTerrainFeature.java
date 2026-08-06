package com.ryanm.tronmod.world;

import com.mojang.serialization.Codec;
import com.ryanm.tronmod.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class GridTerrainFeature extends Feature<NoneFeatureConfiguration> {
    public GridTerrainFeature(Codec<NoneFeatureConfiguration> codec) { super(codec); }
    @Override public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level=context.level(); BlockPos origin=context.origin();
        int radius=3+context.random().nextInt(5), peak=2+context.random().nextInt(5);
        boolean hazard=context.random().nextInt(5)==0;
        for(int x=-radius;x<=radius;x++) for(int z=-radius;z<=radius;z++) {
            double distance=Math.sqrt(x*x+z*z); if(distance>radius) continue;
            int height=Math.max(1,(int)Math.ceil(peak*(1.0-distance/(radius+1.0))));
            for(int y=0;y<height;y++) level.setBlock(origin.offset(x,y-1,z),ModBlocks.GRID_STONE.get().defaultBlockState(),2);
            if((Math.abs(x)+Math.abs(z))%5==0) level.setBlock(origin.offset(x,height-1,z),ModBlocks.CIRCUIT_TILES.get().defaultBlockState(),2);
        }
        if(hazard) for(int x=-1;x<=1;x++) for(int z=-1;z<=1;z++) level.setBlock(origin.offset(x,peak,z),ModBlocks.GRID_ENERGY_FIELD.get().defaultBlockState(),2);
        return true;
    }
}
