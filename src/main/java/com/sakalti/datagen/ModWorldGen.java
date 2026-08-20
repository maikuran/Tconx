package com.sakalti.datagen;

import com.sakalti.Sakalti;
import com.sakalti.registry.ModBlocks;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Sakalti.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModWorldGen {

    /*
     * ============================================================
     * Configured Feature
     * ============================================================
     */

    public static final ResourceKey<ConfiguredFeature<?, ?>> HIROLITE_ORE =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(
                            Sakalti.MODID,
                            "hirolite_ore"
                    )
            );

    public static final ResourceKey<ConfiguredFeature<?, ?>> OURITE_ORE =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(
                            Sakalti.MODID,
                            "ourite_ore"
                    )
            );


    /*
     * ============================================================
     * Placed Feature
     * ============================================================
     */

    public static final ResourceKey<PlacedFeature> HIROLITE_ORE_PLACED =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    new ResourceLocation(
                            Sakalti.MODID,
                            "hirolite_ore"
                    )
            );

    public static final ResourceKey<PlacedFeature> OURITE_ORE_PLACED =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    new ResourceLocation(
                            Sakalti.MODID,
                            "ourite_ore"
                    )
            );


    /*
     * ============================================================
     * Biome Modifier
     * ============================================================
     */

    public static final ResourceKey<BiomeModifier> ADD_HIROLITE_ORE =
            ResourceKey.create(
                    Registries.BIOME_MODIFIER,
                    new ResourceLocation(
                            Sakalti.MODID,
                            "add_hirolite_ore"
                    )
            );

    public static final ResourceKey<BiomeModifier> ADD_OURITE_ORE =
            ResourceKey.create(
                    Registries.BIOME_MODIFIER,
                    new ResourceLocation(
                            Sakalti.MODID,
                            "add_ourite_ore"
                    )
            );


    /*
     * ============================================================
     * RegistrySetBuilder
     * ============================================================
     */

    private static final RegistrySetBuilder BUILDER =
            new RegistrySetBuilder()

                    /*
                     * Configured Features
                     */
                    .add(
                            Registries.CONFIGURED_FEATURE,
                            ModWorldGen::bootstrapConfiguredFeatures
                    )

                    /*
                     * Placed Features
                     */
                    .add(
                            Registries.PLACED_FEATURE,
                            ModWorldGen::bootstrapPlacedFeatures
                    )

                    /*
                     * Biome Modifiers
                     */
                    .add(
                            Registries.BIOME_MODIFIER,
                            ModWorldGen::bootstrapBiomeModifiers
                    );


    /*
     * ============================================================
     * Configured Feature Bootstrap
     * ============================================================
     */

    private static void bootstrapConfiguredFeatures(
            BootstapContext<ConfiguredFeature<?, ?>> context
    ) {

        /*
         * --------------------------------------------------------
         * Hirolite
         * --------------------------------------------------------
         *
         * 最大3ブロック
         */
        OreConfiguration hiroliteConfiguration =
                new OreConfiguration(
                        List.of(
                                OreConfiguration.target(
                                        OreConfiguration.Predicates.STONE_ORE_REPLACEABLES,
                                        ModBlocks.HIROLITE_ORE
                                                .get()
                                                .defaultBlockState()
                                ),
                                OreConfiguration.target(
                                        OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES,
                                        ModBlocks.HIROLITE_ORE
                                                .get()
                                                .defaultBlockState()
                                )
                        ),
                        3
                );

        context.register(
                HIROLITE_ORE,
                new ConfiguredFeature<>(
                        Feature.ORE,
                        hiroliteConfiguration
                )
        );


        /*
         * --------------------------------------------------------
         * Ourite
         * --------------------------------------------------------
         *
         * 最大2ブロック
         */
        OreConfiguration ouriteConfiguration =
                new OreConfiguration(
                        List.of(
                                OreConfiguration.target(
                                        OreConfiguration.Predicates.STONE_ORE_REPLACEABLES,
                                        ModBlocks.OURITE_ORE
                                                .get()
                                                .defaultBlockState()
                                ),
                                OreConfiguration.target(
                                        OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES,
                                        ModBlocks.OURITE_ORE
                                                .get()
                                                .defaultBlockState()
                                )
                        ),
                        2
                );

        context.register(
                OURITE_ORE,
                new ConfiguredFeature<>(
                        Feature.ORE,
                        ouriteConfiguration
                )
        );
    }


    /*
     * ============================================================
     * Placed Feature Bootstrap
     * ============================================================
     */

    private static void bootstrapPlacedFeatures(
            BootstapContext<PlacedFeature> context
    ) {

        Holder<ConfiguredFeature<?, ?>> hirolite =
                context
                        .lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(HIROLITE_ORE);

        Holder<ConfiguredFeature<?, ?>> ourite =
                context
                        .lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(OURITE_ORE);


        /*
         * --------------------------------------------------------
         * Hirolite
         * --------------------------------------------------------
         *
         * 平均48チャンクに1回程度
         *
         * Y=-50 ～ -10
         */
        context.register(
                HIROLITE_ORE_PLACED,
                new PlacedFeature(
                        hirolite,
                        List.of(
                                RarityFilter.onAverageOnceEvery(48),

                                InSquarePlacement.spread(),

                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-50),
                                        VerticalAnchor.absolute(-10)
                                ),

                                BiomeFilter.biome()
                        )
                )
        );


        /*
         * --------------------------------------------------------
         * Ourite
         * --------------------------------------------------------
         *
         * 平均64チャンクに1回程度
         *
         * Y=-45 ～ -5
         */
        context.register(
                OURITE_ORE_PLACED,
                new PlacedFeature(
                        ourite,
                        List.of(
                                RarityFilter.onAverageOnceEvery(64),

                                InSquarePlacement.spread(),

                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-45),
                                        VerticalAnchor.absolute(-5)
                                ),

                                BiomeFilter.biome()
                        )
                )
        );
    }


    /*
     * ============================================================
     * Biome Modifier Bootstrap
     * ============================================================
     */

    private static void bootstrapBiomeModifiers(
            BootstapContext<BiomeModifier> context
    ) {

        Holder<PlacedFeature> hirolite =
                context
                        .lookup(Registries.PLACED_FEATURE)
                        .getOrThrow(HIROLITE_ORE_PLACED);

        Holder<PlacedFeature> ourite =
                context
                        .lookup(Registries.PLACED_FEATURE)
                        .getOrThrow(OURITE_ORE_PLACED);


        /*
         * --------------------------------------------------------
         * Hiroliteを全バイオームの地下鉱石生成へ追加
         * --------------------------------------------------------
         */
        context.register(
                ADD_HIROLITE_ORE,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(),
                        List.of(hirolite),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );


        /*
         * --------------------------------------------------------
         * Ouriteを全バイオームの地下鉱石生成へ追加
         * --------------------------------------------------------
         */
        context.register(
                ADD_OURITE_ORE,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(),
                        List.of(ourite),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );
    }


    /*
     * ============================================================
     * Datagen
     * ============================================================
     */

    @SubscribeEvent
    public static void gatherData(
            GatherDataEvent event
    ) {

        DataGenerator generator =
                event.getGenerator();

        PackOutput packOutput =
                generator.getPackOutput();

        CompletableFuture<net.minecraft.core.HolderLookup.Provider>
                lookupProvider =
                event.getLookupProvider();

        ExistingFileHelper existingFileHelper =
                event.getExistingFileHelper();


        /*
         * ========================================================
         * Worldgen JSON生成
         * ========================================================
         */

        generator.addProvider(
                event.includeServer(),
                new DatapackBuiltinEntriesProvider(
                        packOutput,
                        lookupProvider,
                        BUILDER,
                        Set.of(Sakalti.MODID)
                )
        );
    }
}
