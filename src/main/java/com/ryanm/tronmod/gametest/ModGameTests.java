package com.ryanm.tronmod.gametest;

import com.mojang.serialization.JsonOps;
import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.component.DiscIdentity;
import com.ryanm.tronmod.item.IdentityDiscItem;
import com.ryanm.tronmod.registry.ModDataComponents;
import com.ryanm.tronmod.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;
import java.util.UUID;

public final class ModGameTests {
    private static final DeferredRegister<Consumer<GameTestHelper>> TEST_FUNCTIONS =
            DeferredRegister.create(BuiltInRegistries.TEST_FUNCTION, TronMod.MOD_ID);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> FOUNDATION =
            TEST_FUNCTIONS.register("foundation", () -> helper -> helper.succeed());

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> IDENTITY_CODEC =
            TEST_FUNCTIONS.register("identity_codec", () -> ModGameTests::identityCodec);

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> IDENTITY_OWNERSHIP =
            TEST_FUNCTIONS.register("identity_ownership", () -> ModGameTests::identityOwnership);

    private ModGameTests() {
    }

    public static void register(IEventBus modEventBus) {
        TEST_FUNCTIONS.register(modEventBus);
    }

    private static void identityCodec(GameTestHelper helper) {
        DiscIdentity original = DiscIdentity.create(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "TestProgram",
                1_750_000_000_000L,
                UUID.fromString("22222222-2222-2222-2222-222222222222")
        ).recordHit().recordDefeat();

        var encoded = DiscIdentity.CODEC.encodeStart(JsonOps.INSTANCE, original).getOrThrow();
        DiscIdentity decoded = DiscIdentity.CODEC.parse(JsonOps.INSTANCE, encoded).getOrThrow();
        helper.assertValueEqual(decoded, original, "serialized disc identity");
        helper.succeed();
    }

    private static void identityOwnership(GameTestHelper helper) {
        ItemStack stack = new ItemStack(ModItems.IDENTITY_DISC.get());
        var firstOwner = helper.makeMockPlayer(GameType.SURVIVAL);
        ModItems.IDENTITY_DISC.get().onCraftedBy(stack, firstOwner);

        helper.assertTrue(
                stack.has(ModDataComponents.DISC_IDENTITY.get()),
                "a crafted disc should bind to its crafter"
        );
        helper.assertFalse(
                IdentityDiscItem.bind(stack, UUID.randomUUID(), "Intruder", 1_750_000_001_000L, UUID.randomUUID()),
                "a bound disc must reject a second owner"
        );

        DiscIdentity identity = stack.get(ModDataComponents.DISC_IDENTITY.get());
        helper.assertTrue(identity != null, "the disc should retain its identity component");
        helper.assertValueEqual(identity.ownerId(), firstOwner.getUUID(), "disc owner");
        helper.assertValueEqual(identity.ownerName(), firstOwner.getGameProfile().name(), "disc owner name");
        helper.assertValueEqual(stack.copy().get(ModDataComponents.DISC_IDENTITY.get()), identity, "copied disc identity");

        var registryOps = helper.getLevel().registryAccess().createSerializationContext(NbtOps.INSTANCE);
        var encodedStack = ItemStack.CODEC.encodeStart(registryOps, stack).getOrThrow();
        ItemStack loadedStack = ItemStack.CODEC.parse(registryOps, encodedStack).getOrThrow();
        helper.assertValueEqual(
                loadedStack.get(ModDataComponents.DISC_IDENTITY.get()),
                identity,
                "saved and loaded disc identity"
        );
        helper.succeed();
    }
}
