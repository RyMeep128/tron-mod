package com.ryanm.tronmod.gametest;

import com.mojang.serialization.JsonOps;
import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.component.DiscIdentity;
import com.ryanm.tronmod.component.DiscPrograms;
import com.ryanm.tronmod.component.ProgramType;
import com.ryanm.tronmod.block.IdentityTerminalBlock;
import com.ryanm.tronmod.block.entity.IdentityTerminalBlockEntity;
import com.ryanm.tronmod.entity.IdentityDiscProjectile;
import com.ryanm.tronmod.item.IdentityDiscItem;
import com.ryanm.tronmod.registry.ModDataComponents;
import com.ryanm.tronmod.registry.ModItems;
import com.ryanm.tronmod.registry.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.core.Direction;
import net.minecraft.world.level.GameType;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
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

    public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> PROJECTILE_FOUNDATION =
            TEST_FUNCTIONS.register("projectile_foundation", () -> ModGameTests::projectileFoundation);

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

    private static void projectileFoundation(GameTestHelper helper) {
        helper.assertTrue(ModBlocks.GRID_ACCESS_DEVICE.isBound(), "Grid access device should be registered");
        ItemStack original = new ItemStack(ModItems.IDENTITY_DISC.get());
        UUID ownerId = UUID.fromString("44444444-4444-4444-4444-444444444444");
        IdentityDiscItem.bind(original, ownerId, "DiscThrower", 1_750_000_000_000L, UUID.randomUUID());
        original.setDamageValue(17);
        original.set(ModDataComponents.DISC_PROGRAMS.get(), new DiscPrograms(0, 0, 2, 3, 0, 0, 0, 0, 0, 0));

        var owner = helper.makeMockPlayer(GameType.SURVIVAL);
        IdentityDiscProjectile projectile = new IdentityDiscProjectile(helper.getLevel(), owner, original);
        helper.assertValueEqual(projectile.getItem().getDamageValue(), 17, "projectile disc damage");
        helper.assertValueEqual(
                projectile.getItem().get(ModDataComponents.DISC_IDENTITY.get()),
                original.get(ModDataComponents.DISC_IDENTITY.get()),
                "projectile disc identity"
        );

        Vec3 reflected = IdentityDiscProjectile.reflect(new Vec3(1.0, 2.0, 3.0), Direction.WEST, 0.5);
        helper.assertValueEqual(reflected, new Vec3(-0.5, 1.0, 1.5), "reflected disc velocity");
        helper.assertValueEqual(IdentityDiscProjectile.DEFAULT_RICOCHETS, 2, "default ricochet count");
        helper.assertValueEqual(projectile.getMaximumRicochets(), 5, "Ricochet III bounce count");
        projectile.setChargeProgress(1.0F);
        helper.assertValueEqual(projectile.getImpactDamage(), 13.0F, "Impact II full-charge damage");
        DiscPrograms programTest = DiscPrograms.EMPTY.upgrade(ProgramType.SEEKING);
        helper.assertTrue(!programTest.compatible(ProgramType.SPLIT_CIRCUIT), "Seeking and Split Circuit should conflict");
        helper.assertValueEqual(programTest.remove(ProgramType.SEEKING).level(ProgramType.SEEKING), 0, "Program removal");
        helper.assertTrue(
                IdentityDiscItem.getThrowPower(IdentityDiscItem.MIN_THROW_CHARGE_TICKS)
                        < IdentityDiscItem.getThrowPower(IdentityDiscItem.FULL_THROW_CHARGE_TICKS),
                "a fully charged disc should launch faster than a quick throw"
        );
        helper.assertValueEqual(IdentityDiscItem.getChargeProgress(IdentityDiscItem.FULL_THROW_CHARGE_TICKS), 1.0F, "full throw charge");
        helper.assertTrue(
                IdentityDiscProjectile.getImpactDamage(0.0F) < IdentityDiscProjectile.getImpactDamage(1.0F),
                "a fully charged disc should deal more damage than a quick throw"
        );
        helper.assertValueEqual(IdentityDiscProjectile.getImpactDamage(0.0F), 3.0F, "minimum throw damage");
        helper.assertValueEqual(IdentityDiscProjectile.getImpactDamage(1.0F), 9.0F, "maximum throw damage");

        SimpleContainer terminal = new SimpleContainer(3);
        terminal.setItem(IdentityTerminalBlockEntity.SLOT_DISC, original.copy());
        terminal.setItem(IdentityTerminalBlockEntity.SLOT_PROTOCOL, new ItemStack(ModItems.REBOUND_PROTOCOL.get()));
        owner.getInventory().add(new ItemStack(ModItems.GRID_SHARD.get()));
        helper.assertTrue(IdentityTerminalBlock.runProgramAction(owner, helper.getLevel(), helper.absolutePos(net.minecraft.core.BlockPos.ZERO), terminal, false), "terminal should process valid inputs");
        helper.assertTrue(terminal.getItem(IdentityTerminalBlockEntity.SLOT_DISC).isEmpty(), "terminal should consume the disc input");
        helper.assertTrue(terminal.getItem(IdentityTerminalBlockEntity.SLOT_PROTOCOL).isEmpty(), "terminal should consume the protocol input");
        helper.assertValueEqual(terminal.getItem(IdentityTerminalBlockEntity.SLOT_OUTPUT).getOrDefault(ModDataComponents.DISC_PROGRAMS.get(), DiscPrograms.EMPTY).level(ProgramType.REBOUND), 1, "terminal output program level");
        helper.succeed();
    }
}
