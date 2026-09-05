package com.sakalti;

import com.sakalti.modifier.TconxModifiers;
import com.sakalti.entity.*;
import com.sakalti.scaling.HealthCrystals;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModMain.MODID)
public final class ModMain {

    public static final String MODID = "sakalti";

    public ModMain() {

        IEventBus modEventBus =
                FMLJavaModLoadingContext.get().getModEventBus();

        /*
         * アイテム・ブロック
         */
        ModMetals.register(modEventBus);

        /*
         * 溶融液体
         */
        ModFluids.register(modEventBus);

        /*
         * クリムゾンフライ
         */
        CrimsonFlyEntity.register(modEventBus);

        /*
         * クリエイティブタブ
         */
        ModCreativeTabs.register(modEventBus);

        /*
         * Health Crystal
         */
        HealthCrystals.register(modEventBus);

        /*
         * TConstruct Material
         *
         * ModMaterials側で実際のMaterial登録を行う。
         */
        ModMaterials.register(modEventBus);

        /*
         * TConX modifier
         */
        TconxModifiers.MODIFIERS.register(modEventBus);

        /*
         * ModTiersのstatic初期化を確実に実行
         */
        ModTiers.SUPER.getUses();
    }
}
