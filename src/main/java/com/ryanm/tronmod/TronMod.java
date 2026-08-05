package com.ryanm.tronmod;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(TronMod.MOD_ID)
public final class TronMod {
    public static final String MOD_ID = "tronmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TronMod(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Tron Mod is entering the Grid.");
    }
}
