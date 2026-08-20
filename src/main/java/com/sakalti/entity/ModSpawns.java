package com.sakalti.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class ModSpawns {

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        if (event.getName() == null) {
            return;
        }

        if (!event.getName().equals(
                Biomes.CRIMSON_FOREST.getRegistryName()
        )) {
            return;
        }

        event.getSpawns()
                .getSpawner(EntityClassification.MONSTER)
                .add(
                        new Biome.SpawnListEntry(
                                CrimsonFlyEntity.CRIMSON_FLY.get(),
                                20,
                                2,
                                4
                        )
                );
    }
}
