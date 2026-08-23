package com.sakalti;

import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
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
import net.minecraftforge.eventbus.api.EventPriority;
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
     * BiomeLoadingEvent
     *
     * 重要:
     *
     * WorldGenRegistries.CONFIGURED_FEATURE から取得しない。
     *
     * FMLCommonSetupEvent / enqueueWork() で
     * ConfiguredFeature を後から登録する方式も使用しない。
     *
     * BiomeLoadingEvent が発生した時点で ConfiguredFeature を
     * その場で作成し、その実体を Supplier から返す。
     * ============================================================
     */

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoading(BiomeLoadingEvent event) {

        final Biome.Category category = event.getCategory();

        /*
         * --------------------------------------------------------
         * Nether
         * --------------------------------------------------------
         */

        if (category == Biome.Category.NETHER) {

            addOre(
                    event,
                    createIgnizOre()
            );

            addOre(
                    event,
                    createMomongaiteOre()
            );

            return;
        }

        /*
         * --------------------------------------------------------
         * End
         * --------------------------------------------------------
         */

        if (category == Biome.Category.THEEND) {

            addOre(
                    event,
                    createOuriteOre()
            );

            addOre(
                    event,
                    createHiroliteOre()
            );

            return;
        }

        /*
         * --------------------------------------------------------
         * Overworld
         * --------------------------------------------------------
         */

        addOre(
                event,
                createKaniliteOre()
        );

        addOre(
                event,
                createHachiliteOre()
        );

        addOre(
                event,
                createChiriteOre()
        );
    }

    /*
     * ============================================================
     * BiomeへConfiguredFeatureを追加
     *
     * Supplierから必ず渡されたfeature自身を返す。
     *
     * Registry lookup は一切行わない。
     * ============================================================
     */

    private static void addOre(
            BiomeLoadingEvent event,
            ConfiguredFeature<?, ?> feature
    ) {

        if (feature == null) {
            throw new IllegalStateException(
                    "TConX: Attempted to add a null ConfiguredFeature"
            );
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
}
