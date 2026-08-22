package com.sakalti.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ModSpawns {

    /**
     * エンティティの属性登録
     */
    @SubscribeEvent
    public static void onEntityAttributeCreation(
            EntityAttributeCreationEvent event
    ) {
        event.put(
                CrimsonFlyEntity.CRIMSON_FLY.get(),
                CrimsonFlyEntity.createAttributes().create()
        );
    }

    /**
     * 真紅の森にCrimson Flyの自然スポーンを追加
     */
    @Mod.EventBusSubscriber(
            modid = "sakalti",
            bus = Mod.EventBusSubscriber.Bus.FORGE
    )
    public static class BiomeSpawns {

        @SubscribeEvent
        public static void onBiomeLoading(BiomeLoadingEvent event) {

            ResourceLocation biomeName = event.getName();

            if (biomeName == null) {
                return;
            }

            if (!biomeName.equals(
                    new ResourceLocation(
                            "minecraft",
                            "crimson_forest"
                    )
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
}
