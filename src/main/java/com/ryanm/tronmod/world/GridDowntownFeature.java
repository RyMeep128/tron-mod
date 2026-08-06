package com.ryanm.tronmod.world;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
public final class GridDowntownFeature extends Feature<NoneFeatureConfiguration>{public GridDowntownFeature(Codec<NoneFeatureConfiguration> codec){super(codec);}@Override public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context){return GridDowntownPlan.generateChunk(context.level(),context.origin());}}
