package com.sakalti.datagen;

import com.sakalti.ModMain;
import com.sakalti.ModMetals;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(
        modid = ModMain.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE // BiomeLoadingEventはFORGEバスです
)
public final class ModWorldGen {

    private ModWorldGen() {}

    // 置換対象のブロック（オーバーワールドの石）
    public static final RuleTest BASE_STONE_OVERWORLD = new BlockMatchRuleTest(Blocks.STONE);

    public static ConfiguredFeature<?, ?> HIROLITE_ORE_CONFIGURED;
    public static ConfiguredFeature<?, ?> OURITE_ORE_CONFIGURED;

    // FMLCommonSetupEvent などで呼び出して登録します
    public static void registerFeatures() {

        /*
         * Hirolite Ore の設定
         * Vein Size (鉱脈サイズ): 4
         * Height (高さ): Y = 0 ～ 80 (1.16.5ではマイナス高度がないため)
         * Chance (頻度): 16
         */
        HIROLITE_ORE_CONFIGURED = Feature.ORE.configured(
                new OreFeatureConfig(
                        BASE_STONE_OVERWORLD,
                        ModMetals.HIROLITE_ORE.get().defaultBlockState(),
                        4 // 鉱脈サイズ
                )
        )
        .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(0, 0, 80)))
        .squared()
        .count(16); // 1チャンクあたりの生成試行回数

        /*
         * Ourite Ore の設定
         */
        OURITE_ORE_CONFIGURED = Feature.ORE.configured(
                new OreFeatureConfig(
                        BASE_STONE_OVERWORLD,
                        ModMetals.OURITE_ORE.get().defaultBlockState(),
                        4
                )
        )
        .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(0, 0, 80)))
        .squared()
        .count(16);

        // レジストリに登録
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, ModMain.MODID + ":hirolite_ore", HIROLITE_ORE_CONFIGURED);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, ModMain.MODID + ":ourite_ore", OURITE_ORE_CONFIGURED);
    }

    /*
     * バイオーム生成時に鉱石を追加するイベントリスナー
     */
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        // オーバーワールドのバイオーム（Nether / End 以外）にのみ生成
        if (event.getCategory() != net.minecraft.world.biome.Biome.Category.NETHER 
                && event.getCategory() != net.minecraft.world.biome.Biome.Category.THEEND) {

            event.getGeneration().addFeature(
                    GenerationStage.Decoration.UNDERGROUND_ORES,
                    HIROLITE_ORE_CONFIGURED
            );

            event.getGeneration().addFeature(
                    GenerationStage.Decoration.UNDERGROUND_ORES,
                    OURITE_ORE_CONFIGURED
            );
        }
    }
}
