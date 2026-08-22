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

import java.util.function.Supplier;

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
    // ============================================================

    public static final ConfiguredFeature<?, ?> ORE_KANILITE =
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
            .count(6);

    public static final ConfiguredFeature<?, ?> ORE_HACHILITE =
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
            .count(8);

    public static final ConfiguredFeature<?, ?> ORE_CHIRITE =
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
            .count(9);

    // ============================================================
    // Nether
    // ============================================================

    public static final ConfiguredFeature<?, ?> ORE_IGNIZ =
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
            .count(4);

    public static final ConfiguredFeature<?, ?> ORE_MOMONGAITE =
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
            .count(5);

    // ============================================================
    // The End
    // ============================================================

    public static final ConfiguredFeature<?, ?> ORE_OURITE =
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
            .count(1);

    public static final ConfiguredFeature<?, ?> ORE_HIROLITE =
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
            .count(2);

    // ============================================================
    // Registry
    //
    // static 初期化時に直接登録
    // ============================================================

    static {
        register(
                "ore_kanilite",
                ORE_KANILITE
        );

        register(
                "ore_hachilite",
                ORE_HACHILITE
        );

        register(
                "ore_chirite",
                ORE_CHIRITE
        );

        register(
                "ore_igniz",
                ORE_IGNIZ
        );

        register(
                "ore_momongaite",
                ORE_MOMONGAITE
        );

        register(
                "ore_ourite",
                ORE_OURITE
        );

        register(
                "ore_hirolite",
                ORE_HIROLITE
        );
    }

    // ============================================================
    // Registry helper
    // ============================================================

    private static <FC extends IFeatureConfig>
    ConfiguredFeature<FC, ?> register(
            String name,
            ConfiguredFeature<FC, ?> feature
    ) {
        return Registry.register(
                WorldGenRegistries.CONFIGURED_FEATURE,
                new ResourceLocation(
                        ModMetals.MODID,
                        name
                ),
                feature
        );
    }

    // ============================================================
    // Biome generation
    // ============================================================

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        /*
         * getFeatures() は ConfiguredFeature 本体ではなく
         * Supplier<ConfiguredFeature<?, ?>> を要求する。
         *
         * そのため必ず () -> ORE_XXX の形で渡す。
         */

        // --------------------------------------------------------
        // Nether
        // --------------------------------------------------------

        if (event.getCategory() == Biome.Category.NETHER) {

            event.getGeneration()
                    .getFeatures(
                            GenerationStage.Decoration.UNDERGROUND_ORES
                    )
                    .add(
                            new Supplier<ConfiguredFeature<?, ?>>() {
                                @Override
                                public ConfiguredFeature<?, ?> get() {
                                    return ORE_IGNIZ;
                                }
                            }
                    );

            event.getGeneration()
                    .getFeatures(
                            GenerationStage.Decoration.UNDERGROUND_ORES
                    )
                    .add(
                            new Supplier<ConfiguredFeature<?, ?>>() {
                                @Override
                                public ConfiguredFeature<?, ?> get() {
                                    return ORE_MOMONGAITE;
                                }
                            }
                    );

            return;
        }

        // --------------------------------------------------------
        // The End
        // --------------------------------------------------------

        if (event.getCategory() == Biome.Category.THEEND) {

            event.getGeneration()
                    .getFeatures(
                            GenerationStage.Decoration.UNDERGROUND_ORES
                    )
                    .add(
                            new Supplier<ConfiguredFeature<?, ?>>() {
                                @Override
                                public ConfiguredFeature<?, ?> get() {
                                    return ORE_OURITE;
                                }
                            }
                    );

            event.getGeneration()
                    .getFeatures(
                            GenerationStage.Decoration.UNDERGROUND_ORES
                    )
                    .add(
                            new Supplier<ConfiguredFeature<?, ?>>() {
                                @Override
                                public ConfiguredFeature<?, ?> get() {
                                    return ORE_HIROLITE;
                                }
                            }
                    );

            return;
        }

        // --------------------------------------------------------
        // Overworld
        // --------------------------------------------------------

        event.getGeneration()
                .getFeatures(
                        GenerationStage.Decoration.UNDERGROUND_ORES
                )
                .add(
                        new Supplier<ConfiguredFeature<?, ?>>() {
                            @Override
                            public ConfiguredFeature<?, ?> get() {
                                return ORE_KANILITE;
                            }
                        }
                );

        event.getGeneration()
                .getFeatures(
                        GenerationStage.Decoration.UNDERGROUND_ORES
                )
                .add(
                        new Supplier<ConfiguredFeature<?, ?>>() {
                            @Override
                            public ConfiguredFeature<?, ?> get() {
                                return ORE_HACHILITE;
                            }
                        }
                );

        event.getGeneration()
                .getFeatures(
                        GenerationStage.Decoration.UNDERGROUND_ORES
                )
                .add(
                        new Supplier<ConfiguredFeature<?, ?>>() {
                            @Override
                            public ConfiguredFeature<?, ?> get() {
                                return ORE_CHIRITE;
                            }
                        }
                );
    }
}
