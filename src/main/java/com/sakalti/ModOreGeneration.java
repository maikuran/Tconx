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
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(
        modid = ModMain.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD
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
    // ConfiguredFeature
    // ============================================================

    private static ConfiguredFeature<?, ?> ORE_KANILITE;
    private static ConfiguredFeature<?, ?> ORE_HACHILITE;
    private static ConfiguredFeature<?, ?> ORE_CHIRITE;

    private static ConfiguredFeature<?, ?> ORE_IGNIZ;
    private static ConfiguredFeature<?, ?> ORE_MOMONGAITE;

    private static ConfiguredFeature<?, ?> ORE_OURITE;
    private static ConfiguredFeature<?, ?> ORE_HIROLITE;

    // ============================================================
    // 初期化完了フラグ
    // ============================================================

    private static boolean initialized = false;

    // ============================================================
    // Common Setup
    // ============================================================

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {

        event.enqueueWork(new Runnable() {
            
            public void run() {

                // ==================================================
                // Overworld
                // ==================================================

                ORE_KANILITE = register(
                        "ore_kanilite",
                        Feature.ORE.configured(
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
                        .count(6)
                );

                ORE_HACHILITE = register(
                        "ore_hachilite",
                        Feature.ORE.configured(
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
                        .count(8)
                );

                ORE_CHIRITE = register(
                        "ore_chirite",
                        Feature.ORE.configured(
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
                        .count(9)
                );

                // ==================================================
                // Nether
                // ==================================================

                ORE_IGNIZ = register(
                        "ore_igniz",
                        Feature.ORE.configured(
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
                        .count(4)
                );

                ORE_MOMONGAITE = register(
                        "ore_momongaite",
                        Feature.ORE.configured(
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
                        .count(5)
                );

                // ==================================================
                // End
                // ==================================================

                ORE_OURITE = register(
                        "ore_ourite",
                        Feature.ORE.configured(
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
                        .count(1)
                );

                ORE_HIROLITE = register(
                        "ore_hirolite",
                        Feature.ORE.configured(
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
                        .count(2)
                );

                // ==================================================
                // すべて完成してからtrue
                // ==================================================

                initialized = true;
            }
        });
    }

    // ============================================================
    // ConfiguredFeature Registry
    // ============================================================

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

    // ============================================================
    // BiomeLoadingEvent
    //
    // ここは Forge EVENT_BUS
    // ============================================================

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        // ============================================================
        // 初期化が終わっていなければ何もしない
        // ============================================================

        if (!initialized) {
            return;
        }

        // ============================================================
        // Underground Ores
        // ============================================================

        if (event.getCategory() == Biome.Category.NETHER) {

            addFeature(
                    event,
                    ORE_IGNIZ
            );

            addFeature(
                    event,
                    ORE_MOMONGAITE
            );

            return;
        }

        if (event.getCategory() == Biome.Category.THEEND) {

            addFeature(
                    event,
                    ORE_OURITE
            );

            addFeature(
                    event,
                    ORE_HIROLITE
            );

            return;
        }

        // ============================================================
        // Overworld
        // ============================================================

        addFeature(
                event,
                ORE_KANILITE
        );

        addFeature(
                event,
                ORE_HACHILITE
        );

        addFeature(
                event,
                ORE_CHIRITE
        );
    }

    // ============================================================
    // Feature追加
    // ============================================================

    private static void addFeature(
            BiomeLoadingEvent event,
            ConfiguredFeature<?, ?> feature
    ) {

        if (feature == null) {
            return;
        }

        Supplier<ConfiguredFeature<?, ?>> supplier =
                new Supplier<ConfiguredFeature<?, ?>>() {

                    
                    public ConfiguredFeature<?, ?> get() {
                        return feature;
                    }
                };

        event.getGeneration()
                .getFeatures(
                        GenerationStage.Decoration.UNDERGROUND_ORES
                )
                .add(supplier);
    }
}
