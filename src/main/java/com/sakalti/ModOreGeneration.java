package com.sakalti;

import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModMetals.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModOreGeneration {

    // 生成対象の地面（通常は石：STONE_ORE_REPLACEABLES）
    private static final RuleTest BASE_STONE = OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD;

    // --- Ourite Ore (レア設定) ---
    // 硬度44.0の激レア鉱石
    // 鉱脈サイズ: 2 (1箇所にほぼ2個しか固まらない)
    // 生成高さ: Y = 5 ～ 16 (ダイヤと同等の深さ)
    // 確率(count): 1 (1チャンクに1回試行)
    public static ConfiguredFeature<?, ?> ORE_OURITE;

    // --- Hirolite Ore (ややレア設定) ---
    // 硬度41.0のレア鉱石
    // 鉱脈サイズ: 3
    // 生成高さ: Y = 5 ～ 24
    // 確率(count): 2
    public static ConfiguredFeature<?, ?> ORE_HIROLITE;

    public static void registerConfiguredFeatures() {
        // Ourite Ore の設定
        ORE_OURITE = Feature.ORE
            .withConfiguration(new OreFeatureConfig(
                BASE_STONE,
                ModMetals.OURITE_ORE.get().getDefaultState(),
                2 // 鉱脈あたりのブロック数 (Vein Size)
            ))
            .withPlacement(Placement.RANGE.configure(
                new TopSolidRangeConfig(5, 5, 16) // 高度 Y=5〜16
            ))
            .square()
            .count(1); // チャンクあたりの生成確率

        // Hirolite Ore の設定
        ORE_HIROLITE = Feature.ORE
            .withConfiguration(new OreFeatureConfig(
                BASE_STONE,
                ModMetals.HIROLITE_ORE.get().getDefaultState(),
                3 // 鉱脈あたりのブロック数
            ))
            .withPlacement(Placement.RANGE.configure(
                new TopSolidRangeConfig(5, 5, 24) // 高度 Y=5〜24
            ))
            .square()
            .count(2);
    }

    // バイオームが読み込まれる時にワールドへ鉱石を注入
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_OURITE);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_HIROLITE);
    }
}
