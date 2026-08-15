package com.sakalti.sakalti;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModFluids {

    public static final String MODID = "sakalti";

    private ModFluids() {
    }

    // =========================================================
    // Registers
    // =========================================================

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(
                    ForgeRegistries.Keys.FLUID_TYPES,
                    MODID
            );

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(
                    ForgeRegistries.FLUIDS,
                    MODID
            );

    // =========================================================
    // Fluid properties
    // =========================================================

    private static final FluidType.Properties MOLTEN_PROPERTIES =
            FluidType.Properties.create()
                    .density(2000)
                    .viscosity(1500)
                    .temperature(1000);

    // =========================================================
    // Hachilite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_HACHILITE_TYPE =
            registerType("molten_hachilite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_HACHILITE =
            registerSource("molten_hachilite", MOLTEN_HACHILITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_HACHILITE =
            registerFlowing("molten_hachilite", MOLTEN_HACHILITE_TYPE);

    // =========================================================
    // Kanilite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_KANILITE_TYPE =
            registerType("molten_kanilite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_KANILITE =
            registerSource("molten_kanilite", MOLTEN_KANILITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_KANILITE =
            registerFlowing("molten_kanilite", MOLTEN_KANILITE_TYPE);

    // =========================================================
    // Igniz
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_IGNIZ_TYPE =
            registerType("molten_igniz");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_IGNIZ =
            registerSource("molten_igniz", MOLTEN_IGNIZ_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_IGNIZ =
            registerFlowing("molten_igniz", MOLTEN_IGNIZ_TYPE);

    // =========================================================
    // Chirite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_CHIRITE_TYPE =
            registerType("molten_chirite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_CHIRITE =
            registerSource("molten_chirite", MOLTEN_CHIRITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_CHIRITE =
            registerFlowing("molten_chirite", MOLTEN_CHIRITE_TYPE);

    // =========================================================
    // Momongaite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_MOMONGAITE_TYPE =
            registerType("molten_momongaite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_MOMONGAITE =
            registerSource("molten_momongaite", MOLTEN_MOMONGAITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_MOMONGAITE =
            registerFlowing("molten_momongaite", MOLTEN_MOMONGAITE_TYPE);

    // =========================================================
    // Herdyeen
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_HERDYEEN_TYPE =
            registerType("molten_herdyeen");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_HERDYEEN =
            registerSource("molten_herdyeen", MOLTEN_HERDYEEN_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_HERDYEEN =
            registerFlowing("molten_herdyeen", MOLTEN_HERDYEEN_TYPE);

    // =========================================================
    // Hiroswari
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_HIROSWARI_TYPE =
            registerType("molten_hiroswari");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_HIROSWARI =
            registerSource("molten_hiroswari", MOLTEN_HIROSWARI_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_HIROSWARI =
            registerFlowing("molten_hiroswari", MOLTEN_HIROSWARI_TYPE);

    // =========================================================
    // Marulite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_MARULITE_TYPE =
            registerType("molten_marulite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_MARULITE =
            registerSource("molten_marulite", MOLTEN_MARULITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_MARULITE =
            registerFlowing("molten_marulite", MOLTEN_MARULITE_TYPE);

    // =========================================================
    // Proxia
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_PROXIA_TYPE =
            registerType("molten_proxia");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_PROXIA =
            registerSource("molten_proxia", MOLTEN_PROXIA_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_PROXIA =
            registerFlowing("molten_proxia", MOLTEN_PROXIA_TYPE);

    // =========================================================
    // Ouswari
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_OUSWARI_TYPE =
            registerType("molten_ouswari");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_OUSWARI =
            registerSource("molten_ouswari", MOLTEN_OUSWARI_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_OUSWARI =
            registerFlowing("molten_ouswari", MOLTEN_OUSWARI_TYPE);

    // =========================================================
    // Aurostone
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_AUROSTONE_TYPE =
            registerType("molten_aurostone");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_AUROSTONE =
            registerSource("molten_aurostone", MOLTEN_AUROSTONE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_AUROSTONE =
            registerFlowing("molten_aurostone", MOLTEN_AUROSTONE_TYPE);

    // =========================================================
    // Deepsteel
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_DEEPSTEEL_TYPE =
            registerType("molten_deepsteel");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_DEEPSTEEL =
            registerSource("molten_deepsteel", MOLTEN_DEEPSTEEL_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_DEEPSTEEL =
            registerFlowing("molten_deepsteel", MOLTEN_DEEPSTEEL_TYPE);

    // =========================================================
    // Chiisteel
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_CHIISTEEL_TYPE =
            registerType("molten_chiisteel");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_CHIISTEEL =
            registerSource("molten_chiisteel", MOLTEN_CHIISTEEL_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_CHIISTEEL =
            registerFlowing("molten_chiisteel", MOLTEN_CHIISTEEL_TYPE);

    // =========================================================
    // Ioxium
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_IOXIUM_TYPE =
            registerType("molten_ioxium");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_IOXIUM =
            registerSource("molten_ioxium", MOLTEN_IOXIUM_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_IOXIUM =
            registerFlowing("molten_ioxium", MOLTEN_IOXIUM_TYPE);

    // =========================================================
    // Dilonite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_DILONITE_TYPE =
            registerType("molten_dilonite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_DILONITE =
            registerSource("molten_dilonite", MOLTEN_DILONITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_DILONITE =
            registerFlowing("molten_dilonite", MOLTEN_DILONITE_TYPE);

    // =========================================================
    // Tiberite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_TIBERITE_TYPE =
            registerType("molten_tiberite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_TIBERITE =
            registerSource("molten_tiberite", MOLTEN_TIBERITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_TIBERITE =
            registerFlowing("molten_tiberite", MOLTEN_TIBERITE_TYPE);

    // =========================================================
    // Ostlum
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_OSTLUM_TYPE =
            registerType("molten_ostlum");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_OSTLUM =
            registerSource("molten_ostlum", MOLTEN_OSTLUM_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> FLOWING_MOLTEN_OSTLUM =
            registerFlowing("molten_ostlum", MOLTEN_OSTLUM_TYPE);

    // =========================================================
    // Helpers
    // =========================================================

    private static RegistryObject<FluidType> registerType(String name) {
        return FLUID_TYPES.register(
                name,
                () -> FluidType.Builder.create(MOLTEN_PROPERTIES)
                        .build()
        );
    }

    private static RegistryObject<ForgeFlowingFluid> registerSource(
            String name,
            RegistryObject<FluidType> type
    ) {
        return FLUIDS.register(
                name,
                () -> new ForgeFlowingFluid.Source(
                        new ForgeFlowingFluid.Properties(
                                type,
                                () -> MOLTEN_HACHILITE.get(),
                                () -> FLOWING_MOLTEN_HACHILITE.get()
                        )
                )
        );
    }

    private static RegistryObject<ForgeFlowingFluid> registerFlowing(
            String name,
            RegistryObject<FluidType> type
    ) {
        return FLUIDS.register(
                "flowing_" + name,
                () -> new ForgeFlowingFluid.Flowing(
                        new ForgeFlowingFluid.Properties(
                                type,
                                () -> MOLTEN_HACHILITE.get(),
                                () -> FLOWING_MOLTEN_HACHILITE.get()
                        )
                )
        );
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}
