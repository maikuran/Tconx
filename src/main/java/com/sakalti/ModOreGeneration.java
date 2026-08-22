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

    private static final String MODID = "sakalti";

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
    // 重要:
    // ここでは初期化しない。
    // ModMetals の RegistryObject.get() は CommonSetup 後に行う。
    // ============================================================

    private static ConfiguredFeature<?, ?> ORE_KANILITE;
    private static ConfiguredFeature<?, ?> ORE_HACHILITE;
    private static ConfiguredFeature<?, ?> ORE_CHIRITE;

    private static ConfiguredFeature<?, ?> ORE_IGNIZ;
    private static ConfiguredFeature<?, ?> ORE_MOMONGAITE;

    private static ConfiguredFeature<?, ?> ORE_OURITE;
    private static ConfiguredFeature<?, ?> ORE_HIROLITE;

    private static boolean initialized = false;

    // ============================================================
    // ConfiguredFeature 登録
    //
    // FMLCommonSetupEvent から呼び出す
    // ============================================================

    public static void registerConfiguredFeatures() {

        if (initialized) {
            return;
        }

        initialized = true;

        // ========================================================
        // Overworld
        // ========================================================

        ORE_KANILITE = register(
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

        ORE_HACHILITE = register(
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

        ORE_CHIRITE = register(
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

        // ========================================================
        // Nether
        // ========================================================

        ORE_IGNIZ = register(
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

        ORE_MOMONGAITE = register(
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

        // ========================================================
        // End
        // ========================================================

        ORE_OURITE = register(
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

        ORE_HIROLITE = register(
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
                new ResourceLocation(MODID, name),
                feature
        );
    }

    // ============================================================
    // Null防止
    // ============================================================

    private static boolean isInitialized() {
        return initialized
                && ORE_KANILITE != null
                && ORE_HACHILITE != null
                && ORE_CHIRITE != null
                && ORE_IGNIZ != null
                && ORE_MOMONGAITE != null
                && ORE_OURITE != null
                && ORE_HIROLITE != null;
    }

    // ============================================================
    // BiomeLoadingEvent
    // ============================================================

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        if (!isInitialized()) {
            return;
        }

        Biome.Category category = event.getCategory();

        // ========================================================
        // Nether
        // ========================================================

        if (category == Biome.Category.NETHER) {

            event.getGeneration()
                    .getFeatures(
                            GenerationStage.Decoration.UNDERGROUND_ORES
                    )
                    .add(new Supplier<ConfiguredFeature<?, ?>>() {
                        
                        public ConfiguredFeature<?, ?> get() {
                            return ORE_IGNIZ;
                        }
                    });

            event.getGeneration()
                    .getFeatures(
                            GenerationStage.Decoration.UNDERGROUND_ORES
                    )
                    .add(new Supplier<ConfiguredFeature<?, ?>>() {
                        
                        public ConfiguredFeature<?, ?> get() {
                            return ORE_MOMONGAITE;
                        }
                    });

            return;
        }

        // ========================================================
        // End
        // ========================================================

        if (category == Biome.Category.THEEND) {

            event.getGeneration()
                    .getFeatures(
                            GenerationStage.Decoration.UNDERGROUND_ORES
                    )
                    .add(new Supplier<ConfiguredFeature<?, ?>>() {
                        
                        public ConfiguredFeature<?, ?> get() {
                            return ORE_OURITE;
                        }
                    });

            event.getGeneration()
                    .getFeatures(
                            GenerationStage.Decoration.UNDERGROUND_ORES
                    )
                    .add(new Supplier<ConfiguredFeature<?, ?>>() {
                        
                        public ConfiguredFeature<?, ?> get() {
                            return ORE_HIROLITE;
                        }
                    });

            return;
        }

        // ========================================================
        // Overworld
        // ========================================================

        event.getGeneration()
                .getFeatures(
                        GenerationStage.Decoration.UNDERGROUND_ORES
                )
                .add(new Supplier<ConfiguredFeature<?, ?>>() {
                    
                    public ConfiguredFeature<?, ?> get() {
                        return ORE_KANILITE;
                    }
                });

        event.getGeneration()
                .getFeatures(
                        GenerationStage.Decoration.UNDERGROUND_ORES
                )
                .add(new Supplier<ConfiguredFeature<?, ?>>() {
                    
                    public ConfiguredFeature<?, ?> get() {
                        return ORE_HACHILITE;
                    }
                });

        event.getGeneration()
                .getFeatures(
                        GenerationStage.Decoration.UNDERGROUND_ORES
                )
                .add(new Supplier<ConfiguredFeature<?, ?>>() {
                    
                    public ConfiguredFeature<?, ?> get() {
                        return ORE_CHIRITE;
                    }
                });
    }
}
