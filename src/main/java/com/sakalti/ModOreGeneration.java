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

import java.util.function.Supplier;

public final class ModOreGeneration {

    private ModOreGeneration() {
    }

    private static final RuleTest BASE_STONE =
            OreFeatureConfig.FillerBlockType.NATURAL_STONE;

    private static final RuleTest BASE_NETHER =
            OreFeatureConfig.FillerBlockType.NETHERRACK;

    private static final RuleTest BASE_END =
            new BlockMatchRuleTest(Blocks.END_STONE);

    private static ConfiguredFeature<?, ?> ORE_KANILITE;
    private static ConfiguredFeature<?, ?> ORE_HACHILITE;
    private static ConfiguredFeature<?, ?> ORE_CHIRITE;

    private static ConfiguredFeature<?, ?> ORE_IGNIZ;
    private static ConfiguredFeature<?, ?> ORE_MOMONGAITE;

    private static ConfiguredFeature<?, ?> ORE_OURITE;
    private static ConfiguredFeature<?, ?> ORE_HIROLITE;

    /*
     * ModMainから呼ぶ。
     *
     * ここでは必ず enqueueWork を使用する。
     */
    public static void setup(net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {

        event.enqueueWork(new Runnable() {
            
            public void run() {

                ORE_KANILITE = register(
                        "ore_kanilite",
                        Feature.ORE
                                .configured(
                                        new OreFeatureConfig(
                                                BASE_STONE,
                                                ModMetals.KANILITE_ORE.get().defaultBlockState(),
                                                6
                                        )
                                )
                                .decorated(
                                        Placement.RANGE.configured(
                                                new TopSolidRangeConfig(
                                                        10,
                                                        0,
                                                        50
                                                )
                                        )
                                )
                                .decorated(
                                        Placement.SQUARE.configured(
                                                NoPlacementConfig.INSTANCE
                                        )
                                )
                                .count(6)
                );

                ORE_HACHILITE = register(
                        "ore_hachilite",
                        Feature.ORE
                                .configured(
                                        new OreFeatureConfig(
                                                BASE_STONE,
                                                ModMetals.HACHILITE_ORE.get().defaultBlockState(),
                                                8
                                        )
                                )
                                .decorated(
                                        Placement.RANGE.configured(
                                                new TopSolidRangeConfig(
                                                        10,
                                                        0,
                                                        64
                                                )
                                        )
                                )
                                .decorated(
                                        Placement.SQUARE.configured(
                                                NoPlacementConfig.INSTANCE
                                        )
                                )
                                .count(8)
                );

                ORE_CHIRITE = register(
                        "ore_chirite",
                        Feature.ORE
                                .configured(
                                        new OreFeatureConfig(
                                                BASE_STONE,
                                                ModMetals.CHIRITE_ORE.get().defaultBlockState(),
                                                6
                                        )
                                )
                                .decorated(
                                        Placement.RANGE.configured(
                                                new TopSolidRangeConfig(
                                                        10,
                                                        0,
                                                        31
                                                )
                                        )
                                )
                                .decorated(
                                        Placement.SQUARE.configured(
                                                NoPlacementConfig.INSTANCE
                                        )
                                )
                                .count(9)
                );

                ORE_IGNIZ = register(
                        "ore_igniz",
                        Feature.ORE
                                .configured(
                                        new OreFeatureConfig(
                                                BASE_NETHER,
                                                ModMetals.IGNIZ_ORE.get().defaultBlockState(),
                                                5
                                        )
                                )
                                .decorated(
                                        Placement.RANGE.configured(
                                                new TopSolidRangeConfig(
                                                        10,
                                                        0,
                                                        110
                                                )
                                        )
                                )
                                .decorated(
                                        Placement.SQUARE.configured(
                                                NoPlacementConfig.INSTANCE
                                        )
                                )
                                .count(4)
                );

                ORE_MOMONGAITE = register(
                        "ore_momongaite",
                        Feature.ORE
                                .configured(
                                        new OreFeatureConfig(
                                                BASE_NETHER,
                                                ModMetals.MOMONGAITE_ORE.get().defaultBlockState(),
                                                6
                                        )
                                )
                                .decorated(
                                        Placement.RANGE.configured(
                                                new TopSolidRangeConfig(
                                                        10,
                                                        0,
                                                        110
                                                )
                                        )
                                )
                                .decorated(
                                        Placement.SQUARE.configured(
                                                NoPlacementConfig.INSTANCE
                                        )
                                )
                                .count(5)
                );

                ORE_OURITE = register(
                        "ore_ourite",
                        Feature.ORE
                                .configured(
                                        new OreFeatureConfig(
                                                BASE_END,
                                                ModMetals.OURITE_ORE.get().defaultBlockState(),
                                                2
                                        )
                                )
                                .decorated(
                                        Placement.RANGE.configured(
                                                new TopSolidRangeConfig(
                                                        10,
                                                        0,
                                                        70
                                                )
                                        )
                                )
                                .decorated(
                                        Placement.SQUARE.configured(
                                                NoPlacementConfig.INSTANCE
                                        )
                                )
                                .count(1)
                );

                ORE_HIROLITE = register(
                        "ore_hirolite",
                        Feature.ORE
                                .configured(
                                        new OreFeatureConfig(
                                                BASE_END,
                                                ModMetals.HIROLITE_ORE.get().defaultBlockState(),
                                                3
                                        )
                                )
                                .decorated(
                                        Placement.RANGE.configured(
                                                new TopSolidRangeConfig(
                                                        10,
                                                        0,
                                                        70
                                                )
                                        )
                                )
                                .decorated(
                                        Placement.SQUARE.configured(
                                                NoPlacementConfig.INSTANCE
                                        )
                                )
                                .count(2)
                );
            }
        });
    }

    private static <FC extends IFeatureConfig>
    ConfiguredFeature<FC, ?> register(
            String name,
            ConfiguredFeature<FC, ?> feature
    ) {
        return Registry.register(
                WorldGenRegistries.CONFIGURED_FEATURE,
                new ResourceLocation(
                        ModMain.MODID,
                        name
                ),
                feature
        );
    }

    /*
     * これはForgeの通常イベントバスで呼ばれる。
     */
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        Biome.Category category = event.getCategory();

        if (category == Biome.Category.NETHER) {

            addFeature(event, ORE_IGNIZ);
            addFeature(event, ORE_MOMONGAITE);

            return;
        }

        if (category == Biome.Category.THEEND) {

            addFeature(event, ORE_OURITE);
            addFeature(event, ORE_HIROLITE);

            return;
        }

        addFeature(event, ORE_KANILITE);
        addFeature(event, ORE_HACHILITE);
        addFeature(event, ORE_CHIRITE);
    }

    private static void addFeature(
            BiomeLoadingEvent event,
            final ConfiguredFeature<?, ?> feature
    ) {

        if (feature == null) {
            return;
        }

        event.getGeneration()
                .getFeatures(
                        GenerationStage.Decoration.UNDERGROUND_ORES
                )
                .add(
                        new Supplier<ConfiguredFeature<?, ?>>() {
                            
                            public ConfiguredFeature<?, ?> get() {
                                return feature;
                            }
                        }
                );
    }
}
