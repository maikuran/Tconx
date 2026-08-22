package com.sakalti;

import net.minecraft.block.BlockState;
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

@Mod.EventBusSubscriber(modid = "sakalti", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModOreGeneration {

    // 生成対象のルール（通常は石：STONE_ORE_REPLACEABLES）
    private static final RuleTest BASE_STONE = OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD;

    // --- Ourite Ore (レア設定) ---
    // 鉱脈サイズ: 3 (1回に生成される最大数)
    // 生成高さ: Y = 5 ～ 20
    // レア度(count): 2 (1チャンクあたり約2回試行)
    public static ConfiguredFeature<?, ?> ORE_OURITE;

    // --- Hirorite Ore (さらにレア設定) ---
    // 鉱脈サイズ: 2
    // 生成高さ: Y = 5 ～ 16 (ダイヤと同等)
    // レア度(count): 1 (1チャンクあたり約1回試行)
    public static ConfiguredFeature<?, ?> ORE_HIRORITE;

    public static void registerConfiguredFeatures() {
        // Ourite Ore の設定定義
        ORE_OURITE = Feature.ORE
            .withConfiguration(new OreFeatureConfig(
                BASE_STONE,
                ModMetals.Ourite_ore.get().getDefaultState(),
                3 // 鉱脈あたりのブロック数 (Vein Size)
            ))
            .withPlacement(Placement.RANGE.configure(
                new TopSolidRangeConfig(5, 5, 20) // 最低Y, ベースY, 最大Y (5～20に生成)
            ))
            .square()
            .count(2); // 1チャンクあたりの生成試行回数

        // Hirorite Ore の設定定義
        ORE_HIRORITE = Feature.ORE
            .withConfiguration(new OreFeatureConfig(
                BASE_STONE,
                ModMetals.hirorite_ore.get().getDefaultState(),
                2 // 鉱脈あたりのブロック数
            ))
            .withPlacement(Placement.RANGE.configure(
                new TopSolidRangeConfig(5, 5, 16) // 5～16に生成
            ))
            .square()
            .count(1); // チャンクあたり1回（かなりレア）
    }

    // バイオームが読み込まれたタイミングでワールドに鉱石を追加
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        // ネザーやエンドを除外したい場合はここでバイオームカテゴリをチェックできます
        // 例: if (event.getCategory() != Biome.Category.NETHER) { ... }

        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_OURITE);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_HIRORITE);
    }
}
