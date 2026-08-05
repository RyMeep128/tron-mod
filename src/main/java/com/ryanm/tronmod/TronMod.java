package com.ryanm.tronmod;

import com.mojang.logging.LogUtils;
import com.ryanm.tronmod.gametest.ModGameTests;
import com.ryanm.tronmod.registry.ModCreativeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(TronMod.MOD_ID)
public final class TronMod {
    public static final String MOD_ID = "tronmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TronMod(IEventBus modEventBus, ModContainer modContainer) {
        ModCreativeTabs.register(modEventBus);
        ModGameTests.register(modEventBus);
        LOGGER.info("Tron Mod is entering the Grid.");
    }
}
