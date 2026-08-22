package com.sakalti;

import com.sakalti.modifier.TconxModifiers;
import com.sakalti.entity.*;
import com.sakalti.scaling.HealthCrystals;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent; // インポート追加
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModMain.MODID)
public final class ModMain {

    public static final String MODID = "sakalti";

    public ModMain() {
        IEventBus modEventBus =
                FMLJavaModLoadingContext.get().getModEventBus();

        // アイテム・ブロック
        ModMetals.register(modEventBus);

        // 溶融液体
        ModFluids.register(modEventBus);
        CrimsonFlyEntity.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        HealthCrystals.register(modEventBus);
        
        // TConX modifier
        TconxModifiers.MODIFIERS.register(modEventBus);

        // ModTiers の static 初期化を確実に実行
        ModTiers.SUPER.getUses();

        // setup イベントリスナーを登録
        modEventBus.addListener(this::setup);
    } // <-- コンストラクタはここで閉じます！

    // setup メソッドはコンストラクタの外側に配置します
    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModOreGeneration.registerConfiguredFeatures();
        });
    }
}
