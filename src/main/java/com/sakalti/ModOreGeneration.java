package com.sakalti;

import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
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

    // === 地面（置換対象ブロック）の定義 ===
    // オーバーワールド（石）
    private static final RuleTest BASE_STONE = OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD;
    // ネザー（ネザーラック）
    private static final RuleTest BASE_NETHER = OreFeatureConfig.FillerBlockType.NETHERRACK;
    // エンド（エンドストーン）※1.16.5には標準で用意されていないため直接指定
    private static final RuleTest BASE_END = new BlockMatcher(Blocks.END_STONE);


    // === 1. Overworld 鉱石 ===
    public static ConfiguredFeature<?, ?> ORE_KANILITE;
    public static ConfiguredFeature<?, ?> ORE_HACHILITE;

    // === 2. Nether 鉱石 ===
    public static ConfiguredFeature<?, ?> ORE_IGNIZ;
    public static ConfiguredFeature<?, ?> ORE_MOMONGAITE;

    // === 3. The End 鉱石 ===
    public static ConfiguredFeature<?, ?> ORE_OURITE;
    public static ConfiguredFeature<?, ?> ORE_HIROLITE;


    public static void registerConfiguredFeatures() {

        // ------------------------------------------------------------
        // Overworld (Kanilite / Hachilite)
        // ------------------------------------------------------------
        ORE_KANILITE = Feature.ORE
            .withConfiguration(new OreFeatureConfig(BASE_STONE, ModMetals.KANILITE_ORE.get().getDefaultState(), 6))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 50)))
            .square().count(6);

        ORE_HACHILITE = Feature.ORE
            .withConfiguration(new OreFeatureConfig(BASE_STONE, ModMetals.HACHILITE_ORE.get().getDefaultState(), 8))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 64)))
            .square().count(8);

        ORE_CHIRITE = Feature.ORE
            .withConfiguration(new OreFeatureConfig(BASE_STONE, ModMetals.CHIRITE_ORE.get().getDefaultState(), 6))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 31)))
            .square().count(9);

        // ------------------------------------------------------------
        // Nether (Igniz / Momongaite)
        // ------------------------------------------------------------
        ORE_IGNIZ = Feature.ORE
            .withConfiguration(new OreFeatureConfig(BASE_NETHER, ModMetals.IGNIZ_ORE.get().getDefaultState(), 5))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 110)))
            .square().count(4);

        ORE_MOMONGAITE = Feature.ORE
            .withConfiguration(new OreFeatureConfig(BASE_NETHER, ModMetals.MOMONGAITE_ORE.get().getDefaultState(), 6))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 110)))
            .square().count(5);

        // ------------------------------------------------------------
        // The End (Ourite / Hirolite - レア設定)
        // ------------------------------------------------------------
        ORE_OURITE = Feature.ORE
            .withConfiguration(new OreFeatureConfig(BASE_END, ModMetals.OURITE_ORE.get().getDefaultState(), 2))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 70)))
            .square().count(1); // エンド島でのかなりレアな生成設定

        ORE_HIROLITE = Feature.ORE
            .withConfiguration(new OreFeatureConfig(BASE_END, ModMetals.HIROLITE_ORE.get().getDefaultState(), 3))
            .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 10, 70)))
            .square().count(2);
    }


    // === バイオームごとに生成する鉱石を振り分け ===
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        // 1. ネザーバイオームの判定
        if (event.getCategory() == net.minecraft.world.biome.Biome.Category.NETHER) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_IGNIZ);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_MOMONGAITE);
        }
        // 2. エンドバイオームの判定
        else if (event.getCategory() == net.minecraft.world.biome.Biome.Category.THEEND) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_OURITE);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_HIROLITE);
        }
        // 3. それ以外（オーバーワールド）
        else {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_KANILITE);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_CHIRITE);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_HACHILITE);
        }
    }
}
