package com.ray0ffire.hoodie.clientonly;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HoodieClientOnlyMod implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("hoodie-clientonly");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hoodie Client-Only Replacer loaded! All chestplates -> black hoodie, all leggings -> black tights.");
    }
}
