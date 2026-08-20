
package com.sakalti.datagen;

import com.sakalti.ModMain;
import com.sakalti.ModMetals;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
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

import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraftforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(
        modid = ModMain.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public final class ModWorldGen {

    private ModWorldGen() {
    }

    /*
     * ============================================================
     * Configured Features
     * ============================================================
     */

    public static final ResourceKey<ConfiguredFeature<?, ?>> HIROLITE_ORE =
            ResourceKey.create(
                    net.minecraft.core.registries.Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(
                            ModMain.MODID,
                            "hirolite_ore"
                    )
            );

    public static final ResourceKey<ConfiguredFeature<?, ?>> OURITE_ORE =
            ResourceKey.create(
                    net.minecraft.core.registries.Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(
                            ModMain.MODID,
                            "ourite_ore"
                    )
            );

    /*
     * ============================================================
     * Placed Features
     * ============================================================
     */

    public static final ResourceKey<PlacedFeature> HIROLITE_ORE_PLACED =
            ResourceKey.create(
                    net.minecraft.core.registries.Registries.PLACED_FEATURE,
                    new ResourceLocation(
                            ModMain.MODID,
                            "hirolite_ore"
                    )
            );

    public static final ResourceKey<PlacedFeature> OURITE_ORE_PLACED =
            ResourceKey.create(
                    net.minecraft.core.registries.Registries.PLACED_FEATURE,
                    new ResourceLocation(
                            ModMain.MODID,
                            "ourite_ore"
                    )
            );

    /*
     * ============================================================
     * Biome Modifiers
     * ============================================================
     */

    public static final ResourceKey<BiomeModifier> ADD_HIROLITE_ORE =
            ResourceKey.create(
                    ForgeRegistries.Keys.BIOME_MODIFIERS,
                    new ResourceLocation(
                            ModMain.MODID,
                            "add_hirolite_ore"
                    )
            );

    public static final ResourceKey<BiomeModifier> ADD_OURITE_ORE =
            ResourceKey.create(
                    ForgeRegistries.Keys.BIOME_MODIFIERS,
                    new ResourceLocation(
                            ModMain.MODID,
                            "add_ourite_ore"
                    )
            );

    /*
     * ============================================================
     * Configured Feature
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
         * かなり希少
         * 最大4個の鉱石脈
         * オーバーワールド地下
         */

        context.register(
                HIROLITE_ORE,
                new ConfiguredFeature<>(
                        Feature.ORE,
                        new OreConfiguration(
                                List.of(
                                        OreConfiguration.target(
                                                BlockTags.BASE_STONE_OVERWORLD,
                                                ModMetals.HIROLITE_ORE
                                                        .get()
                                                        .defaultBlockState()
                                        )
                                ),
                                4
                        )
                )
        );

        /*
         * --------------------------------------------------------
         * Ourite
         * --------------------------------------------------------
         *
         * かなり希少
         * 最大4個の鉱石脈
         * オーバーワールド地下
         */

        context.register(
                OURITE_ORE,
                new ConfiguredFeature<>(
                        Feature.ORE,
                        new OreConfiguration(
                                List.of(
                                        OreConfiguration.target(
                                                BlockTags.BASE_STONE_OVERWORLD,
                                                ModMetals.OURITE_ORE
                                                        .get()
                                                        .defaultBlockState()
                                        )
                                ),
                                4
                        )
                )
        );
    }

    /*
     * ============================================================
     * Placed Features
     * ============================================================
     */

    private static void bootstrapPlacedFeatures(
            BootstapContext<PlacedFeature> context
    ) {

        HolderLookup.RegistryLookup<ConfiguredFeature<?, ?>> configuredFeatures =
                context.lookup(
                        net.minecraft.core.registries.Registries.CONFIGURED_FEATURE
                );

        /*
         * --------------------------------------------------------
         * Hirolite
         * --------------------------------------------------------
         *
         * RarityFilter:
         * 平均16チャンクに1回程度の試行
         *
         * Height:
         * Y=-64 ～ Y=16
         */

        context.register(
                HIROLITE_ORE_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(HIROLITE_ORE),
                        List.of(
                                RarityFilter.onAverageOnceEvery(16),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-64),
                                        VerticalAnchor.absolute(16)
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
         * Hirolite と同程度に希少。
         */

        context.register(
                OURITE_ORE_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(OURITE_ORE),
                        List.of(
                                RarityFilter.onAverageOnceEvery(16),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-64),
                                        VerticalAnchor.absolute(16)
                                ),
                                BiomeFilter.biome()
                        )
                )
        );
    }

    /*
     * ============================================================
     * Biome Modifiers
     * ============================================================
     */

    private static void bootstrapBiomeModifiers(
            BootstapContext<BiomeModifier> context
    ) {

        HolderLookup.RegistryLookup<PlacedFeature> placedFeatures =
                context.lookup(
                        net.minecraft.core.registries.Registries.PLACED_FEATURE
                );

        HolderLookup.RegistryLookup<net.minecraft.world.level.biome.Biome> biomes =
                context.lookup(
                        net.minecraft.core.registries.Registries.BIOME
                );

        /*
         * --------------------------------------------------------
         * Hirolite
         * --------------------------------------------------------
         */

        context.register(
                ADD_HIROLITE_ORE,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(
                                        net.minecraft.tags.BiomeTags.IS_OVERWORLD
                                )
                        ),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(
                                        HIROLITE_ORE_PLACED
                                )
                        ),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );

        /*
         * --------------------------------------------------------
         * Ourite
         * --------------------------------------------------------
         */

        context.register(
                ADD_OURITE_ORE,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(
                                        net.minecraft.tags.BiomeTags.IS_OVERWORLD
                                )
                        ),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(
                                        OURITE_ORE_PLACED
                                )
                        ),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );
    }

    /*
     * ============================================================
     * RegistrySetBuilder
     * ============================================================
     */

    public static final net.minecraft.core.RegistrySetBuilder BUILDER =
            new net.minecraft.core.RegistrySetBuilder()
                    .add(
                            net.minecraft.core.registries.Registries.CONFIGURED_FEATURE,
                            ModWorldGen::bootstrapConfiguredFeatures
                    )
                    .add(
                            net.minecraft.core.registries.Registries.PLACED_FEATURE,
                            ModWorldGen::bootstrapPlacedFeatures
                    )
                    .add(
                            ForgeRegistries.Keys.BIOME_MODIFIERS,
                            ModWorldGen::bootstrapBiomeModifiers
                    );

    /*
     * ============================================================
     * Gather Data
     * ============================================================
     */

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        event.getGenerator().addProvider(
                event.includeServer(),
                output -> new DatapackBuiltinEntriesProvider(
                        output,
                        event.getLookupProvider(),
                        BUILDER,
                        Set.of(ModMain.MODID)
                )
        );
    }
}
