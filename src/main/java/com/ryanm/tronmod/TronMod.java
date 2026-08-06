package com.ryanm.tronmod;

import com.mojang.logging.LogUtils;
import com.ryanm.tronmod.gametest.ModGameTests;
import com.ryanm.tronmod.registry.ModCreativeTabs;
import com.ryanm.tronmod.registry.ModBlocks;
import com.ryanm.tronmod.registry.ModDataComponents;
import com.ryanm.tronmod.registry.ModEntities;
import com.ryanm.tronmod.registry.ModItems;
import com.ryanm.tronmod.registry.ModWorldgen;
import com.ryanm.tronmod.registry.ModBlockEntities;
import com.ryanm.tronmod.registry.ModMenus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(TronMod.MOD_ID)
public final class TronMod {
    public static final String MOD_ID = "tronmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TronMod(IEventBus modEventBus, ModContainer modContainer) {
        ModDataComponents.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.register(modEventBus);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModWorldgen.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModGameTests.register(modEventBus);
        LOGGER.info("Tron Mod is entering the Grid.");
    }
}
