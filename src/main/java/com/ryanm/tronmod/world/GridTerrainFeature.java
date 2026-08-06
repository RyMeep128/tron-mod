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
        if(GridDowntownPlan.contains(origin.getX(),origin.getZ())) return false;
        return switch(GridRegion.at(origin.getX(),origin.getZ())) {
            case CENTRAL_GRID -> central(context);
            case URBAN_FRINGE -> fringe(context);
            case CIRCUIT_PLAINS -> circuit(context);
            case OUTLANDS -> outlands(context);
            case DIGITAL_SEA -> sea(context);
            case DATA_STORM -> storm(context);
            case ISO_SANCTUARY -> sanctuary(context);
            case CORRUPTED_EXPANSE -> corrupted(context);
            case DELETED_SECTOR -> deleted(context);
        };
    }
    private boolean fringe(FeaturePlaceContext<NoneFeatureConfiguration> c){
        WorldGenLevel l=c.level();BlockPos o=c.origin();int distance=GridDowntownPlan.distanceToCity(o.getX(),o.getZ());
        float urbanWeight=Math.max(0.0F,1.0F-(distance-GridDowntownPlan.RADIUS)/804.0F);
        if(c.random().nextFloat()>urbanWeight)return c.random().nextBoolean()?outlands(c):circuit(c);
        int radius=2+c.random().nextInt(4);
        for(int x=-radius;x<=radius;x++)for(int z=-radius;z<=radius;z++)if(c.random().nextFloat()<0.72F)
            l.setBlock(o.offset(x,-1,z),(x==0||z==0?ModBlocks.CYAN_LINE_TILE.get():ModBlocks.DARK_PANEL.get()).defaultBlockState(),2);
        int height=2+c.random().nextInt(Math.max(2,2+(int)(urbanWeight*10)));
        for(int y=0;y<height;y++)l.setBlock(o.offset(radius, y, radius),
                (y%4==1?ModBlocks.CYAN_CIRCUIT_PANEL.get():ModBlocks.REINFORCED_PANEL.get()).defaultBlockState(),2);
        return true;
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
        WorldGenLevel l=c.level();BlockPos o=c.origin();int minX=o.getX()&~15,minZ=o.getZ()&~15;
        for(int x=minX;x<minX+16;x++)for(int z=minZ;z<minZ+16;z++){
            BlockPos surface=new BlockPos(x,o.getY()-1,z);
            l.setBlock(surface,ModBlocks.DARK_GRID_GLASS.get().defaultBlockState(),2);
            l.setBlock(surface.above(),Blocks.WATER.defaultBlockState(),2);
            l.setBlock(surface.above(2),Blocks.AIR.defaultBlockState(),2);
        }return true;
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
