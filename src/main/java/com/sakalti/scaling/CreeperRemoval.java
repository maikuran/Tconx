package com.sakalti.scaling;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "sakalti", bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class CreeperRemoval {
    private static final int CHECK_INTERVAL = 100;
    private static final double RANGE = 64.0D;

    /** Registered with vanilla GameRules; defaults to disabled. */
    public static final GameRules.Key<GameRules.BooleanValue> REMOVE_CREEPERS = GameRules.register(
            "sakaltiRemoveCreepers", GameRules.Category.MOBS, GameRules.BooleanValue.create(false));

    private CreeperRemoval() {}



    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
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
