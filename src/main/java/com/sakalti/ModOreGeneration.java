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
        modid = ModMain.MODID,
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
    // static初期化時には絶対に ModMetals.*.get() しない
    // ============================================================

    private static ConfiguredFeature<?, ?> ORE_KANILITE;
    private static ConfiguredFeature<?, ?> ORE_HACHILITE;
    private static ConfiguredFeature<?, ?> ORE_CHIRITE;

    private static ConfiguredFeature<?, ?> ORE_IGNIZ;
    private static ConfiguredFeature<?, ?> ORE_MOMONGAITE;

    private static ConfiguredFeature<?, ?> ORE_OURITE;
    private static ConfiguredFeature<?, ?> ORE_HIROLITE;

    // ============================================================
    // 登録済みか
    // ============================================================

    private static boolean initialized = false;

    // ============================================================
    // ConfiguredFeature生成
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
                Feature.ORE.configured(
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
                Feature.ORE.configured(
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
                Feature.ORE.configured(
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

        // ========================================================
        // The End
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
                Feature.ORE.configured(
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

    // ============================================================
    // Registry登録
    // ============================================================

    private static <FC extends IFeatureConfig>
    ConfiguredFeature<FC, ?> register(
            String name,
            ConfiguredFeature<FC, ?> configuredFeature
    ) {

        return Registry.register(
                WorldGenRegistries.CONFIGURED_FEATURE,
                new ResourceLocation(
                        ModMain.MODID,
                        name
                ),
                configuredFeature
        );
    }

    // ============================================================
    // Biome Loading
    // ============================================================

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        // --------------------------------------------------------
        // まだConfiguredFeatureが生成されていない場合
        // --------------------------------------------------------

        if (!initialized) {
            return;
        }

        // --------------------------------------------------------
        // Nether
        // --------------------------------------------------------

        if (event.getCategory() == Biome.Category.NETHER) {

            addOre(
                    event,
                    ORE_IGNIZ
            );

            addOre(
                    event,
                    ORE_MOMONGAITE
            );

            return;
        }

        // --------------------------------------------------------
        // The End
        // --------------------------------------------------------

        if (event.getCategory() == Biome.Category.THEEND) {

            addOre(
                    event,
                    ORE_OURITE
            );

            addOre(
                    event,
                    ORE_HIROLITE
            );

            return;
        }

        // --------------------------------------------------------
        // Overworld
        // --------------------------------------------------------

        addOre(
                event,
                ORE_KANILITE
        );

        addOre(
                event,
                ORE_HACHILITE
        );

        addOre(
                event,
                ORE_CHIRITE
        );
    }

    // ============================================================
    // Ore追加
    // ============================================================

    private static void addOre(
            BiomeLoadingEvent event,
            ConfiguredFeature<?, ?> feature
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
