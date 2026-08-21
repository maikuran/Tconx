package com.sakalti.sakalti;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.sakalti.sakalti.material.hachilite.ModMaterials;
import com.sakalti.sakalti.material.hachilite.ModStats;

@Mod("sakalti")
public class ModMain {

    public ModMain() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModMaterials.registerMaterials(bus);
        ModStats.registerStats();
    }
}
