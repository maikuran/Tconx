package com.sakalti.scaling;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.RegisterGameRulesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "sakalti", bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class CreeperRemoval {
    private static final int CHECK_INTERVAL = 100;
    private static final double RANGE = 64.0D;

    /** Registered on the mod event bus; defaults to disabled. */
    public static GameRules.Key<GameRules.BooleanValue> REMOVE_CREEPERS;

    private CreeperRemoval() {}

    public static void registerGameRule(RegisterGameRulesEvent event) {
        REMOVE_CREEPERS = event.register(
                "sakaltiRemoveCreepers",
                GameRules.Category.MOBS,
                GameRules.BooleanValue.create(false)
        );
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || REMOVE_CREEPERS == null) return;
        MinecraftServer server = event.getServer();
        if (server.getTickCount() % CHECK_INTERVAL != 0) return;

        for (ServerLevel level : server.getAllLevels()) {
            if (!level.getGameRules().getBoolean(REMOVE_CREEPERS)) continue;
            for (ServerPlayer player : level.players()) {
                AABB bounds = player.getBoundingBox().inflate(RANGE);
                level.getEntitiesOfClass(Creeper.class, bounds, Creeper::isAlive)
                        .forEach(Creeper::discard);
            }
        }
    }
}
