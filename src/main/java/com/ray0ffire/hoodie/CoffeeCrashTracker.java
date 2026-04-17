package com.ray0ffire.hoodie;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks pending "coffee crash" debuffs. When a player finishes a Coffee
 * Cup the item schedules a crash tick; when that tick arrives we apply
 * Slowness I and Mining Fatigue I to the player. Using world time instead
 * of a countdown keeps this cheap - the map only holds players who
 * actually have a crash pending.
 */
public final class CoffeeCrashTracker {
    private static final Map<UUID, Long> CRASH_AT = new ConcurrentHashMap<>();
    // 15s = 300 ticks, 10s = 200 ticks
    private static final int SLOWNESS_TICKS = 15 * 20;
    private static final int MINING_FATIGUE_TICKS = 10 * 20;

    private CoffeeCrashTracker() {}

    /** Schedule a crash at {@code crashTick} (world time, in ticks) for {@code playerUuid}. */
    public static void schedule(UUID playerUuid, long crashTick) {
        CRASH_AT.put(playerUuid, crashTick);
    }

    /** Registers the tick hook. Called once from the mod initializer. */
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (CRASH_AT.isEmpty()) return;
            long now = server.getOverworld().getTime();
            Iterator<Map.Entry<UUID, Long>> it = CRASH_AT.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<UUID, Long> entry = it.next();
                if (entry.getValue() > now) continue;
                it.remove();
                ServerPlayerEntity player = server.getPlayerManager().getPlayer(entry.getKey());
                if (player != null) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, SLOWNESS_TICKS, 0));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, MINING_FATIGUE_TICKS, 0));
                }
            }
        });
    }
}
