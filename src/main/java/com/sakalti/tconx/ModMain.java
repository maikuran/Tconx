package com.sakalti.tconx;

import com.sakalti.tconx.enchant.ModEnchantments;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModMain.MODID)
public class ModMain {

    public static final String MODID = "sakalti";

    public ModMain() {
        IEventBus modEventBus =
                FMLJavaModLoadingContext.get().getModEventBus();

        // エンチャント登録
        ModEnchantments.ENCHANTMENTS.register(modEventBus);

        // ブロック登録
        ModMetals.BLOCKS.register(modEventBus);

        // アイテム登録
        ModMetals.ITEMS.register(modEventBus);
    }
}
