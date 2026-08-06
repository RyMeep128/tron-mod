package com.ryanm.tronmod.registry;

import com.ryanm.tronmod.TronMod;
import com.ryanm.tronmod.block.entity.IdentityTerminalBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> TYPES=DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,TronMod.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<IdentityTerminalBlockEntity>> IDENTITY_TERMINAL=TYPES.register("identity_terminal",()->new BlockEntityType<>(IdentityTerminalBlockEntity::new,java.util.Set.of(ModBlocks.IDENTITY_TERMINAL.get())));
    private ModBlockEntities(){}
    public static void register(IEventBus bus){TYPES.register(bus);}
}
