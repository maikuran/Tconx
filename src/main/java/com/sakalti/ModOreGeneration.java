package com.sakalti;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Supplier;

public final class ModOreGeneration {

    private ModOreGeneration() {}

    // RuleTest
    private static final RuleTest BASE_STONE = OreFeatureConfig.FillerBlockType.NATURAL_STONE;
    private static final RuleTest BASE_NETHER = OreFeatureConfig.FillerBlockType.NETHERRACK;
    private static final RuleTest BASE_END = new BlockMatchRuleTest(Blocks.END_STONE);

    // ConfiguredFeature は呼び出された時点（必要になった瞬間）で生成するように Supplier にする
    public static final Supplier<ConfiguredFeature<?, ?>> KANILITE_ORE = () -> Feature.ORE
            .configured(new OreFeatureConfig(BASE_STONE, ModMetals.KANILITE_ORE.get().defaultBlockState(), 6))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 50)))
            .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
            .count(6);

    public static final Supplier<ConfiguredFeature<?, ?>> HACHILITE_ORE = () -> Feature.ORE
            .configured(new OreFeatureConfig(BASE_STONE, ModMetals.HACHILITE_ORE.get().defaultBlockState(), 8))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 64)))
            .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
            .count(8);

    public static final Supplier<ConfiguredFeature<?, ?>> CHIRITE_ORE = () -> Feature.ORE
            .configured(new OreFeatureConfig(BASE_STONE, ModMetals.CHIRITE_ORE.get().defaultBlockState(), 6))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 31)))
            .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
            .count(9);

    public static final Supplier<ConfiguredFeature<?, ?>> IGNIZ_ORE = () -> Feature.ORE
            .configured(new OreFeatureConfig(BASE_NETHER, ModMetals.IGNIZ_ORE.get().defaultBlockState(), 5))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 110)))
            .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
            .count(4);

    public static final Supplier<ConfiguredFeature<?, ?>> MOMONGAITE_ORE = () -> Feature.ORE
            .configured(new OreFeatureConfig(BASE_NETHER, ModMetals.MOMONGAITE_ORE.get().defaultBlockState(), 6))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 110)))
            .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
            .count(5);

    public static final Supplier<ConfiguredFeature<?, ?>> OURITE_ORE = () -> Feature.ORE
            .configured(new OreFeatureConfig(BASE_END, ModMetals.OURITE_ORE.get().defaultBlockState(), 2))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 70)))
            .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
            .count(1);

    public static final Supplier<ConfiguredFeature<?, ?>> HIROLITE_ORE = () -> Feature.ORE
            .configured(new OreFeatureConfig(BASE_END, ModMetals.HIROLITE_ORE.get().defaultBlockState(), 3))
            .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 70)))
            .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
            .count(2);

    // Main クラスの FMLCommonSetupEvent などから呼ぶ
    public static void register() {
        MinecraftForge.EVENT_BUS.register(ModOreGeneration.class);
    }

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();

        // Nether
        if (category == Biome.Category.NETHER) {
            addFeature(event, IGNIZ_ORE);
            addFeature(event, MOMONGAITE_ORE);
            return;
        }

        // End
        if (category == Biome.Category.THEEND) {
            addFeature(event, OURITE_ORE);
            addFeature(event, HIROLITE_ORE);
            return;
        }

        // Overworld (NONE や NETHER/THEEND 以外)
        if (category != Biome.Category.NETHER && category != Biome.Category.THEEND) {
            addFeature(event, KANILITE_ORE);
            addFeature(event, HACHILITE_ORE);
            addFeature(event, CHIRITE_ORE);
        }
    }

    private static void addFeature(BiomeLoadingEvent event, Supplier<ConfiguredFeature<?, ?>> supplier) {
        event.getGeneration()
                .getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
                .add(supplier);
    }
}
