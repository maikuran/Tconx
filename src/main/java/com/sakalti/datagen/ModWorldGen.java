
package com.sakalti.datagen;

import com.sakalti.ModMain;
import com.sakalti.ModMetals;

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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Sakalti world generation for Minecraft 1.20.1 / Forge 47.x.
 *
 * Generates:
 * - Configured features
 * - Placed features
 * - Forge biome modifiers
 */
@Mod.EventBusSubscriber(
    modid = ModMain.MODID,
    bus = Mod.EventBusSubscriber.Bus.MOD
)
public final class ModWorldGen {

    private ModWorldGen() {
    }

    private enum Dimension {
        OVERWORLD,
        NETHER,
        END
    }

    private record OreSpec(
        String name,
        RegistryObject<Block> block,
        int size,
        int minY,
        int maxY,
        int count,
        Dimension dimension
    ) {
        ResourceKey<ConfiguredFeature<?, ?>> configured() {
            return ResourceKey.create(
                Registries.CONFIGURED_FEATURE,
                id(name)
            );
        }

        ResourceKey<PlacedFeature> placed() {
            return ResourceKey.create(
                Registries.PLACED_FEATURE,
                id(name)
            );
        }

        ResourceKey<BiomeModifier> modifier() {
            return ResourceKey.create(
                ForgeRegistries.Keys.BIOME_MODIFIERS,
                id("add_" + name)
            );
        }
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(ModMain.MODID, path);
    }

    /*
     * Ore parameters:
     * name, block, vein size, minimum Y, maximum Y, count, dimension.
     */
    private static final List<OreSpec> ORES = List.of(
        new OreSpec(
            "hirolite_ore",
            ModMetals.HIROLITE_ORE,
            4, 8, 40, 6,
            Dimension.END
        ),
        new OreSpec(
            "ourite_ore",
            ModMetals.OURITE_ORE,
            3, 5, 32, 5,
            Dimension.END
        ),
        new OreSpec(
            "hachilite_ore",
            ModMetals.HACHILITE_ORE,
            6, 5, 80, 8,
            Dimension.OVERWORLD
        ),
        new OreSpec(
            "kanilite_ore",
            ModMetals.KANILITE_ORE,
            6, 5, 40, 8,
            Dimension.OVERWORLD
        ),
        new OreSpec(
            "chirite_ore",
            ModMetals.CHIRITE_ORE,
            4, 5, 24, 6,
            Dimension.OVERWORLD
        ),
        new OreSpec(
            "igniz_ore",
            ModMetals.IGNIZ_ORE,
            5, 5, 32, 7,
            Dimension.NETHER
        ),
        new OreSpec(
            "momongaite_ore",
            ModMetals.MOMONGAITE_ORE,
            4, 5, 30, 6,
            Dimension.NETHER
        ),
        new OreSpec(
            "tiberium_ore",
            ModMetals.TIBERIUM_ORE,
            4, 4, 32, 5,
            Dimension.NETHER
        ),
        new OreSpec(
            "seiren_ore",
            ModMetals.SEIREN_ORE,
            3, 4, 24, 3,
            Dimension.NETHER
        ),
        new OreSpec(
            "ostlum_ore",
            ModMetals.OSTLUM_ORE,
            5, 1, 8, 3,
            Dimension.OVERWORLD
        ),
        new OreSpec(
            "stannoidea_ore",
            ModMetals.STANNOIDEA_ORE,
            1, 1, 45, 1,
            Dimension.END
        ),
        new OreSpec(
            "sulphur_ore",
            ModMetals.SULPHUR_ORE,
            8, 4, 32, 8,
            Dimension.OVERWORLD
        )
    );

    /*
     * Configured features.
     *
     * IMPORTANT:
     * Explicitly provide discardChanceOnAirExposure.
     * The 1.20.1 JSON codec requires
     * "discard_chance_on_air_exposure".
     */
    private static void configured(
        BootstapContext<ConfiguredFeature<?, ?>> ctx
    ) {
        for (OreSpec ore : ORES) {
            Block oreBlock = ore.block().get();

            List<OreConfiguration.TargetBlockState> targets =
                new ArrayList<>();

            switch (ore.dimension()) {
                case OVERWORLD -> {
                    RuleTest stoneRule =
                        new TagMatchTest(
                            BlockTags.STONE_ORE_REPLACEABLES
                        );

                    RuleTest deepslateRule =
                        new TagMatchTest(
                            BlockTags.DEEPSLATE_ORE_REPLACEABLES
                        );

                    targets.add(
                        OreConfiguration.target(
                            stoneRule,
                            oreBlock.defaultBlockState()
                        )
                    );

                    targets.add(
                        OreConfiguration.target(
                            deepslateRule,
                            oreBlock.defaultBlockState()
                        )
                    );
                }

                case NETHER -> {
                    RuleTest netherRule =
                        new TagMatchTest(
                            BlockTags.BASE_STONE_NETHER
                        );

                    targets.add(
                        OreConfiguration.target(
                            netherRule,
                            oreBlock.defaultBlockState()
                        )
                    );
                }

                case END -> {
                    RuleTest endRule =
                        new BlockMatchTest(Blocks.END_STONE);

                    targets.add(
                        OreConfiguration.target(
                            endRule,
                            oreBlock.defaultBlockState()
                        )
                    );
                }
            }

            OreConfiguration configuration =
                new OreConfiguration(
                    targets,
                    ore.size(),
                    0.0F
                );

            ConfiguredFeature<?, ?> feature =
                new ConfiguredFeature<>(
                    Feature.ORE,
                    configuration
                );

            ctx.register(
                ore.configured(),
                feature
            );
        }
    }

    /*
     * Placed features.
     */
    private static void placed(
        BootstapContext<PlacedFeature> ctx
    ) {
        HolderGetter<ConfiguredFeature<?, ?>> configured =
            ctx.lookup(Registries.CONFIGURED_FEATURE);

        for (OreSpec ore : ORES) {
            PlacedFeature feature =
                new PlacedFeature(
                    configured.getOrThrow(ore.configured()),
                    List.of(
                        CountPlacement.of(ore.count()),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                            VerticalAnchor.absolute(ore.minY()),
                            VerticalAnchor.absolute(ore.maxY())
                        ),
                        BiomeFilter.biome()
                    )
                );

            ctx.register(
                ore.placed(),
                feature
            );
        }
    }

    /*
     * Forge biome modifiers.
     */
    private static void modifiers(
        BootstapContext<BiomeModifier> ctx
    ) {
        HolderGetter<PlacedFeature> placed =
            ctx.lookup(Registries.PLACED_FEATURE);

        HolderGetter<Biome> biomes =
            ctx.lookup(Registries.BIOME);

        for (OreSpec ore : ORES) {
            HolderSet.Named<Biome> targetBiomes =
                switch (ore.dimension()) {
                    case OVERWORLD ->
                        biomes.getOrThrow(
                            BiomeTags.IS_OVERWORLD
                        );

                    case NETHER ->
                        biomes.getOrThrow(
                            BiomeTags.IS_NETHER
                        );

                    case END ->
                        biomes.getOrThrow(
                            BiomeTags.IS_END
                        );
                };

            ForgeBiomeModifiers.AddFeaturesBiomeModifier modifier =
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                    targetBiomes,
                    HolderSet.direct(
                        placed.getOrThrow(ore.placed())
                    ),
                    GenerationStep.Decoration.UNDERGROUND_ORES
                );

            ctx.register(
                ore.modifier(),
                modifier
            );
        }
    }

    /*
     * Dynamic datapack registry builder.
     */
    public static final RegistrySetBuilder BUILDER =
        new RegistrySetBuilder()
            .add(
                Registries.CONFIGURED_FEATURE,
                ModWorldGen::configured
            )
            .add(
                Registries.PLACED_FEATURE,
                ModWorldGen::placed
            )
            .add(
                ForgeRegistries.Keys.BIOME_MODIFIERS,
                ModWorldGen::modifiers
            );

    /*
     * Register the datapack registry data generator.
     */
    @SubscribeEvent
    public static void gatherData(
        GatherDataEvent event
    ) {
        DatapackBuiltinEntriesProvider provider =
            new DatapackBuiltinEntriesProvider(
                event.getGenerator().getPackOutput(),
                event.getLookupProvider(),
                BUILDER,
                Set.of(ModMain.MODID)
            );

        event.getGenerator().addProvider(
            event.includeServer(),
            provider
        );
    }
}
