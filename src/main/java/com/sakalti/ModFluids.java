package com.sakalti;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.world.level.material.Fluid;

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
    // Fluid registrations
    // =========================================================

    public static final RegistryObject<FluidType> HACHILITE_TYPE =
            registerType("hachilite");

    public static final RegistryObject<ForgeFlowingFluid> HACHILITE =
            registerSource("hachilite", HACHILITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> HACHILITE_FLOWING =
            registerFlowing("hachilite", HACHILITE_TYPE, HACHILITE);

    public static final RegistryObject<FluidType> KANILITE_TYPE =
            registerType("kanilite");

    public static final RegistryObject<ForgeFlowingFluid> KANILITE =
            registerSource("kanilite", KANILITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> KANILITE_FLOWING =
            registerFlowing("kanilite", KANILITE_TYPE, KANILITE);

    public static final RegistryObject<FluidType> IGNIZ_TYPE =
            registerType("igniz");

    public static final RegistryObject<ForgeFlowingFluid> IGNIZ =
            registerSource("igniz", IGNIZ_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> IGNIZ_FLOWING =
            registerFlowing("igniz", IGNIZ_TYPE, IGNIZ);

    public static final RegistryObject<FluidType> CHIRITE_TYPE =
            registerType("chirite");

    public static final RegistryObject<ForgeFlowingFluid> CHIRITE =
            registerSource("chirite", CHIRITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> CHIRITE_FLOWING =
            registerFlowing("chirite", CHIRITE_TYPE, CHIRITE);

    public static final RegistryObject<FluidType> MOMONGAITE_TYPE =
            registerType("momongaite");

    public static final RegistryObject<ForgeFlowingFluid> MOMONGAITE =
            registerSource("momongaite", MOMONGAITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> MOMONGAITE_FLOWING =
            registerFlowing("momongaite", MOMONGAITE_TYPE, MOMONGAITE);

    public static final RegistryObject<FluidType> HERDYEEN_TYPE =
            registerType("herdyeen");

    public static final RegistryObject<ForgeFlowingFluid> HERDYEEN =
            registerSource("herdyeen", HERDYEEN_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> HERDYEEN_FLOWING =
            registerFlowing("herdyeen", HERDYEEN_TYPE, HERDYEEN);

    public static final RegistryObject<FluidType> HIROSWARI_TYPE =
            registerType("hiroswari");

    public static final RegistryObject<ForgeFlowingFluid> HIROSWARI =
            registerSource("hiroswari", HIROSWARI_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> HIROSWARI_FLOWING =
            registerFlowing("hiroswari", HIROSWARI_TYPE, HIROSWARI);

    public static final RegistryObject<FluidType> MARULITE_TYPE =
            registerType("marulite");

    public static final RegistryObject<ForgeFlowingFluid> MARULITE =
            registerSource("marulite", MARULITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> MARULITE_FLOWING =
            registerFlowing("marulite", MARULITE_TYPE, MARULITE);

    public static final RegistryObject<FluidType> PROXIA_TYPE =
            registerType("proxia");

    public static final RegistryObject<ForgeFlowingFluid> PROXIA =
            registerSource("proxia", PROXIA_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> PROXIA_FLOWING =
            registerFlowing("proxia", PROXIA_TYPE, PROXIA);

    public static final RegistryObject<FluidType> OUSWARI_TYPE =
            registerType("ouswari");

    public static final RegistryObject<ForgeFlowingFluid> OUSWARI =
            registerSource("ouswari", OUSWARI_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> OUSWARI_FLOWING =
            registerFlowing("ouswari", OUSWARI_TYPE, OUSWARI);

    public static final RegistryObject<FluidType> AUROSTONE_TYPE =
            registerType("aurostone");

    public static final RegistryObject<ForgeFlowingFluid> AUROSTONE =
            registerSource("aurostone", AUROSTONE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> AUROSTONE_FLOWING =
            registerFlowing("aurostone", AUROSTONE_TYPE, AUROSTONE);

    public static final RegistryObject<FluidType> DEEPSTEEL_TYPE =
            registerType("deepsteel");

    public static final RegistryObject<ForgeFlowingFluid> DEEPSTEEL =
            registerSource("deepsteel", DEEPSTEEL_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> DEEPSTEEL_FLOWING =
            registerFlowing("deepsteel", DEEPSTEEL_TYPE, DEEPSTEEL);

    public static final RegistryObject<FluidType> CHIISTEEL_TYPE =
            registerType("chiisteel");

    public static final RegistryObject<ForgeFlowingFluid> CHIISTEEL =
            registerSource("chiisteel", CHIISTEEL_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> CHIISTEEL_FLOWING =
            registerFlowing("chiisteel", CHIISTEEL_TYPE, CHIISTEEL);

    public static final RegistryObject<FluidType> IOXIUM_TYPE =
            registerType("ioxium");

    public static final RegistryObject<ForgeFlowingFluid> IOXIUM =
            registerSource("ioxium", IOXIUM_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> IOXIUM_FLOWING =
            registerFlowing("ioxium", IOXIUM_TYPE, IOXIUM);

    public static final RegistryObject<FluidType> DILONITE_TYPE =
            registerType("dilonite");

    public static final RegistryObject<ForgeFlowingFluid> DILONITE =
            registerSource("dilonite", DILONITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> DILONITE_FLOWING =
            registerFlowing("dilonite", DILONITE_TYPE, DILONITE);

    public static final RegistryObject<FluidType> TIBERITE_TYPE =
            registerType("tiberite");

    public static final RegistryObject<ForgeFlowingFluid> TIBERITE =
            registerSource("tiberite", TIBERITE_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> TIBERITE_FLOWING =
            registerFlowing("tiberite", TIBERITE_TYPE, TIBERITE);

    public static final RegistryObject<FluidType> OSTLUM_TYPE =
            registerType("ostlum");

    public static final RegistryObject<ForgeFlowingFluid> OSTLUM =
            registerSource("ostlum", OSTLUM_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> OSTLUM_FLOWING =
            registerFlowing("ostlum", OSTLUM_TYPE, OSTLUM);

    public static final RegistryObject<FluidType> EMERALD_TYPE =
            registerType("emerald");

    public static final RegistryObject<ForgeFlowingFluid> EMERALD =
            registerSource("emerald", EMERALD_TYPE);

    public static final RegistryObject<ForgeFlowingFluid> EMERALD_FLOWING =
            registerFlowing("emerald", EMERALD_TYPE, EMERALD);

    // =========================================================
    // FluidType
    // =========================================================

    private static RegistryObject<FluidType> registerType(String name) {
        return FLUID_TYPES.register(
                name,
                () -> new FluidType(
                        FluidType.Properties.create()
                                .density(2000)
                                .viscosity(1000)
                                .temperature(1300)
                                .canSwim(false)
                                .canDrown(false)
                )
        );
    }

    // =========================================================
    // Source
    // =========================================================

    private static RegistryObject<ForgeFlowingFluid> registerSource(
            String name,
            RegistryObject<FluidType> type
    ) {
        return FLUIDS.register(
                name,
                () -> new ForgeFlowingFluid.Source(
                        createProperties(
                                type,
                                name
                        )
                )
        );
    }

    // =========================================================
    // Flowing
    // =========================================================

    private static RegistryObject<ForgeFlowingFluid> registerFlowing(
            String name,
            RegistryObject<FluidType> type,
            RegistryObject<ForgeFlowingFluid> source
    ) {
        return FLUIDS.register(
                name + "_flowing",
                () -> new ForgeFlowingFluid.Flowing(
                        createProperties(
                                type,
                                name
                        )
                )
        );
    }

    // =========================================================
    // Properties
    // =========================================================

    private static ForgeFlowingFluid.Properties createProperties(
            RegistryObject<FluidType> type,
            String name
    ) {
        /*
         * Source / Flowing の相互参照をここで直接作らない。
         *
         * そのため illegal forward reference を回避できる。
         */
        return new ForgeFlowingFluid.Properties(
                type,
                () -> getSource(name),
                () -> getFlowing(name)
        )
                .slopeFindDistance(4)
                .levelDecreasePerBlock(1);
    }

    private static ForgeFlowingFluid getSource(String name) {
        return switch (name) {
            case "hachilite" -> HACHILITE.get();
            case "kanilite" -> KANILITE.get();
            case "igniz" -> IGNIZ.get();
            case "chirite" -> CHIRITE.get();
            case "momongaite" -> MOMONGAITE.get();
            case "herdyeen" -> HERDYEEN.get();
            case "hiroswari" -> HIROSWARI.get();
            case "marulite" -> MARULITE.get();
            case "proxia" -> PROXIA.get();
            case "ouswari" -> OUSWARI.get();
            case "aurostone" -> AUROSTONE.get();
            case "deepsteel" -> DEEPSTEEL.get();
            case "chiisteel" -> CHIISTEEL.get();
            case "ioxium" -> IOXIUM.get();
            case "dilonite" -> DILONITE.get();
            case "tiberite" -> TIBERITE.get();
            case "ostlum" -> OSTLUM.get();
            case "emerald" -> EMERALD.get();
            default -> throw new IllegalArgumentException(
                    "Unknown fluid: " + name
            );
        };
    }

    private static ForgeFlowingFluid getFlowing(String name) {
        return switch (name) {
            case "hachilite" -> HACHILITE_FLOWING.get();
            case "kanilite" -> KANILITE_FLOWING.get();
            case "igniz" -> IGNIZ_FLOWING.get();
            case "chirite" -> CHIRITE_FLOWING.get();
            case "momongaite" -> MOMONGAITE_FLOWING.get();
            case "herdyeen" -> HERDYEEN_FLOWING.get();
            case "hiroswari" -> HIROSWARI_FLOWING.get();
            case "marulite" -> MARULITE_FLOWING.get();
            case "proxia" -> PROXIA_FLOWING.get();
            case "ouswari" -> OUSWARI_FLOWING.get();
            case "aurostone" -> AUROSTONE_FLOWING.get();
            case "deepsteel" -> DEEPSTEEL_FLOWING.get();
            case "chiisteel" -> CHIISTEEL_FLOWING.get();
            case "ioxium" -> IOXIUM_FLOWING.get();
            case "dilonite" -> DILONITE_FLOWING.get();
            case "tiberite" -> TIBERITE_FLOWING.get();
            case "ostlum" -> OSTLUM_FLOWING.get();
            case "emerald" -> EMERALD_FLOWING.get();
            default -> throw new IllegalArgumentException(
                    "Unknown fluid: " + name
            );
        };
    }

    // =========================================================
    // Register
    // =========================================================

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}
