package com.ryanm.tronmod.world;

import com.mojang.serialization.Codec;
import com.ryanm.tronmod.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class GridTerrainFeature extends Feature<NoneFeatureConfiguration> {
    public GridTerrainFeature(Codec<NoneFeatureConfiguration> codec) { super(codec); }
    @Override public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos origin=context.origin();
        return switch(GridRegion.at(origin.getX(),origin.getZ())) {
            case CENTRAL_GRID -> central(context);
            case CIRCUIT_PLAINS -> circuit(context);
            case OUTLANDS -> outlands(context);
            case DIGITAL_SEA -> sea(context);
            case DATA_STORM -> storm(context);
            case ISO_SANCTUARY -> sanctuary(context);
            case CORRUPTED_EXPANSE -> corrupted(context);
            case DELETED_SECTOR -> deleted(context);
        };
    }
    private boolean central(FeaturePlaceContext<NoneFeatureConfiguration> c){
        WorldGenLevel l=c.level();BlockPos o=c.origin();int height=5+c.random().nextInt(12);
        for(int y=0;y<height;y++) for(int x=-2;x<=2;x++) for(int z=-2;z<=2;z++) if(Math.abs(x)==2||Math.abs(z)==2) l.setBlock(o.offset(x,y,z),ModBlocks.GRID_STONE.get().defaultBlockState(),2);
        for(int y=0;y<height;y+=3) l.setBlock(o.offset(0,y,-2),ModBlocks.CIRCUIT_TILES.get().defaultBlockState(),2);return true;
    }
    private boolean circuit(FeaturePlaceContext<NoneFeatureConfiguration> c){
        WorldGenLevel l=c.level();BlockPos o=c.origin();boolean xAxis=c.random().nextBoolean();
        for(int i=-12;i<=12;i++) l.setBlock(o.offset(xAxis?i:0,-1,xAxis?0:i),ModBlocks.CIRCUIT_TILES.get().defaultBlockState(),2);return true;
    }
    private boolean outlands(FeaturePlaceContext<NoneFeatureConfiguration> c){
        WorldGenLevel l=c.level();BlockPos o=c.origin();int height=4+c.random().nextInt(8);
        for(int x=-6;x<=6;x++) for(int z=-3;z<=3;z++) for(int y=0;y<height-Math.abs(z);y++) l.setBlock(o.offset(x,y-1,z),ModBlocks.GRID_STONE.get().defaultBlockState(),2);return true;
    }
    private boolean sea(FeaturePlaceContext<NoneFeatureConfiguration> c){
        WorldGenLevel l=c.level();BlockPos o=c.origin();
        for(int x=-6;x<=6;x++) for(int z=-6;z<=6;z++){l.setBlock(o.offset(x,-1,z),Blocks.BLACK_STAINED_GLASS.defaultBlockState(),2);l.setBlock(o.offset(x,0,z),Blocks.WATER.defaultBlockState(),2);}return true;
    }
    private boolean storm(FeaturePlaceContext<NoneFeatureConfiguration> c){
        WorldGenLevel l=c.level();BlockPos o=c.origin();
        for(int i=0;i<9;i++){int x=c.random().nextInt(11)-5,z=c.random().nextInt(11)-5,y=c.random().nextInt(5);l.setBlock(o.offset(x,y,z),ModBlocks.GRID_ENERGY_FIELD.get().defaultBlockState(),2);}return true;
    }
    private boolean sanctuary(FeaturePlaceContext<NoneFeatureConfiguration> c){
        WorldGenLevel l=c.level();BlockPos o=c.origin();
        for(int i=0;i<7;i++){int x=c.random().nextInt(9)-4,z=c.random().nextInt(9)-4,h=2+c.random().nextInt(5);for(int y=0;y<h;y++)l.setBlock(o.offset(x,y,z),Blocks.AMETHYST_BLOCK.defaultBlockState(),2);l.setBlock(o.offset(x,h,z),Blocks.END_ROD.defaultBlockState(),2);}return true;
    }
    private boolean corrupted(FeaturePlaceContext<NoneFeatureConfiguration> c){
        WorldGenLevel l=c.level();BlockPos o=c.origin();
        for(int i=-9;i<=9;i++){l.setBlock(o.offset(i,-1,i/2),Blocks.RED_GLAZED_TERRACOTTA.defaultBlockState(),2);if(i%3==0)l.setBlock(o.offset(i,0,i/2),ModBlocks.GRID_ENERGY_FIELD.get().defaultBlockState(),2);}return true;
    }
    private boolean deleted(FeaturePlaceContext<NoneFeatureConfiguration> c){
        WorldGenLevel l=c.level();BlockPos o=c.origin();int radius=2+c.random().nextInt(3);
        for(int x=-radius;x<=radius;x++)for(int z=-radius;z<=radius;z++)if(x*x+z*z<=radius*radius)for(int y=0;y<18;y++)l.setBlock(o.offset(x,-y,z),Blocks.AIR.defaultBlockState(),2);return true;
    }
}
