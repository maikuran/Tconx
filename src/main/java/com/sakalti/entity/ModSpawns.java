package com.sakalti.entity;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "sakalti", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSpawns {

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        if (event.getName() != null
                && event.getName().equals(Biomes.CRIMSON_FOREST.location())) {

            event.getSpawns().addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(
                            CrimsonFlyEntity.CRIMSON_FLY.get(),
                            20,
                            2,
                            4
                    )
            );
        }
    }
}
