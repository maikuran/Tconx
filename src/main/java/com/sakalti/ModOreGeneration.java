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
    // 登録
    //
    // ModMain から一度だけ呼ぶ。
    // ============================================================

    public static void register() {
        MinecraftForge.EVENT_BUS.register(ModOreGeneration.class);
    }

    // ============================================================
    // Overworld
    // ============================================================

    private static ConfiguredFeature<?, ?> createKaniliteOre() {

        return Feature.ORE
                .configured(
                        new OreFeatureConfig(
                                BASE_STONE,
                                ModMetals.KANILITE_ORE
                                        .get()
                                        .defaultBlockState(),
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
                .count(6);
    }

    private static ConfiguredFeature<?, ?> createHachiliteOre() {

        return Feature.ORE
                .configured(
                        new OreFeatureConfig(
                                BASE_STONE,
                                ModMetals.HACHILITE_ORE
                                        .get()
                                        .defaultBlockState(),
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
                .count(8);
    }

    private static ConfiguredFeature<?, ?> createChiriteOre() {

        return Feature.ORE
                .configured(
                        new OreFeatureConfig(
                                BASE_STONE,
                                ModMetals.CHIRITE_ORE
                                        .get()
                                        .defaultBlockState(),
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
                .count(9);
    }

    // ============================================================
    // Nether
    // ============================================================

    private static ConfiguredFeature<?, ?> createIgnizOre() {

        return Feature.ORE
                .configured(
                        new OreFeatureConfig(
                                BASE_NETHER,
                                ModMetals.IGNIZ_ORE
                                        .get()
                                        .defaultBlockState(),
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
                .count(4);
    }

    private static ConfiguredFeature<?, ?> createMomongaiteOre() {

        return Feature.ORE
                .configured(
                        new OreFeatureConfig(
                                BASE_NETHER,
                                ModMetals.MOMONGAITE_ORE
                                        .get()
                                        .defaultBlockState(),
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
                .count(5);
    }

    // ============================================================
    // End
    // ============================================================

    private static ConfiguredFeature<?, ?> createOuriteOre() {

        return Feature.ORE
                .configured(
                        new OreFeatureConfig(
                                BASE_END,
                                ModMetals.OURITE_ORE
                                        .get()
                                        .defaultBlockState(),
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
                .count(1);
    }

    private static ConfiguredFeature<?, ?> createHiroliteOre() {

        return Feature.ORE
                .configured(
                        new OreFeatureConfig(
                                BASE_END,
                                ModMetals.HIROLITE_ORE
                                        .get()
                                        .defaultBlockState(),
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
                .count(2);
    }

    // ============================================================
    // BiomeLoadingEvent
    //
    // 重要:
    // このイベントは Forge EVENT BUS で受け取る。
    //
    // ConfiguredFeature を static フィールドに保存しない。
    // ============================================================

    @SubscribeEvent
    public static void onBiomeLoading(
            BiomeLoadingEvent event
    ) {

        Biome.Category category = event.getCategory();

        // ========================================================
        // Nether
        // ========================================================

        if (category == Biome.Category.NETHER) {

            addFeature(
                    event,
                    createIgnizOre()
            );

            addFeature(
                    event,
                    createMomongaiteOre()
            );

            return;
        }

        // ========================================================
        // End
        // ========================================================

        if (category == Biome.Category.THEEND) {

            addFeature(
                    event,
                    createOuriteOre()
            );

            addFeature(
                    event,
                    createHiroliteOre()
            );

            return;
        }

        // ========================================================
        // Overworld
        // ========================================================

        addFeature(
                event,
                createKaniliteOre()
        );

        addFeature(
                event,
                createHachiliteOre()
        );

        addFeature(
                event,
                createChiriteOre()
        );
    }

    // ============================================================
    // Biomeへ追加
    // ============================================================

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
