package com.sakalti;

import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ModMetals.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModOreGeneration {

    // === 置換対象ブロック（RuleTest）の定義 (1.16.5 Mojmap仕様) ===
    private static final RuleTest BASE_STONE = OreFeatureConfig.FillerBlockType.NATURAL_STONE;
    private static final RuleTest BASE_NETHER = OreFeatureConfig.FillerBlockType.NETHERRACK;
    private static final RuleTest BASE_END = new BlockMatchRuleTest(Blocks.END_STONE);

    // === 1. Overworld 鉱石（フィールド宣言） ===
    public static ConfiguredFeature<?, ?> ORE_KANILITE;
    public static ConfiguredFeature<?, ?> ORE_HACHILITE;
    public static ConfiguredFeature<?, ?> ORE_CHIRITE;

    // === 2. Nether 鉱石 ===
    public static ConfiguredFeature<?, ?> ORE_IGNIZ;
    public static ConfiguredFeature<?, ?> ORE_MOMONGAITE;

    // === 3. The End 鉱石 ===
    public static ConfiguredFeature<?, ?> ORE_OURITE;
    public static ConfiguredFeature<?, ?> ORE_HIROLITE;

    /**
     * FMLCommonSetupEvent 等から呼び出す登録メソッド
     */
    public static void registerConfiguredFeatures() {

        // ------------------------------------------------------------
        // Overworld
        // ------------------------------------------------------------
        ORE_KANILITE = register("ore_kanilite", Feature.ORE
            .configured(new OreFeatureConfig(BASE_STONE, ModMetals.KANILITE_ORE.get().defaultBlockState(), 6))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 50)))
            .decorated(Placement.SQUARE)
            .count(6));

        ORE_HACHILITE = register("ore_hachilite", Feature.ORE
            .configured(new OreFeatureConfig(BASE_STONE, ModMetals.HACHILITE_ORE.get().defaultBlockState(), 8))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 64)))
            .decorated(Placement.SQUARE)
            .count(8));

        ORE_CHIRITE = register("ore_chirite", Feature.ORE
            .configured(new OreFeatureConfig(BASE_STONE, ModMetals.CHIRITE_ORE.get().defaultBlockState(), 6))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 31)))
            .decorated(Placement.SQUARE)
            .count(9));

        // ------------------------------------------------------------
        // Nether
        // ------------------------------------------------------------
        ORE_IGNIZ = register("ore_igniz", Feature.ORE
            .configured(new OreFeatureConfig(BASE_NETHER, ModMetals.IGNIZ_ORE.get().defaultBlockState(), 5))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 110)))
            .decorated(Placement.SQUARE)
            .count(4));

        ORE_MOMONGAITE = register("ore_momongaite", Feature.ORE
            .configured(new OreFeatureConfig(BASE_NETHER, ModMetals.MOMONGAITE_ORE.get().defaultBlockState(), 6))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 110)))
            .decorated(Placement.SQUARE)
            .count(5));

        // ------------------------------------------------------------
        // The End
        // ------------------------------------------------------------
        ORE_OURITE = register("ore_ourite", Feature.ORE
            .configured(new OreFeatureConfig(BASE_END, ModMetals.OURITE_ORE.get().defaultBlockState(), 2))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 70)))
            .decorated(Placement.SQUARE)
            .count(1));

        ORE_HIROLITE = register("ore_hirolite", Feature.ORE
            .configured(new OreFeatureConfig(BASE_END, ModMetals.HIROLITE_ORE.get().defaultBlockState(), 3))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 70)))
            .decorated(Placement.SQUARE)
            .count(2));
    }

    /**
     * Java 8のジェネリクス型推論に対応したレジストリ登録ヘルパーメソッド
     */
    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(ModMetals.MODID, key), configuredFeature);
    }

    // === バイオームイベント（Java 8 ラムダ式による Supplier 渡し） ===
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        // 1. ネザーバイオーム
        if (event.getCategory() == Biome.Category.NETHER) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(new Supplier<ConfiguredFeature<?, ?>>() {
                @Override
                public ConfiguredFeature<?, ?> get() {
                    return ORE_IGNIZ;
                }
            });
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_MOMONGAITE);
        }
        // 2. エンドバイオーム
        else if (event.getCategory() == Biome.Category.THEEND) {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_OURITE);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_HIROLITE);
        }
        // 3. オーバーワールド
        else {
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_KANILITE);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_CHIRITE);
            event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> ORE_HACHILITE);
        }
    }
}
