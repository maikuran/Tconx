package com.sakalti;

import com.sakalti.entity.CrimsonFlyEntity;
import com.sakalti.modifier.TconxModifiers;
import com.sakalti.scaling.HealthCrystals;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModMain.MODID)
public final class ModMain {

    public static final String MODID = "sakalti";

    public ModMain() {

        IEventBus modEventBus =
                FMLJavaModLoadingContext
                        .get()
                        .getModEventBus();

        // ============================================================
        // Blocks / Items
        // ============================================================

        ModMetals.register(modEventBus);

        // ============================================================
        // Fluids
        // ============================================================

        ModFluids.register(modEventBus);

        // ============================================================
        // Entity
        // ============================================================

        CrimsonFlyEntity.register(modEventBus);

        // ============================================================
        // Health Crystals
        // ============================================================

        HealthCrystals.register(modEventBus);

        // ============================================================
        // TConX modifiers
        // ============================================================

        TconxModifiers.MODIFIERS.register(modEventBus);

        // ============================================================
        // ModTiers
        // ============================================================

        ModTiers.SUPER.getUses();

        // ============================================================
        // World Generation
        // ============================================================

        modEventBus.addListener(
                ModOreGeneration::setup
        );
    }
}
