package com.ryanm.tronmod.entity;

import com.ryanm.tronmod.component.DiscIdentity;
import com.ryanm.tronmod.registry.ModDataComponents;
import com.ryanm.tronmod.registry.ModEntities;
import com.ryanm.tronmod.registry.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public final class IdentityDiscProjectile extends ThrowableItemProjectile {
    public static final int DEFAULT_RICOCHETS = 2;
    private static final int MAX_FLIGHT_TICKS = 200;
    private static final float PROJECTILE_DAMAGE = 6.0F;
    private static final double BOUNCE_SPEED_RETAINED = 0.82;

    private int ricochets;
    private boolean creativeOnly;

    public IdentityDiscProjectile(EntityType<? extends IdentityDiscProjectile> type, Level level) {
        super(type, level);
    }

    public IdentityDiscProjectile(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntities.IDENTITY_DISC_PROJECTILE.get(), owner, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.IDENTITY_DISC.get();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isAlive()) {
            return;
        }
        if (this.level().isClientSide()) {
            Vec3 movement = this.getDeltaMovement();
            this.level().addParticle(
                    ParticleTypes.ELECTRIC_SPARK,
                    this.getX() - movement.x * 0.25,
                    this.getY() - movement.y * 0.25,
                    this.getZ() - movement.z * 0.25,
                    0.0, 0.0, 0.0
            );
        } else if (this.tickCount >= MAX_FLIGHT_TICKS && this.level() instanceof ServerLevel serverLevel) {
            this.dropAndDiscard(serverLevel);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        Entity target = hitResult.getEntity();
        boolean damaged = target.hurtOrSimulate(this.damageSources().thrown(this, this.getOwner()), PROJECTILE_DAMAGE);
        if (damaged) {
            this.updateIdentityAfterHit(!target.isAlive());
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY(0.5), target.getZ(), 12, 0.25, 0.25, 0.25, 0.08);
            serverLevel.playSound(null, target.blockPosition(), SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 1.0F, 1.25F);
        }
        this.dropAndDiscard(serverLevel);
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (this.ricochets < DEFAULT_RICOCHETS) {
            this.ricochets++;
            this.updateIdentityAfterBounce();
            Direction face = hitResult.getDirection();
            this.setDeltaMovement(reflect(this.getDeltaMovement(), face, BOUNCE_SPEED_RETAINED));
            Vec3 normal = face.getUnitVec3().scale(0.08);
            this.setPos(hitResult.getLocation().add(normal));
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), 10, 0.12, 0.12, 0.12, 0.1);
            serverLevel.playSound(null, hitResult.getBlockPos(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.9F, 1.35F + this.ricochets * 0.1F);
        } else {
            serverLevel.playSound(null, hitResult.getBlockPos(), SoundEvents.TRIDENT_HIT_GROUND, SoundSource.PLAYERS, 0.9F, 1.2F);
            this.dropAndDiscard(serverLevel);
        }
    }

    public static Vec3 reflect(Vec3 velocity, Direction hitFace, double speedRetained) {
        return switch (hitFace.getAxis()) {
            case X -> new Vec3(-velocity.x, velocity.y, velocity.z).scale(speedRetained);
            case Y -> new Vec3(velocity.x, -velocity.y, velocity.z).scale(speedRetained);
            case Z -> new Vec3(velocity.x, velocity.y, -velocity.z).scale(speedRetained);
        };
    }

    public int getRicochets() {
        return this.ricochets;
    }

    public void setCreativeOnly(boolean creativeOnly) {
        this.creativeOnly = creativeOnly;
    }

    private void updateIdentityAfterBounce() {
        ItemStack stack = this.getItem().copy();
        DiscIdentity identity = stack.get(ModDataComponents.DISC_IDENTITY.get());
        if (identity != null) {
            stack.set(ModDataComponents.DISC_IDENTITY.get(), identity.recordBounce());
            this.setItem(stack);
        }
    }

    private void updateIdentityAfterHit(boolean defeatedTarget) {
        ItemStack stack = this.getItem().copy();
        DiscIdentity identity = stack.get(ModDataComponents.DISC_IDENTITY.get());
        if (identity != null) {
            DiscIdentity updated = identity.recordHit();
            if (defeatedTarget) {
                updated = updated.recordDefeat();
            }
            stack.set(ModDataComponents.DISC_IDENTITY.get(), updated);
            this.setItem(stack);
        }
    }

    private void dropAndDiscard(ServerLevel level) {
        if (!this.creativeOnly) {
            this.spawnAtLocation(level, this.getItem().copy(), 0.1F);
        }
        this.discard();
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Ricochets", this.ricochets);
        output.putBoolean("CreativeOnly", this.creativeOnly);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.ricochets = input.getIntOr("Ricochets", 0);
        this.creativeOnly = input.getBooleanOr("CreativeOnly", false);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.01;
    }
}
