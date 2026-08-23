package com.sakalti;

import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
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

    /*
     * ============================================================
     * RuleTest
     * ============================================================
     */

    private static final RuleTest OVERWORLD_STONE =
            OreFeatureConfig.FillerBlockType.NATURAL_STONE;

    private static final RuleTest NETHER_STONE =
            OreFeatureConfig.FillerBlockType.NETHERRACK;

    private static final RuleTest END_STONE =
            new BlockMatchRuleTest(Blocks.END_STONE);

    /*
     * ============================================================
     * Common Setup
     *
     * 注意:
     * FMLCommonSetupEvent は FORGE バスではなく MOD バス。
     *
     * そのため、このメソッドは ModMain 側から
     * MODイベントバスへ登録する。
     *
     * ============================================================
     */

    public static void setup(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {

            registerConfiguredFeature(
                    "kanilite_ore",
                    createKaniliteOre()
            );

            registerConfiguredFeature(
                    "hachilite_ore",
                    createHachiliteOre()
            );

            registerConfiguredFeature(
                    "chirite_ore",
                    createChiriteOre()
            );

            registerConfiguredFeature(
                    "igniz_ore",
                    createIgnizOre()
            );

            registerConfiguredFeature(
                    "momongaite_ore",
                    createMomongaiteOre()
            );

            registerConfiguredFeature(
                    "ourite_ore",
                    createOuriteOre()
            );

            registerConfiguredFeature(
                    "hirolite_ore",
                    createHiroliteOre()
            );
        });
    }

    /*
     * ============================================================
     * ConfiguredFeature registration
     * ============================================================
     */

    private static ConfiguredFeature<?, ?> registerConfiguredFeature(
            String name,
            ConfiguredFeature<?, ?> feature
    ) {

        if (feature == null) {
            throw new IllegalStateException(
                    "TConX: ConfiguredFeature is null: "
                            + ModMain.MODID
                            + ":"
                            + name
            );
        }

        ResourceLocation id =
                new ResourceLocation(
                        ModMain.MODID,
                        name
                );

        ConfiguredFeature<?, ?> existing =
                WorldGenRegistries.CONFIGURED_FEATURE.get(id);

        if (existing != null) {
            return existing;
        }

        return Registry.register(
                WorldGenRegistries.CONFIGURED_FEATURE,
                id,
                feature
        );
    }

    /*
     * ============================================================
     * Overworld
     * ============================================================
     */

    private static ConfiguredFeature<?, ?> createKaniliteOre() {

        return Feature.ORE.configured(
                new OreFeatureConfig(
                        OVERWORLD_STONE,
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

        return Feature.ORE.configured(
                new OreFeatureConfig(
                        OVERWORLD_STONE,
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

        return Feature.ORE.configured(
                new OreFeatureConfig(
                        OVERWORLD_STONE,
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

    /*
     * ============================================================
     * Nether
     * ============================================================
     */

    private static ConfiguredFeature<?, ?> createIgnizOre() {

        return Feature.ORE.configured(
                new OreFeatureConfig(
                        NETHER_STONE,
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

        return Feature.ORE.configured(
                new OreFeatureConfig(
                        NETHER_STONE,
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

    /*
     * ============================================================
     * End
     * ============================================================
     */

    private static ConfiguredFeature<?, ?> createOuriteOre() {

        return Feature.ORE.configured(
                new OreFeatureConfig(
                        END_STONE,
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

        return Feature.ORE.configured(
                new OreFeatureConfig(
                        END_STONE,
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

    /*
     * ============================================================
     * BiomeLoadingEvent
     * ============================================================
     *
     * ここは FORGE EVENT BUS。
     *
     * ============================================================
     */

    @SubscribeEvent
    public static void onBiomeLoading(
            BiomeLoadingEvent event
    ) {

        Biome.Category category =
                event.getCategory();

        /*
         * --------------------------------------------------------
         * Nether
         * --------------------------------------------------------
         */

        if (category == Biome.Category.NETHER) {

            addRegisteredFeature(
                    event,
                    "igniz_ore"
            );

            addRegisteredFeature(
                    event,
                    "momongaite_ore"
            );

            return;
        }

        /*
         * --------------------------------------------------------
         * End
         * --------------------------------------------------------
         */

        if (category == Biome.Category.THEEND) {

            addRegisteredFeature(
                    event,
                    "ourite_ore"
            );

            addRegisteredFeature(
                    event,
                    "hirolite_ore"
            );

            return;
        }

        /*
         * --------------------------------------------------------
         * Overworld
         * --------------------------------------------------------
         */

        addRegisteredFeature(
                event,
                "kanilite_ore"
        );

        addRegisteredFeature(
                event,
                "hachilite_ore"
        );

        addRegisteredFeature(
                event,
                "chirite_ore"
        );
    }

    /*
     * ============================================================
     * 登録済み ConfiguredFeature を取得してBiomeへ追加
     * ============================================================
     */

    private static void addRegisteredFeature(
            BiomeLoadingEvent event,
            String name
    ) {

        ResourceLocation id =
                new ResourceLocation(
                        ModMain.MODID,
                        name
                );

        ConfiguredFeature<?, ?> feature =
                WorldGenRegistries.CONFIGURED_FEATURE.get(id);

        /*
         * ========================================================
         * 最重要:
         *
         * null の ConfiguredFeature を絶対に Supplier に入れない。
         *
         * ========================================================
         */

        if (feature == null) {
            return;
        }

        /*
         * ========================================================
         * 登録済みオブジェクトをローカル変数に保持。
         *
         * get() が null を返す Supplier を作らない。
         * ========================================================
         */

        final ConfiguredFeature<?, ?> registeredFeature =
                feature;

        event.getGeneration()
                .getFeatures(
                        GenerationStage.Decoration.UNDERGROUND_ORES
                )
                .add(
                        new Supplier<ConfiguredFeature<?, ?>>() {

                            @Override
                            public ConfiguredFeature<?, ?> get() {
                                return registeredFeature;
                            }
                        }
                );
    }
}
