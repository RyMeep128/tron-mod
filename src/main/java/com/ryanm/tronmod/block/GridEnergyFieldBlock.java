package com.ryanm.tronmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public final class GridEnergyFieldBlock extends Block {
    public GridEnergyFieldBlock(BlockBehaviour.Properties properties) { super(properties); }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effects, boolean intersects) {
        if (intersects && level instanceof ServerLevel && entity.tickCount % 10 == 0) {
            entity.hurtOrSimulate(level.damageSources().magic(), 2.0F);
        }
        super.entityInside(state, level, pos, entity, effects, intersects);
    }
}
