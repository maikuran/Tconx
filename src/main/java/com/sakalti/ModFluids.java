package com.sakalti.sakalti;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

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
    // Hachilite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_HACHILITE_TYPE =
            registerType("molten_hachilite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_HACHILITE =
            FLUIDS.register(
                    "molten_hachilite",
                    () -> new ForgeFlowingFluid.Source(
                            hachiliteProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_HACHILITE_FLOWING =
            FLUIDS.register(
                    "molten_hachilite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            hachiliteProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties hachiliteProperties() {
        return properties(
                MOLTEN_HACHILITE_TYPE,
                MOLTEN_HACHILITE,
                MOLTEN_HACHILITE_FLOWING
        );
    }

    // =========================================================
    // Kanilite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_KANILITE_TYPE =
            registerType("molten_kanilite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_KANILITE =
            FLUIDS.register(
                    "molten_kanilite",
                    () -> new ForgeFlowingFluid.Source(
                            kaniliteProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_KANILITE_FLOWING =
            FLUIDS.register(
                    "molten_kanilite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            kaniliteProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties kaniliteProperties() {
        return properties(
                MOLTEN_KANILITE_TYPE,
                MOLTEN_KANILITE,
                MOLTEN_KANILITE_FLOWING
        );
    }

    // =========================================================
    // Igniz
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_IGNIZ_TYPE =
            registerType("molten_igniz");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_IGNIZ =
            FLUIDS.register(
                    "molten_igniz",
                    () -> new ForgeFlowingFluid.Source(
                            ignizProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_IGNIZ_FLOWING =
            FLUIDS.register(
                    "molten_igniz_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            ignizProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties ignizProperties() {
        return properties(
                MOLTEN_IGNIZ_TYPE,
                MOLTEN_IGNIZ,
                MOLTEN_IGNIZ_FLOWING
        );
    }

    // =========================================================
    // Chirite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_CHIRITE_TYPE =
            registerType("molten_chirite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_CHIRITE =
            FLUIDS.register(
                    "molten_chirite",
                    () -> new ForgeFlowingFluid.Source(
                            chiriteProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_CHIRITE_FLOWING =
            FLUIDS.register(
                    "molten_chirite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            chiriteProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties chiriteProperties() {
        return properties(
                MOLTEN_CHIRITE_TYPE,
                MOLTEN_CHIRITE,
                MOLTEN_CHIRITE_FLOWING
        );
    }

    // =========================================================
    // Momongaite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_MOMONGAITE_TYPE =
            registerType("molten_momongaite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_MOMONGAITE =
            FLUIDS.register(
                    "molten_momongaite",
                    () -> new ForgeFlowingFluid.Source(
                            momongaiteProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_MOMONGAITE_FLOWING =
            FLUIDS.register(
                    "molten_momongaite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            momongaiteProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties momongaiteProperties() {
        return properties(
                MOLTEN_MOMONGAITE_TYPE,
                MOLTEN_MOMONGAITE,
                MOLTEN_MOMONGAITE_FLOWING
        );
    }

    // =========================================================
    // Herdyeen
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_HERDYEEN_TYPE =
            registerType("molten_herdyeen");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_HERDYEEN =
            FLUIDS.register(
                    "molten_herdyeen",
                    () -> new ForgeFlowingFluid.Source(
                            herdyeenProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_HERDYEEN_FLOWING =
            FLUIDS.register(
                    "molten_herdyeen_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            herdyeenProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties herdyeenProperties() {
        return properties(
                MOLTEN_HERDYEEN_TYPE,
                MOLTEN_HERDYEEN,
                MOLTEN_HERDYEEN_FLOWING
        );
    }

    // =========================================================
    // Hiroswari
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_HIROSWARI_TYPE =
            registerType("molten_hiroswari");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_HIROSWARI =
            FLUIDS.register(
                    "molten_hiroswari",
                    () -> new ForgeFlowingFluid.Source(
                            hiroswariProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_HIROSWARI_FLOWING =
            FLUIDS.register(
                    "molten_hiroswari_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            hiroswariProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties hiroswariProperties() {
        return properties(
                MOLTEN_HIROSWARI_TYPE,
                MOLTEN_HIROSWARI,
                MOLTEN_HIROSWARI_FLOWING
        );
    }

    // =========================================================
    // Marulite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_MARULITE_TYPE =
            registerType("molten_marulite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_MARULITE =
            FLUIDS.register(
                    "molten_marulite",
                    () -> new ForgeFlowingFluid.Source(
                            maruliteProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_MARULITE_FLOWING =
            FLUIDS.register(
                    "molten_marulite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            maruliteProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties maruliteProperties() {
        return properties(
                MOLTEN_MARULITE_TYPE,
                MOLTEN_MARULITE,
                MOLTEN_MARULITE_FLOWING
        );
    }

    // =========================================================
    // Proxia
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_PROXIA_TYPE =
            registerType("molten_proxia");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_PROXIA =
            FLUIDS.register(
                    "molten_proxia",
                    () -> new ForgeFlowingFluid.Source(
                            proxiaProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_PROXIA_FLOWING =
            FLUIDS.register(
                    "molten_proxia_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            proxiaProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties proxiaProperties() {
        return properties(
                MOLTEN_PROXIA_TYPE,
                MOLTEN_PROXIA,
                MOLTEN_PROXIA_FLOWING
        );
    }

    // =========================================================
    // Ouswari
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_OUSWARI_TYPE =
            registerType("molten_ouswari");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_OUSWARI =
            FLUIDS.register(
                    "molten_ouswari",
                    () -> new ForgeFlowingFluid.Source(
                            ouswariProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_OUSWARI_FLOWING =
            FLUIDS.register(
                    "molten_ouswari_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            ouswariProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties ouswariProperties() {
        return properties(
                MOLTEN_OUSWARI_TYPE,
                MOLTEN_OUSWARI,
                MOLTEN_OUSWARI_FLOWING
        );
    }

    // =========================================================
    // Aurostone
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_AUROSTONE_TYPE =
            registerType("molten_aurostone");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_AUROSTONE =
            FLUIDS.register(
                    "molten_aurostone",
                    () -> new ForgeFlowingFluid.Source(
                            aurostoneProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_AUROSTONE_FLOWING =
            FLUIDS.register(
                    "molten_aurostone_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            aurostoneProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties aurostoneProperties() {
        return properties(
                MOLTEN_AUROSTONE_TYPE,
                MOLTEN_AUROSTONE,
                MOLTEN_AUROSTONE_FLOWING
        );
    }

    // =========================================================
    // Deepsteel
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_DEEPSTEEL_TYPE =
            registerType("molten_deepsteel");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_DEEPSTEEL =
            FLUIDS.register(
                    "molten_deepsteel",
                    () -> new ForgeFlowingFluid.Source(
                            deepsteelProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_DEEPSTEEL_FLOWING =
            FLUIDS.register(
                    "molten_deepsteel_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            deepsteelProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties deepsteelProperties() {
        return properties(
                MOLTEN_DEEPSTEEL_TYPE,
                MOLTEN_DEEPSTEEL,
                MOLTEN_DEEPSTEEL_FLOWING
        );
    }

    // =========================================================
    // Chiisteel
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_CHIISTEEL_TYPE =
            registerType("molten_chiisteel");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_CHIISTEEL =
            FLUIDS.register(
                    "molten_chiisteel",
                    () -> new ForgeFlowingFluid.Source(
                            chiisteelProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_CHIISTEEL_FLOWING =
            FLUIDS.register(
                    "molten_chiisteel_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            chiisteelProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties chiisteelProperties() {
        return properties(
                MOLTEN_CHIISTEEL_TYPE,
                MOLTEN_CHIISTEEL,
                MOLTEN_CHIISTEEL_FLOWING
        );
    }

    // =========================================================
    // Ioxium
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_IOXIUM_TYPE =
            registerType("molten_ioxium");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_IOXIUM =
            FLUIDS.register(
                    "molten_ioxium",
                    () -> new ForgeFlowingFluid.Source(
                            ioxiumProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_IOXIUM_FLOWING =
            FLUIDS.register(
                    "molten_ioxium_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            ioxiumProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties ioxiumProperties() {
        return properties(
                MOLTEN_IOXIUM_TYPE,
                MOLTEN_IOXIUM,
                MOLTEN_IOXIUM_FLOWING
        );
    }

    // =========================================================
    // Dilonite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_DILONITE_TYPE =
            registerType("molten_dilonite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_DILONITE =
            FLUIDS.register(
                    "molten_dilonite",
                    () -> new ForgeFlowingFluid.Source(
                            diloniteProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_DILONITE_FLOWING =
            FLUIDS.register(
                    "molten_dilonite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            diloniteProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties diloniteProperties() {
        return properties(
                MOLTEN_DILONITE_TYPE,
                MOLTEN_DILONITE,
                MOLTEN_DILONITE_FLOWING
        );
    }

    // =========================================================
    // Tiberite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_TIBERITE_TYPE =
            registerType("molten_tiberite");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_TIBERITE =
            FLUIDS.register(
                    "molten_tiberite",
                    () -> new ForgeFlowingFluid.Source(
                            tiberiteProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_TIBERITE_FLOWING =
            FLUIDS.register(
                    "molten_tiberite_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            tiberiteProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties tiberiteProperties() {
        return properties(
                MOLTEN_TIBERITE_TYPE,
                MOLTEN_TIBERITE,
                MOLTEN_TIBERITE_FLOWING
        );
    }

    // =========================================================
    // Ostlum
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_OSTLUM_TYPE =
            registerType("molten_ostlum");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_OSTLUM =
            FLUIDS.register(
                    "molten_ostlum",
                    () -> new ForgeFlowingFluid.Source(
                            ostlumProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_OSTLUM_FLOWING =
            FLUIDS.register(
                    "molten_ostlum_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            ostlumProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties ostlumProperties() {
        return properties(
                MOLTEN_OSTLUM_TYPE,
                MOLTEN_OSTLUM,
                MOLTEN_OSTLUM_FLOWING
        );
    }

    // =========================================================
    // Emerald
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_EMERALD_TYPE =
            registerType("molten_emerald");

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_EMERALD =
            FLUIDS.register(
                    "molten_emerald",
                    () -> new ForgeFlowingFluid.Source(
                            emeraldProperties()
                    )
            );

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_EMERALD_FLOWING =
            FLUIDS.register(
                    "molten_emerald_flowing",
                    () -> new ForgeFlowingFluid.Flowing(
                            emeraldProperties()
                    )
            );

    private static ForgeFlowingFluid.Properties emeraldProperties() {
        return properties(
                MOLTEN_EMERALD_TYPE,
                MOLTEN_EMERALD,
                MOLTEN_EMERALD_FLOWING
        );
    }

    // =========================================================
    // FluidType registration
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
    // ForgeFlowingFluid properties
    // =========================================================

    private static ForgeFlowingFluid.Properties properties(
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
