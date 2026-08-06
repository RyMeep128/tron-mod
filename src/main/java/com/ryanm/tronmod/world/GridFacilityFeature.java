package com.ryanm.tronmod.world;

import com.mojang.serialization.Codec;
import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.storage.loot.LootTable;

public final class GridFacilityFeature extends Feature<NoneFeatureConfiguration> {
    private static final ResourceKey<LootTable> LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(TronMod.MOD_ID, "chests/grid_facility"));
    public GridFacilityFeature(Codec<NoneFeatureConfiguration> codec) { super(codec); }

    @Override public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos center = context.origin();
        if (GridDowntownPlan.contains(center.getX(), center.getZ())) return false;
        GridRegion region=GridRegion.at(center.getX(),center.getZ());
        if(region!=GridRegion.CENTRAL_GRID&&region!=GridRegion.CIRCUIT_PLAINS&&region!=GridRegion.OUTLANDS) return false;
        while (center.getY() > level.getMinY() + 1 && level.isEmptyBlock(center.below())) center = center.below();
        if (!level.getBlockState(center.below()).isSolid()) return false;
        for (int x=-5;x<=5;x++) for(int z=-5;z<=5;z++) {
            level.setBlock(center.offset(x,-1,z), ((x==0||z==0)?ModBlocks.CIRCUIT_TILES.get():ModBlocks.GRID_STONE.get()).defaultBlockState(), 2);
            for(int y=0;y<=4;y++) level.setBlock(center.offset(x,y,z), Blocks.AIR.defaultBlockState(),2);
        }
        for(int x:new int[]{-5,5}) for(int z:new int[]{-5,5}) for(int y=0;y<=4;y++) level.setBlock(center.offset(x,y,z), ModBlocks.CIRCUIT_TILES.get().defaultBlockState(),2);
        for(int x=-3;x<=3;x++) level.setBlock(center.offset(x,4,-3), ModBlocks.CIRCUIT_TILES.get().defaultBlockState(),2);
        level.setBlock(center, ModBlocks.IDENTITY_TERMINAL.get().defaultBlockState(),2);
        BlockPos cache=center.offset(3,0,3);
        level.setBlock(cache, Blocks.BARREL.defaultBlockState(),2);
        if(level.getBlockEntity(cache) instanceof RandomizableContainerBlockEntity container){container.setLootTable(LOOT);container.setLootTableSeed(context.random().nextLong());}
        for(int x=-2;x<=2;x++) level.setBlock(center.offset(x,0,-5), ModBlocks.GRID_ENERGY_FIELD.get().defaultBlockState(),2);
        return true;
    }
}
