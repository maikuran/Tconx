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

        // モンスターの自然スポーン候補だけを対象にする
        if (event.getMobCategory() != MobCategory.MONSTER) {
            return;
        }

        if (!(event.getLevel() instanceof Level level)) {
            return;
        }

        BlockPos pos = event.getPos();

        // その地点のBiome
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

        // Crimson Flyをスポーン候補に追加
        event.addSpawnerData(
                new MobSpawnSettings.SpawnerData(
                        CrimsonFlyEntity.CRIMSON_FLY.get(),
                        20,
                        2,
                        4
                )
        );
    }
}
