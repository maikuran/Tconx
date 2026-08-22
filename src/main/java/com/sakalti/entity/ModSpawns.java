package com.sakalti.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
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
                CrimsonFlyEntity.createAttributes().build() // create() ではなく build() が標準的ですが、どちらでも可
        );
    }

    /**
     * スポーン配置（Placement）の登録
     * ※ これを登録しておかないと、BiomeLoadingEvent で追加しても出現しないかクラッシュすることがあります
     */
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            EntitySpawnPlacementRegistry.register(
                    CrimsonFlyEntity.CRIMSON_FLY.get(),
                    EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, // 飛行型なら ON_GROUND や IN_AIR を用途に合わせて選択
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    MonsterEntity::checkMonsterSpawnRules // または独自に定義したスポーン条件メソッド
            );
        });
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

            // minecraft:crimson_forest バイオームか判定
            if (biomeName.equals(new ResourceLocation("minecraft", "crimson_forest"))) {
                event.getSpawns()
                        .getSpawner(EntityClassification.MONSTER)
                        .add(new Biome.SpawnListEntry(
                                CrimsonFlyEntity.CRIMSON_FLY.get(),
                                20, // スポーン重み (Weight)
                                2,  // 最小パック数
                                4   // 最大パック数
                        ));
            }
        }
    }
}
