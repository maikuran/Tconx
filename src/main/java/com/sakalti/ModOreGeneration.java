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
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = ModMetals.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public final class ModOreGeneration {

    private ModOreGeneration() {
    }

    // ============================================================
    // RuleTest
    // ============================================================

    private static final RuleTest BASE_STONE =
            OreFeatureConfig.FillerBlockType.NATURAL_STONE;

    private static final RuleTest BASE_NETHER =
            OreFeatureConfig.FillerBlockType.NETHERRACK;

    private static final RuleTest BASE_END =
            new BlockMatchRuleTest(Blocks.END_STONE);


    // ============================================================
    // Configured Features
    //
    // static初期化時に生成・登録する
    // ============================================================

    public static final ConfiguredFeature<?, ?> ORE_KANILITE =
            register(
                    "ore_kanilite",
                    Feature.ORE.configured(
                            new OreFeatureConfig(
                                    BASE_STONE,
                                    ModMetals.KANILITE_ORE.get().defaultBlockState(),
                                    6
                            )
                    )
                    .decorated(
                            Placement.RANGE.configured(
                                    new TopSolidRangeConfig(10, 0, 50)
                            )
                    )
                    .decorated(
                            Placement.SQUARE.configured(
                                    NoPlacementConfig.INSTANCE
                            )
                    )
                    .count(6)
            );


    public static final ConfiguredFeature<?, ?> ORE_HACHILITE =
            register(
                    "ore_hachilite",
                    Feature.ORE.configured(
                            new OreFeatureConfig(
                                    BASE_STONE,
                                    ModMetals.HACHILITE_ORE.get().defaultBlockState(),
                                    8
                            )
                    )
                    .decorated(
                            Placement.RANGE.configured(
                                    new TopSolidRangeConfig(10, 0, 64)
                            )
                    )
                    .decorated(
                            Placement.SQUARE.configured(
                                    NoPlacementConfig.INSTANCE
                            )
                    )
                    .count(8)
            );


    public static final ConfiguredFeature<?, ?> ORE_CHIRITE =
            register(
                    "ore_chirite",
                    Feature.ORE.configured(
                            new OreFeatureConfig(
                                    BASE_STONE,
                                    ModMetals.CHIRITE_ORE.get().defaultBlockState(),
                                    6
                            )
                    )
                    .decorated(
                            Placement.RANGE.configured(
                                    new TopSolidRangeConfig(10, 0, 31)
                            )
                    )
                    .decorated(
                            Placement.SQUARE.configured(
                                    NoPlacementConfig.INSTANCE
                            )
                    )
                    .count(9)
            );


    // ============================================================
    // Nether
    // ============================================================

    public static final ConfiguredFeature<?, ?> ORE_IGNIZ =
            register(
                    "ore_igniz",
                    Feature.ORE.configured(
                            new OreFeatureConfig(
                                    BASE_NETHER,
                                    ModMetals.IGNIZ_ORE.get().defaultBlockState(),
                                    5
                            )
                    )
                    .decorated(
                            Placement.RANGE.configured(
                                    new TopSolidRangeConfig(10, 0, 110)
                            )
                    )
                    .decorated(
                            Placement.SQUARE.configured(
                                    NoPlacementConfig.INSTANCE
                            )
                    )
                    .count(4)
            );


    public static final ConfiguredFeature<?, ?> ORE_MOMONGAITE =
            register(
                    "ore_momongaite",
                    Feature.ORE.configured(
                            new OreFeatureConfig(
                                    BASE_NETHER,
                                    ModMetals.MOMONGAITE_ORE.get().defaultBlockState(),
                                    6
                            )
                    )
                    .decorated(
                            Placement.RANGE.configured(
                                    new TopSolidRangeConfig(10, 0, 110)
                            )
                    )
                    .decorated(
                            Placement.SQUARE.configured(
                                    NoPlacementConfig.INSTANCE
                            )
                    )
                    .count(5)
            );


    // ============================================================
    // End
    // ============================================================

    public static final ConfiguredFeature<?, ?> ORE_OURITE =
            register(
                    "ore_ourite",
                    Feature.ORE.configured(
                            new OreFeatureConfig(
                                    BASE_END,
                                    ModMetals.OURITE_ORE.get().defaultBlockState(),
                                    2
                            )
                    )
                    .decorated(
                            Placement.RANGE.configured(
                                    new TopSolidRangeConfig(10, 0, 70)
                            )
                    )
                    .decorated(
                            Placement.SQUARE.configured(
                                    NoPlacementConfig.INSTANCE
                            )
                    )
                    .count(1)
            );


    public static final ConfiguredFeature<?, ?> ORE_HIROLITE =
            register(
                    "ore_hirolite",
                    Feature.ORE.configured(
                            new OreFeatureConfig(
                                    BASE_END,
                                    ModMetals.HIROLITE_ORE.get().defaultBlockState(),
                                    3
                            )
                    )
                    .decorated(
                            Placement.RANGE.configured(
                                    new TopSolidRangeConfig(10, 0, 70)
                            )
                    )
                    .decorated(
                            Placement.SQUARE.configured(
                                    NoPlacementConfig.INSTANCE
                            )
                    )
                    .count(2)
            );


    // ============================================================
    // Registry
    // ============================================================

    private static <FC extends IFeatureConfig>
    ConfiguredFeature<FC, ?> register(
            String name,
            ConfiguredFeature<FC, ?> feature
    ) {
        return Registry.register(
                WorldGenRegistries.CONFIGURED_FEATURE,
                new ResourceLocation(ModMetals.MODID, name),
                feature
        );
    }


    // ============================================================
    // Biome Loading
    // ============================================================

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        Biome.Category category = event.getCategory();

        // --------------------------------------------------------
        // Nether
        // --------------------------------------------------------

        if (category == Biome.Category.NETHER) {

            event.getGeneration()
                    .getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
                    .add(ORE_IGNIZ);

            event.getGeneration()
                    .getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
                    .add(ORE_MOMONGAITE);

            return;
        }


        // --------------------------------------------------------
        // End
        // --------------------------------------------------------

        if (category == Biome.Category.THEEND) {

            event.getGeneration()
                    .getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
                    .add(ORE_OURITE);

            event.getGeneration()
                    .getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
                    .add(ORE_HIROLITE);

            return;
        }


        // --------------------------------------------------------
        // Overworld
        // --------------------------------------------------------

        event.getGeneration()
                .getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
                .add(ORE_KANILITE);

        event.getGeneration()
                .getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
                .add(ORE_HACHILITE);

        event.getGeneration()
                .getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES)
                .add(ORE_CHIRITE);
    }
}
