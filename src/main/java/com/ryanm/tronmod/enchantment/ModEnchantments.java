package com.ryanm.tronmod.enchantment;

import com.ryanm.tronmod.TronMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public final class ModEnchantments {
    public static final ResourceKey<Enchantment> REBOUND = key("rebound");
    public static final ResourceKey<Enchantment> VELOCITY = key("velocity");
    public static final ResourceKey<Enchantment> IMPACT = key("impact");
    public static final ResourceKey<Enchantment> RICOCHET = key("ricochet");

    private ModEnchantments() {
    }

    public static int getLevel(Level level, ItemStack stack, ResourceKey<Enchantment> enchantment) {
        Holder<Enchantment> holder = level.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(enchantment);
        return EnchantmentHelper.getItemEnchantmentLevel(holder, stack);
    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(TronMod.MOD_ID, name));
    }
}
