
package com.sakalti.datagen;

import com.sakalti.ModMain;
import com.sakalti.ModMetals;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(
        modid = ModMain.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public final class ModWorldGen {

    private ModWorldGen() {
    }

    /*
     * ============================================================
     * CONFIGURED FEATURES
     * ============================================================
     */

    public static final ResourceKey<ConfiguredFeature<?, ?>> HIROLITE_ORE =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(
                            ModMain.MODID,
                            "hirolite_ore"
                    )
            );

    public static final ResourceKey<ConfiguredFeature<?, ?>> OURITE_ORE =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(
                            ModMain.MODID,
                            "ourite_ore"
                    )
            );

    /*
     * ============================================================
     * PLACED FEATURES
     * ============================================================
     */

    public static final ResourceKey<PlacedFeature> HIROLITE_ORE_PLACED =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    new ResourceLocation(
                            ModMain.MODID,
                            "hirolite_ore"
                    )
            );

    public static final ResourceKey<PlacedFeature> OURITE_ORE_PLACED =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    new ResourceLocation(
                            ModMain.MODID,
                            "ourite_ore"
                    )
            );

    /*
     * ============================================================
     * BIOME MODIFIERS
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
     * CONFIGURED FEATURE BOOTSTRAP
     * ============================================================
     */

    private static void bootstrapConfiguredFeatures(
            BootstapContext<ConfiguredFeature<?, ?>> context
    ) {

        /*
         * Hirolite Ore
         *
         * 最大4個の鉱石で構成される鉱脈
         * オーバーワールドの石系ブロックを置換
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
         * Ourite Ore
         *
         * 最大4個の鉱石で構成される鉱脈
         * オーバーワールドの石系ブロックを置換
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
     * PLACED FEATURE BOOTSTRAP
     * ============================================================
     */

    private static void bootstrapPlacedFeatures(
            BootstapContext<PlacedFeature> context
    ) {

        /*
         * ここが重要。
         *
         * BootstapContext#lookup() は
         * HolderGetter を返す。
         *
         * RegistryLookup ではない。
         */

        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures =
                context.lookup(
                        Registries.CONFIGURED_FEATURE
                );

        /*
         * --------------------------------------------------------
         * Hirolite
         * --------------------------------------------------------
         *
         * 平均16回に1回
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
         * 平均16回に1回
         * Y=-64 ～ Y=16
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
     * BIOME MODIFIER BOOTSTRAP
     * ============================================================
     */

    private static void bootstrapBiomeModifiers(
            BootstapContext<BiomeModifier> context
    ) {

        /*
         * PlacedFeature の HolderGetter
         */

        HolderGetter<PlacedFeature> placedFeatures =
                context.lookup(
                        Registries.PLACED_FEATURE
                );

        /*
         * Biome の HolderGetter
         */

        HolderGetter<Biome> biomes =
                context.lookup(
                        Registries.BIOME
                );

        /*
         * オーバーワールドタグを取得。
         *
         * getOrThrow() ではなく get()。
         *
         * BiomeTags.IS_OVERWORLD は TagKey<Biome> なので、
         * HolderSet.Named<Biome> が返る。
         */

        HolderSet.Named<Biome> overworldBiomes =
                biomes.get(
                        BiomeTags.IS_OVERWORLD
                ).orElseThrow(
                        () -> new IllegalStateException(
                                "Missing biome tag: " + BiomeTags.IS_OVERWORLD
                        )
                );

        /*
         * --------------------------------------------------------
         * Hirolite
         * --------------------------------------------------------
         */

        context.register(
                ADD_HIROLITE_ORE,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        overworldBiomes,
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
                        overworldBiomes,
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
     * REGISTRY SET BUILDER
     * ============================================================
     */

    public static final RegistrySetBuilder BUILDER =
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
                     * Forge Biome Modifiers
                     */

                    .add(
                            ForgeRegistries.Keys.BIOME_MODIFIERS,
                            ModWorldGen::bootstrapBiomeModifiers
                    );

    /*
     * ============================================================
     * GATHER DATA
     * ============================================================
     */

    @SubscribeEvent
    public static void gatherData(
            GatherDataEvent event
    ) {

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
