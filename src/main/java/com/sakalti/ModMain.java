package com.sakalti.sakalti;

import com.sakalti.sakalti.enchant.ModEnchantments;
import com.sakalti.sakalti.modifier.TconxModifiers;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModMain.MODID)
public final class ModMain {

    public static final String MODID = "sakalti";

    public ModMain() {
        IEventBus modEventBus =
                FMLJavaModLoadingContext.get().getModEventBus();

        ModEnchantments.ENCHANTMENTS.register(modEventBus);

        ModMetals.register(modEventBus);

        // ModTiersのstatic初期化を確実に実行
        ModTiers.SUPER.getUses();

        TconxModifiers.MODIFIERS.register(modEventBus);
    }
}
