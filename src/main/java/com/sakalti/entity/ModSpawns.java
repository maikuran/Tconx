package com.sakalti.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class ModSpawns {

    @SubscribeEvent
    public static void onPotentialSpawns(LevelEvent.PotentialSpawns event) {

        // モンスター以外は対象外
        if (event.getMobCategory() != MobCategory.MONSTER) {
            return;
        }

        if (!(event.getLevel() instanceof Level level)) {
            return;
        }

        BlockPos pos = event.getPos();

        ResourceLocation biomeId =
                level.getBiome(pos)
                        .unwrapKey()
                        .map(key -> key.location())
                        .orElse(null);

        if (biomeId == null) {
            return;
        }

        // 真紅の森だけ
        if (!biomeId.equals(
                new ResourceLocation(
                        "minecraft",
                        "crimson_forest"
                )
        )) {
            return;
        }

        event.getSpawnerData().add(
                new MobSpawnSettings.SpawnerData(
                        CrimsonFlyEntity.CRIMSON_FLY.get(),
                        20,
                        2,
                        4
                )
        );
    }
}
