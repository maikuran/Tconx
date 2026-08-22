package com.sakalti.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ModSpawns {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            EntitySpawnPlacementRegistry.<CrimsonFlyEntity>register(
                    CrimsonFlyEntity.CRIMSON_FLY.get(),
                    EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    (entityType, world, spawnType, pos, random) ->
                            MonsterEntity.checkMonsterSpawnRules(
                                    (EntityType<? extends MonsterEntity>) (EntityType<?>) entityType,
                                    world,
                                    spawnType,
                                    pos,
                                    random
                            )
            );
        });
    }

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

            if (biomeName.equals(
                    new ResourceLocation("minecraft", "crimson_forest"))) {

                event.getSpawns()
                        .getSpawner(EntityClassification.MONSTER)
                        .add(
                                new MobSpawnInfo.Spawners(
                                        CrimsonFlyEntity.CRIMSON_FLY.get(),
                                        20,
                                        2,
                                        4
                                )
                        );
            }
        }
    }
}
