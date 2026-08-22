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

        ModMetals.register(modEventBus);
        ModFluids.register(modEventBus);

        CrimsonFlyEntity.register(modEventBus);
        HealthCrystals.register(modEventBus);

        TconxModifiers.MODIFIERS.register(modEventBus);

        ModTiers.SUPER.getUses();
    }
}
