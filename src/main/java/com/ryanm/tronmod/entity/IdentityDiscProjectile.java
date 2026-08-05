package com.ryanm.tronmod.entity;

import com.ryanm.tronmod.component.DiscIdentity;
import com.ryanm.tronmod.enchantment.ModEnchantments;
import com.ryanm.tronmod.registry.ModDataComponents;
import com.ryanm.tronmod.registry.ModEntities;
import com.ryanm.tronmod.registry.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
    private static final int MAX_TOTAL_LIFETIME_TICKS = 400;
    private static final int EMBEDDED_DROP_TICKS = 1200;
    private static final float MIN_PROJECTILE_DAMAGE = 3.0F;
    private static final float MAX_PROJECTILE_DAMAGE = 9.0F;
    private static final double BOUNCE_SPEED_RETAINED = 0.82;
    private static final int MAX_TOTAL_RICOCHETS = 8;
    private static final double RETURN_ACCELERATION = 0.18;

    private static final EntityDataAccessor<Integer> DATA_RICOCHETS =
            SynchedEntityData.defineId(IdentityDiscProjectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_EMBEDDED =
            SynchedEntityData.defineId(IdentityDiscProjectile.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DATA_CHARGE =
            SynchedEntityData.defineId(IdentityDiscProjectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DATA_RETURNING =
            SynchedEntityData.defineId(IdentityDiscProjectile.class, EntityDataSerializers.BOOLEAN);

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
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_RICOCHETS, 0);
        entityData.define(DATA_EMBEDDED, false);
        entityData.define(DATA_CHARGE, 0.0F);
        entityData.define(DATA_RETURNING, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isAlive()) {
            return;
        }
        if (this.isEmbedded()) {
            this.setDeltaMovement(Vec3.ZERO);
            if (!this.level().isClientSide() && this.tickCount >= EMBEDDED_DROP_TICKS && this.level() instanceof ServerLevel serverLevel) {
                this.dropAndDiscard(serverLevel);
            }
        } else if (this.level().isClientSide()) {
            Vec3 movement = this.getDeltaMovement();
            this.level().addParticle(
                    ParticleTypes.ELECTRIC_SPARK,
                    this.getX() - movement.x * 0.25,
                    this.getY() - movement.y * 0.25,
                    this.getZ() - movement.z * 0.25,
                    0.0, 0.0, 0.0
            );
        } else if (this.level() instanceof ServerLevel serverLevel) {
            if (this.isReturning()) {
                if (this.tickCount >= MAX_TOTAL_LIFETIME_TICKS) {
                    this.dropAndDiscard(serverLevel);
                } else {
                    this.tickReturn();
                }
            } else if (this.tickCount >= MAX_FLIGHT_TICKS) {
                if (this.getEnchantmentLevel(ModEnchantments.REBOUND) > 0) {
                    this.beginReturn();
                } else {
                    this.dropAndDiscard(serverLevel);
                }
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        Entity target = hitResult.getEntity();
        boolean damaged = target.hurtOrSimulate(this.damageSources().thrown(this, this.getOwner()), this.getImpactDamage());
        if (damaged) {
            int impactLevel = this.getEnchantmentLevel(ModEnchantments.IMPACT);
            if (impactLevel > 0) {
                Vec3 knockback = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(impactLevel * 0.35);
                target.push(knockback.x, 0.08 * impactLevel, knockback.z);
            }
            this.updateIdentityAfterHit(!target.isAlive());
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY(0.5), target.getZ(), 12, 0.25, 0.25, 0.25, 0.08);
            serverLevel.playSound(null, target.blockPosition(), SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 1.0F, 1.25F);
        }
        if (this.getEnchantmentLevel(ModEnchantments.REBOUND) > 0) {
            this.beginReturn();
        } else {
            this.entityData.set(DATA_RICOCHETS, this.getMaximumRicochets());
        }
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.05, 0.2, -0.05));
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (this.getRicochets() < this.getMaximumRicochets()) {
            this.entityData.set(DATA_RICOCHETS, this.getRicochets() + 1);
            this.updateIdentityAfterBounce();
            Direction face = hitResult.getDirection();
            this.setDeltaMovement(reflect(this.getDeltaMovement(), face, BOUNCE_SPEED_RETAINED));
            Vec3 normal = face.getUnitVec3().scale(0.08);
            this.setPos(hitResult.getLocation().add(normal));
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), 10, 0.12, 0.12, 0.12, 0.1);
            serverLevel.playSound(null, hitResult.getBlockPos(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.9F, 1.35F + this.getRicochets() * 0.1F);
        } else {
            if (this.getEnchantmentLevel(ModEnchantments.REBOUND) > 0) {
                this.beginReturn();
                this.setDeltaMovement(reflect(this.getDeltaMovement(), hitResult.getDirection(), BOUNCE_SPEED_RETAINED));
            } else {
                serverLevel.playSound(null, hitResult.getBlockPos(), SoundEvents.TRIDENT_HIT_GROUND, SoundSource.PLAYERS, 0.9F, 1.2F);
                this.embed(hitResult);
            }
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
        return this.entityData.get(DATA_RICOCHETS);
    }

    public int getMaximumRicochets() {
        return Math.min(MAX_TOTAL_RICOCHETS, DEFAULT_RICOCHETS + this.getEnchantmentLevel(ModEnchantments.RICOCHET));
    }

    public boolean isEmbedded() {
        return this.entityData.get(DATA_EMBEDDED);
    }

    public void setCreativeOnly(boolean creativeOnly) {
        this.creativeOnly = creativeOnly;
    }

    public void setChargeProgress(float chargeProgress) {
        this.entityData.set(DATA_CHARGE, Mth.clamp(chargeProgress, 0.0F, 1.0F));
    }

    public float getChargeProgress() {
        return this.entityData.get(DATA_CHARGE);
    }

    public float getImpactDamage() {
        return getImpactDamage(this.getChargeProgress()) + this.getEnchantmentLevel(ModEnchantments.IMPACT) * 2.0F;
    }

    public static float getImpactDamage(float chargeProgress) {
        return Mth.lerp(Mth.clamp(chargeProgress, 0.0F, 1.0F), MIN_PROJECTILE_DAMAGE, MAX_PROJECTILE_DAMAGE);
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

    private int getEnchantmentLevel(net.minecraft.resources.ResourceKey<net.minecraft.world.item.enchantment.Enchantment> enchantment) {
        return ModEnchantments.getLevel(this.level(), this.getItem(), enchantment);
    }

    private boolean isReturning() {
        return this.entityData.get(DATA_RETURNING);
    }

    private void beginReturn() {
        this.entityData.set(DATA_RETURNING, true);
        this.setNoGravity(true);
    }

    private void tickReturn() {
        Entity owner = this.getOwner();
        if (owner == null || !owner.isAlive()) {
            return;
        }
        Vec3 target = owner.getEyePosition().subtract(this.position());
        if (target.lengthSqr() < 2.25) {
            if (owner instanceof Player player && (this.creativeOnly || player.getInventory().add(this.getItem().copy()))) {
                this.playSound(SoundEvents.ITEM_PICKUP, 0.5F, 1.5F);
                this.discard();
            }
            return;
        }
        int reboundLevel = this.getEnchantmentLevel(ModEnchantments.REBOUND);
        double acceleration = RETURN_ACCELERATION + reboundLevel * 0.05;
        this.setDeltaMovement(this.getDeltaMovement().scale(0.88).add(target.normalize().scale(acceleration)));
    }

    private void embed(BlockHitResult hitResult) {
        this.entityData.set(DATA_EMBEDDED, true);
        this.setNoGravity(true);
        this.setDeltaMovement(Vec3.ZERO);
        Vec3 normal = hitResult.getDirection().getUnitVec3().scale(0.035);
        this.setPos(hitResult.getLocation().add(normal));
    }

    @Override
    public void playerTouch(Player player) {
        if (!(this.level() instanceof ServerLevel) || !this.isEmbedded()) {
            return;
        }
        if (this.getOwner() != null && !this.ownedBy(player)) {
            return;
        }
        if (this.creativeOnly || player.getInventory().add(this.getItem().copy())) {
            this.playSound(SoundEvents.ITEM_PICKUP, 0.4F, 1.4F);
            this.discard();
        }
    }

    @Override
    public boolean isPickable() {
        return this.isEmbedded();
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Ricochets", this.getRicochets());
        output.putBoolean("Embedded", this.isEmbedded());
        output.putFloat("Charge", this.getChargeProgress());
        output.putBoolean("Returning", this.isReturning());
        output.putBoolean("CreativeOnly", this.creativeOnly);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.entityData.set(DATA_RICOCHETS, input.getIntOr("Ricochets", 0));
        this.entityData.set(DATA_EMBEDDED, input.getBooleanOr("Embedded", false));
        this.entityData.set(DATA_CHARGE, input.getFloatOr("Charge", 0.0F));
        this.entityData.set(DATA_RETURNING, input.getBooleanOr("Returning", false));
        this.setNoGravity(this.isEmbedded() || this.isReturning());
        this.creativeOnly = input.getBooleanOr("CreativeOnly", false);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.01;
    }
}
