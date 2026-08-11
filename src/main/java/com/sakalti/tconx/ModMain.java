package com.sakalti.tconx;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.sakalti.tconx.material.hachilite.ModMaterials;
import com.sakalti.tconx.enchant.ModEnchantments;
import com.sakalti.tconx.registry.ModMirzo;
import com.sakalti.tconx.registry.ModMetals;

@Mod("sakalti")
public class ModMain {

    public ModMain() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEnchantments.ENCHANTMENTS.register(bus); 
        ModMetals.BLOCKS.register(bus);      // ← 追加
        ModMetals.ITEMS.register(bus);       // ← 追加
        ModMaterials.registerMaterials();
    }
}
