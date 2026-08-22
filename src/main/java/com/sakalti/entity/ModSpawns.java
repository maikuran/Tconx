package com.sakalti.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ModSpawns {

    /**
     * エンティティの属性登録
     */
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(
                CrimsonFlyEntity.CRIMSON_FLY.get(),
                CrimsonFlyEntity.createAttributes().build()
        );
    }

    /**
     * スポーン配置（Placement）の登録
     */
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            EntitySpawnPlacementRegistry.register(
                    CrimsonFlyEntity.CRIMSON_FLY.get(),
                    EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    (EntityType entityType, net.minecraft.world.IServerWorld world, net.minecraft.entity.SpawnReason spawnType, net.minecraft.util.math.BlockPos pos, java.util.Random random) -> 
                        MonsterEntity.checkMonsterSpawnRules((EntityType<? extends MonsterEntity>) entityType, world, spawnType, pos, random)
            );
        });
    }

    /**
     * 真紅の森に Crimson Fly の自然スポーンを追加
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

            // minecraft:crimson_forest バイオームか判定
            if (biomeName.equals(new ResourceLocation("minecraft", "crimson_forest"))) {
                event.getSpawns().getSpawner(EntityClassification.MONSTER).add(
                        new MobSpawnInfo.Spawners(
                                CrimsonFlyEntity.CRIMSON_FLY.get(),
                                20, // スポーン重み (Weight)
                                2,  // 最小パック数
                                4   // 最大パック数
                        )
                );
            }
        }
    }
}
