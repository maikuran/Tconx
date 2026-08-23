package com.sakalti;

import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

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
    // ConfiguredFeature
    //
    // ここは最初は null。
    // FMLCommonSetupEvent の enqueueWork 内で生成・登録する。
    // ============================================================

    private static ConfiguredFeature<?, ?> KANILITE_ORE_FEATURE;
    private static ConfiguredFeature<?, ?> HACHILITE_ORE_FEATURE;
    private static ConfiguredFeature<?, ?> CHIRITE_ORE_FEATURE;

    private static ConfiguredFeature<?, ?> IGNIZ_ORE_FEATURE;
    private static ConfiguredFeature<?, ?> MOMONGAITE_ORE_FEATURE;

    private static ConfiguredFeature<?, ?> OURITE_ORE_FEATURE;
    private static ConfiguredFeature<?, ?> HIROLITE_ORE_FEATURE;

    // ============================================================
    // 初期化
    // ============================================================

    public static void register() {
        MinecraftForge.EVENT_BUS.register(ModOreGeneration.class);
    }

    // ============================================================
    // Common Setup
    // ============================================================

    public static void setup(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {

            // ====================================================
            // Overworld
            // ====================================================

            KANILITE_ORE_FEATURE =
                    registerConfiguredFeature(
                            "kanilite_ore",
                            createKaniliteOre()
                    );

            HACHILITE_ORE_FEATURE =
                    registerConfiguredFeature(
                            "hachilite_ore",
                            createHachiliteOre()
                    );

            CHIRITE_ORE_FEATURE =
                    registerConfiguredFeature(
                            "chirite_ore",
                            createChiriteOre()
                    );

            // ====================================================
            // Nether
            // ====================================================

            IGNIZ_ORE_FEATURE =
                    registerConfiguredFeature(
                            "igniz_ore",
                            createIgnizOre()
                    );

            MOMONGAITE_ORE_FEATURE =
                    registerConfiguredFeature(
                            "momongaite_ore",
                            createMomongaiteOre()
                    );

            // ====================================================
            // End
            // ====================================================

            OURITE_ORE_FEATURE =
                    registerConfiguredFeature(
                            "ourite_ore",
                            createOuriteOre()
                    );

            HIROLITE_ORE_FEATURE =
                    registerConfiguredFeature(
                            "hirolite_ore",
                            createHiroliteOre()
                    );
        });
    }

    // ============================================================
    // ConfiguredFeature 登録
    // ============================================================

    private static ConfiguredFeature<?, ?> registerConfiguredFeature(
            String name,
            ConfiguredFeature<?, ?> feature
    ) {

        if (feature == null) {
            throw new IllegalStateException(
                    "ConfiguredFeature is null: " + name
            );
        }

        return WorldGenRegistries.register(
                WorldGenRegistries.CONFIGURED_FEATURE,
                new ResourceLocation(
                        ModMain.MODID,
                        name
                ),
                feature
        );
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
    // ============================================================

    @SubscribeEvent
    public static void onBiomeLoading(
            BiomeLoadingEvent event
    ) {

        Biome.Category category =
                event.getCategory();

        // ========================================================
        // Nether
        // ========================================================

        if (category == Biome.Category.NETHER) {

            addFeature(
                    event,
                    IGNIZ_ORE_FEATURE
            );

            addFeature(
                    event,
                    MOMONGAITE_ORE_FEATURE
            );

            return;
        }

        // ========================================================
        // End
        // ========================================================

        if (category == Biome.Category.THEEND) {

            addFeature(
                    event,
                    OURITE_ORE_FEATURE
            );

            addFeature(
                    event,
                    HIROLITE_ORE_FEATURE
            );

            return;
        }

        // ========================================================
        // Overworld
        // ========================================================

        addFeature(
                event,
                KANILITE_ORE_FEATURE
        );

        addFeature(
                event,
                HACHILITE_ORE_FEATURE
        );

        addFeature(
                event,
                CHIRITE_ORE_FEATURE
        );
    }

    // ============================================================
    // Biomeへ追加
    // ============================================================

    private static void addFeature(
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

                            @Override
                            public ConfiguredFeature<?, ?> get() {
                                return feature;
                            }
                        }
                );
    }
}
