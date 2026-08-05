package com.ryanm.tronmod.item;

import com.ryanm.tronmod.component.DiscIdentity;
import com.ryanm.tronmod.entity.IdentityDiscProjectile;
import com.ryanm.tronmod.registry.ModDataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.function.Consumer;

public final class IdentityDiscItem extends Item {
    private static final int THROW_CHARGE_TICKS = 10;
    private static final float THROW_POWER = 2.2F;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneOffset.UTC);

    public IdentityDiscItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.has(ModDataComponents.DISC_IDENTITY.get())) {
            if (!level.isClientSide()) {
                bind(stack, player.getUUID(), player.getGameProfile().name(), System.currentTimeMillis(), UUID.randomUUID());
                player.sendOverlayMessage(Component.translatable("message.tronmod.disc_bound", player.getName()));
            }
            return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }
        if (stack.nextDamageWillBreak()) {
            return InteractionResult.FAIL;
        }
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remainingTime) {
        if (!(entity instanceof Player player)) {
            return false;
        }
        int chargeTicks = this.getUseDuration(stack, entity) - remainingTime;
        if (chargeTicks < THROW_CHARGE_TICKS || stack.nextDamageWillBreak()) {
            return false;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return true;
        }

        stack.hurtWithoutBreaking(1, player);
        ItemStack thrownStack = stack.consumeAndReturn(1, player);
        DiscIdentity identity = thrownStack.get(ModDataComponents.DISC_IDENTITY.get());
        if (identity != null) {
            thrownStack.set(ModDataComponents.DISC_IDENTITY.get(), identity.recordThrow());
        }

        IdentityDiscProjectile projectile = Projectile.spawnProjectileFromRotation(
                IdentityDiscProjectile::new,
                serverLevel,
                thrownStack,
                player,
                0.0F,
                THROW_POWER,
                0.25F
        );
        projectile.setCreativeOnly(player.hasInfiniteMaterials());
        level.playSound(null, projectile, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.25F);
        player.awardStat(Stats.ITEM_USED.get(this));
        return true;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Player player) {
        super.onCraftedBy(stack, player);
        if (!player.level().isClientSide()) {
            bind(stack, player.getUUID(), player.getGameProfile().name(), System.currentTimeMillis(), UUID.randomUUID());
        }
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        DiscIdentity identity = stack.get(ModDataComponents.DISC_IDENTITY.get());
        if (identity != null && !attacker.level().isClientSide()) {
            stack.set(ModDataComponents.DISC_IDENTITY.get(), identity.recordHit());
        }
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.isAlive() && !attacker.level().isClientSide()) {
            DiscIdentity identity = stack.get(ModDataComponents.DISC_IDENTITY.get());
            if (identity != null) {
                stack.set(ModDataComponents.DISC_IDENTITY.get(), identity.recordDefeat());
            }
        }
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> tooltip,
            TooltipFlag flag
    ) {
        DiscIdentity identity = stack.get(ModDataComponents.DISC_IDENTITY.get());
        if (identity == null) {
            tooltip.accept(Component.translatable("tooltip.tronmod.identity_disc.unbound").withStyle(ChatFormatting.GRAY));
            tooltip.accept(Component.translatable("tooltip.tronmod.identity_disc.bind_hint").withStyle(ChatFormatting.DARK_GRAY));
            return;
        }

        tooltip.accept(Component.translatable("tooltip.tronmod.identity_disc.owner", identity.ownerName()).withStyle(ChatFormatting.AQUA));
        tooltip.accept(Component.translatable(
                "tooltip.tronmod.identity_disc.created",
                DATE_FORMAT.format(Instant.ofEpochMilli(identity.createdAt()))
        ).withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.translatable("tooltip.tronmod.identity_disc.throws", identity.throwsCount()).withStyle(ChatFormatting.DARK_GRAY));
        tooltip.accept(Component.translatable("tooltip.tronmod.identity_disc.hits", identity.hits()).withStyle(ChatFormatting.DARK_GRAY));
        tooltip.accept(Component.translatable("tooltip.tronmod.identity_disc.defeats", identity.defeats()).withStyle(ChatFormatting.DARK_GRAY));
        tooltip.accept(Component.translatable("tooltip.tronmod.identity_disc.bounces", identity.bounces()).withStyle(ChatFormatting.DARK_GRAY));

        if (flag.isAdvanced()) {
            tooltip.accept(Component.translatable("tooltip.tronmod.identity_disc.id", identity.discId()).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    public static boolean bind(ItemStack stack, UUID ownerId, String ownerName, long createdAt, UUID discId) {
        if (stack.has(ModDataComponents.DISC_IDENTITY.get())) {
            return false;
        }
        stack.set(ModDataComponents.DISC_IDENTITY.get(), DiscIdentity.create(ownerId, ownerName, createdAt, discId));
        return true;
    }
}
