package com.sakalti.datagen;

import com.sakalti.ModMain;
import com.sakalti.ModMetals;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.data.PackOutput;
import java.util.List;
import java.util.Set;

/** 1.16.5 ore-generation rules ported to the 1.20.1 datapack registry system. */
@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModWorldGen {
  private ModWorldGen() {}
  private enum Dimension { OVERWORLD, NETHER, END }
  private record OreSpec(String name, RegistryObject<Block> block, int size, int minY, int maxY, int count, Dimension dimension) {
    ResourceKey<ConfiguredFeature<?, ?>> configured() { return ResourceKey.create(Registries.CONFIGURED_FEATURE, id(name)); }
    ResourceKey<PlacedFeature> placed() { return ResourceKey.create(Registries.PLACED_FEATURE, id(name)); }
    ResourceKey<BiomeModifier> modifier() { return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, id("add_" + name)); }
  }
  private static ResourceLocation id(String path) { return new ResourceLocation(ModMain.MODID, path); }
  private static final List<OreSpec> ORES = List.of(
    // Arguments are size, minY, maxY, attempts/count, transcribed from 1.16.5 bytecode.
    new OreSpec("hirolite_ore", ModMetals.HIROLITE_ORE, 4, 8, 40, 6, Dimension.END),
    new OreSpec("ourite_ore", ModMetals.OURITE_ORE, 3, 5, 32, 5, Dimension.END),
    new OreSpec("hachilite_ore", ModMetals.HACHILITE_ORE, 6, 5, 80, 8, Dimension.OVERWORLD),
    new OreSpec("kanilite_ore", ModMetals.KANILITE_ORE, 6, 5, 40, 8, Dimension.OVERWORLD),
    new OreSpec("chirite_ore", ModMetals.CHIRITE_ORE, 4, 5, 24, 6, Dimension.OVERWORLD),
    new OreSpec("igniz_ore", ModMetals.IGNIZ_ORE, 5, 5, 32, 7, Dimension.NETHER),
    new OreSpec("momongaite_ore", ModMetals.MOMONGAITE_ORE, 4, 5, 30, 6, Dimension.NETHER),
    new OreSpec("tiberium_ore", ModMetals.TIBERIUM_ORE, 4, 4, 32, 5, Dimension.NETHER),
    new OreSpec("seiren_ore", ModMetals.SEIREN_ORE, 3, 4, 24, 3, Dimension.NETHER),
    new OreSpec("ostlum_ore", ModMetals.OSTLUM_ORE, 5, 1, 8, 3, Dimension.OVERWORLD),
    new OreSpec("stannoidea_ore", ModMetals.STANNOIDEA_ORE, 1, 1, 45, 1, Dimension.END),
    new OreSpec("sulphur_ore", ModMetals.SULPHUR_ORE, 8, 4, 32, 8, Dimension.OVERWORLD)
  );
  private static void configured(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
    for (OreSpec ore : ORES) {
      RuleTest rule = switch (ore.dimension()) {
        case OVERWORLD -> new net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        case NETHER -> new net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest(BlockTags.BASE_STONE_NETHER);
        case END -> new BlockMatchTest(Blocks.END_STONE);
      };
      ctx.register(ore.configured(), new ConfiguredFeature<>(Feature.ORE,
        new OreConfiguration(List.of(OreConfiguration.target(rule, ore.block().get().defaultBlockState())), ore.size())));
    }
  }
  private static void placed(BootstapContext<PlacedFeature> ctx) {
    HolderGetter<ConfiguredFeature<?, ?>> configured = ctx.lookup(Registries.CONFIGURED_FEATURE);
    for (OreSpec ore : ORES) {
      // Keep the per-ore count and vertical range from the 1.16.5 generator.
      ctx.register(ore.placed(), new PlacedFeature(configured.getOrThrow(ore.configured()), List.of(
        CountPlacement.of(ore.count()), InSquarePlacement.spread(),
        HeightRangePlacement.uniform(VerticalAnchor.absolute(ore.minY()), VerticalAnchor.absolute(ore.maxY())),
        BiomeFilter.biome())));
    }
  }
  private static void modifiers(BootstapContext<BiomeModifier> ctx) {
    HolderGetter<PlacedFeature> placed = ctx.lookup(Registries.PLACED_FEATURE);
    HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);
    for (OreSpec ore : ORES) {
      HolderSet.Named<Biome> target = switch (ore.dimension()) {
        case OVERWORLD -> biomes.getOrThrow(BiomeTags.IS_OVERWORLD);
        case NETHER -> biomes.getOrThrow(BiomeTags.IS_NETHER);
        case END -> biomes.getOrThrow(BiomeTags.IS_END);
      };
      ctx.register(ore.modifier(), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(target,
        HolderSet.direct(placed.getOrThrow(ore.placed())), GenerationStep.Decoration.UNDERGROUND_ORES));
    }
  }
  public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
    .add(Registries.CONFIGURED_FEATURE, ModWorldGen::configured)
    .add(Registries.PLACED_FEATURE, ModWorldGen::placed)
    .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModWorldGen::modifiers);
  @SubscribeEvent public static void gatherData(GatherDataEvent event) {
    DatapackBuiltinEntriesProvider provider = new DatapackBuiltinEntriesProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), BUILDER, Set.of(ModMain.MODID));
    event.getGenerator().addProvider(event.includeServer(), provider);
  }
}
