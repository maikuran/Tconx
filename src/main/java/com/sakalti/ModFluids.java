package com.sakalti;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

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
    // Hachilite
    // =========================================================

    public static final RegistryObject<FluidType> HACHILITE_TYPE =
            registerType("hachilite");

    public static final RegistryObject<ForgeFlowingFluid> HACHILITE =
            FLUIDS.register(
                    "hachilite",
                    () -> new ForgeFlowingFluid.Source(
                            HACHILITE_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> HACHILITE_FLOWING =
            FLUIDS.register(
                    "hachilite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            HACHILITE_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> HACHILITE_PROPERTIES =
            () -> createProperties(
                    HACHILITE_TYPE,
                    HACHILITE,
                    HACHILITE_FLOWING
            );

    // =========================================================
    // Kanilite
    // =========================================================

    public static final RegistryObject<FluidType> KANILITE_TYPE =
            registerType("kanilite");

    public static final RegistryObject<ForgeFlowingFluid> KANILITE =
            FLUIDS.register(
                    "kanilite",
                    () -> new ForgeFlowingFluid.Source(
                            KANILITE_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> KANILITE_FLOWING =
            FLUIDS.register(
                    "kanilite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            KANILITE_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> KANILITE_PROPERTIES =
            () -> createProperties(
                    KANILITE_TYPE,
                    KANILITE,
                    KANILITE_FLOWING
            );

    // =========================================================
    // Igniz
    // =========================================================

    public static final RegistryObject<FluidType> IGNIZ_TYPE =
            registerType("igniz");

    public static final RegistryObject<ForgeFlowingFluid> IGNIZ =
            FLUIDS.register(
                    "igniz",
                    () -> new ForgeFlowingFluid.Source(
                            IGNIZ_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> IGNIZ_FLOWING =
            FLUIDS.register(
                    "igniz_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            IGNIZ_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> IGNIZ_PROPERTIES =
            () -> createProperties(
                    IGNIZ_TYPE,
                    IGNIZ,
                    IGNIZ_FLOWING
            );

    // =========================================================
    // Chirite
    // =========================================================

    public static final RegistryObject<FluidType> CHIRITE_TYPE =
            registerType("chirite");

    public static final RegistryObject<ForgeFlowingFluid> CHIRITE =
            FLUIDS.register(
                    "chirite",
                    () -> new ForgeFlowingFluid.Source(
                            CHIRITE_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> CHIRITE_FLOWING =
            FLUIDS.register(
                    "chirite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            CHIRITE_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> CHIRITE_PROPERTIES =
            () -> createProperties(
                    CHIRITE_TYPE,
                    CHIRITE,
                    CHIRITE_FLOWING
            );

    // =========================================================
    // Momongaite
    // =========================================================

    public static final RegistryObject<FluidType> MOMONGAITE_TYPE =
            registerType("momongaite");

    public static final RegistryObject<ForgeFlowingFluid> MOMONGAITE =
            FLUIDS.register(
                    "momongaite",
                    () -> new ForgeFlowingFluid.Source(
                            MOMONGAITE_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOMONGAITE_FLOWING =
            FLUIDS.register(
                    "momongaite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            MOMONGAITE_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> MOMONGAITE_PROPERTIES =
            () -> createProperties(
                    MOMONGAITE_TYPE,
                    MOMONGAITE,
                    MOMONGAITE_FLOWING
            );

    // =========================================================
    // Herdyeen
    // =========================================================

    public static final RegistryObject<FluidType> HERDYEEN_TYPE =
            registerType("herdyeen");

    public static final RegistryObject<ForgeFlowingFluid> HERDYEEN =
            FLUIDS.register(
                    "herdyeen",
                    () -> new ForgeFlowingFluid.Source(
                            HERDYEEN_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> HERDYEEN_FLOWING =
            FLUIDS.register(
                    "herdyeen_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            HERDYEEN_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> HERDYEEN_PROPERTIES =
            () -> createProperties(
                    HERDYEEN_TYPE,
                    HERDYEEN,
                    HERDYEEN_FLOWING
            );

    // =========================================================
    // Hiroswari
    // =========================================================

    public static final RegistryObject<FluidType> HIROSWARI_TYPE =
            registerType("hiroswari");

    public static final RegistryObject<ForgeFlowingFluid> HIROSWARI =
            FLUIDS.register(
                    "hiroswari",
                    () -> new ForgeFlowingFluid.Source(
                            HIROSWARI_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> HIROSWARI_FLOWING =
            FLUIDS.register(
                    "hiroswari_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            HIROSWARI_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> HIROSWARI_PROPERTIES =
            () -> createProperties(
                    HIROSWARI_TYPE,
                    HIROSWARI,
                    HIROSWARI_FLOWING
            );

    // =========================================================
    // Marulite
    // =========================================================

    public static final RegistryObject<FluidType> MARULITE_TYPE =
            registerType("marulite");

    public static final RegistryObject<ForgeFlowingFluid> MARULITE =
            FLUIDS.register(
                    "marulite",
                    () -> new ForgeFlowingFluid.Source(
                            MARULITE_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MARULITE_FLOWING =
            FLUIDS.register(
                    "marulite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            MARULITE_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> MARULITE_PROPERTIES =
            () -> createProperties(
                    MARULITE_TYPE,
                    MARULITE,
                    MARULITE_FLOWING
            );

    // =========================================================
    // Proxia
    // =========================================================

    public static final RegistryObject<FluidType> PROXIA_TYPE =
            registerType("proxia");

    public static final RegistryObject<ForgeFlowingFluid> PROXIA =
            FLUIDS.register(
                    "proxia",
                    () -> new ForgeFlowingFluid.Source(
                            PROXIA_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> PROXIA_FLOWING =
            FLUIDS.register(
                    "proxia_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            PROXIA_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> PROXIA_PROPERTIES =
            () -> createProperties(
                    PROXIA_TYPE,
                    PROXIA,
                    PROXIA_FLOWING
            );

    // =========================================================
    // Ouswari
    // =========================================================

    public static final RegistryObject<FluidType> OUSWARI_TYPE =
            registerType("ouswari");

    public static final RegistryObject<ForgeFlowingFluid> OUSWARI =
            FLUIDS.register(
                    "ouswari",
                    () -> new ForgeFlowingFluid.Source(
                            OUSWARI_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> OUSWARI_FLOWING =
            FLUIDS.register(
                    "ouswari_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            OUSWARI_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> OUSWARI_PROPERTIES =
            () -> createProperties(
                    OUSWARI_TYPE,
                    OUSWARI,
                    OUSWARI_FLOWING
            );

    // =========================================================
    // Aurostone
    // =========================================================

    public static final RegistryObject<FluidType> AUROSTONE_TYPE =
            registerType("aurostone");

    public static final RegistryObject<ForgeFlowingFluid> AUROSTONE =
            FLUIDS.register(
                    "aurostone",
                    () -> new ForgeFlowingFluid.Source(
                            AUROSTONE_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> AUROSTONE_FLOWING =
            FLUIDS.register(
                    "aurostone_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            AUROSTONE_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> AUROSTONE_PROPERTIES =
            () -> createProperties(
                    AUROSTONE_TYPE,
                    AUROSTONE,
                    AUROSTONE_FLOWING
            );

    // =========================================================
    // Deepsteel
    // =========================================================

    public static final RegistryObject<FluidType> DEEPSTEEL_TYPE =
            registerType("deepsteel");

    public static final RegistryObject<ForgeFlowingFluid> DEEPSTEEL =
            FLUIDS.register(
                    "deepsteel",
                    () -> new ForgeFlowingFluid.Source(
                            DEEPSTEEL_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> DEEPSTEEL_FLOWING =
            FLUIDS.register(
                    "deepsteel_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            DEEPSTEEL_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> DEEPSTEEL_PROPERTIES =
            () -> createProperties(
                    DEEPSTEEL_TYPE,
                    DEEPSTEEL,
                    DEEPSTEEL_FLOWING
            );

    // =========================================================
    // Chiisteel
    // =========================================================

    public static final RegistryObject<FluidType> CHIISTEEL_TYPE =
            registerType("chiisteel");

    public static final RegistryObject<ForgeFlowingFluid> CHIISTEEL =
            FLUIDS.register(
                    "chiisteel",
                    () -> new ForgeFlowingFluid.Source(
                            CHIISTEEL_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> CHIISTEEL_FLOWING =
            FLUIDS.register(
                    "chiisteel_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            CHIISTEEL_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> CHIISTEEL_PROPERTIES =
            () -> createProperties(
                    CHIISTEEL_TYPE,
                    CHIISTEEL,
                    CHIISTEEL_FLOWING
            );

    // =========================================================
    // Ioxium
    // =========================================================

    public static final RegistryObject<FluidType> IOXIUM_TYPE =
            registerType("ioxium");

    public static final RegistryObject<ForgeFlowingFluid> IOXIUM =
            FLUIDS.register(
                    "ioxium",
                    () -> new ForgeFlowingFluid.Source(
                            IOXIUM_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> IOXIUM_FLOWING =
            FLUIDS.register(
                    "ioxium_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            IOXIUM_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> IOXIUM_PROPERTIES =
            () -> createProperties(
                    IOXIUM_TYPE,
                    IOXIUM,
                    IOXIUM_FLOWING
            );

    // =========================================================
    // Dilonite
    // =========================================================

    public static final RegistryObject<FluidType> DILONITE_TYPE =
            registerType("dilonite");

    public static final RegistryObject<ForgeFlowingFluid> DILONITE =
            FLUIDS.register(
                    "dilonite",
                    () -> new ForgeFlowingFluid.Source(
                            DILONITE_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> DILONITE_FLOWING =
            FLUIDS.register(
                    "dilonite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            DILONITE_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> DILONITE_PROPERTIES =
            () -> createProperties(
                    DILONITE_TYPE,
                    DILONITE,
                    DILONITE_FLOWING
            );

    // =========================================================
    // Tiberite
    // =========================================================

    public static final RegistryObject<FluidType> TIBERITE_TYPE =
            registerType("tiberite");

    public static final RegistryObject<ForgeFlowingFluid> TIBERITE =
            FLUIDS.register(
                    "tiberite",
                    () -> new ForgeFlowingFluid.Source(
                            TIBERITE_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> TIBERITE_FLOWING =
            FLUIDS.register(
                    "tiberite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            TIBERITE_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> TIBERITE_PROPERTIES =
            () -> createProperties(
                    TIBERITE_TYPE,
                    TIBERITE,
                    TIBERITE_FLOWING
            );

    // =========================================================
    // Ostlum
    // =========================================================

    public static final RegistryObject<FluidType> OSTLUM_TYPE =
            registerType("ostlum");

    public static final RegistryObject<ForgeFlowingFluid> OSTLUM =
            FLUIDS.register(
                    "ostlum",
                    () -> new ForgeFlowingFluid.Source(
                            OSTLUM_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> OSTLUM_FLOWING =
            FLUIDS.register(
                    "ostlum_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            OSTLUM_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> OSTLUM_PROPERTIES =
            () -> createProperties(
                    OSTLUM_TYPE,
                    OSTLUM,
                    OSTLUM_FLOWING
            );

    // =========================================================
    // Emerald
    // =========================================================

    public static final RegistryObject<FluidType> EMERALD_TYPE =
            registerType("emerald");

    public static final RegistryObject<ForgeFlowingFluid> EMERALD =
            FLUIDS.register(
                    "emerald",
                    () -> new ForgeFlowingFluid.Source(
                            EMERALD_PROPERTIES.get()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> EMERALD_FLOWING =
            FLUIDS.register(
                    "emerald_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            EMERALD_PROPERTIES.get()
                    )
            );

    private static final Supplier<ForgeFlowingFluid.Properties> EMERALD_PROPERTIES =
            () -> createProperties(
                    EMERALD_TYPE,
                    EMERALD,
                    EMERALD_FLOWING
            );

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
    // Properties
    // =========================================================

    private static ForgeFlowingFluid.Properties createProperties(
            RegistryObject<FluidType> type,
            RegistryObject<ForgeFlowingFluid> source,
            RegistryObject<ForgeFlowingFluid> flowing
    ) {
        return new ForgeFlowingFluid.Properties(
                type,
                source,
                flowing
        )
                .slopeFindDistance(4)
                .levelDecreasePerBlock(1);
    }

    // =========================================================
    // Register
    // =========================================================

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}
