package com.ray0ffire.hoodie.client;

import com.ray0ffire.hoodie.HoodieMod;
import net.fabricmc.api.ClientModInitializer;

public class HoodieModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HoodieMod.LOGGER.info("Hoodie Mod client initialized!");
    }
}
