package com.ray0ffire.hoodie;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HoodieMod implements ModInitializer {
    public static final String MOD_ID = "hoodie";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModArmorMaterials.register();
        ModItems.register();
        CoffeeCrashTracker.register();
        LOGGER.info("Hoodie Mod loaded! Stay cozy.");
    }
}
