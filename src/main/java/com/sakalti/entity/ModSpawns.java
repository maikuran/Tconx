package com.sakalti.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.world.BiomeLoadingEvent;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class ModSpawns {

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        // 真紅の森だけ
        if (event.getName() == null
                || !event.getName().equals(Biomes.CRIMSON_FOREST.getRegistryName())) {
            return;
        }

        // モンスターの自然スポーン候補に Crimson Fly を追加
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
