package com.sakalti.sakalti;

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
    // Fluids
    // =========================================================

    public static final FluidEntry HACHILITE =
            registerFluid("hachilite");

    public static final FluidEntry KANILITE =
            registerFluid("kanilite");

    public static final FluidEntry IGNIZ =
            registerFluid("igniz");

    public static final FluidEntry CHIRITE =
            registerFluid("chirite");

    public static final FluidEntry MOMONGAITE =
            registerFluid("momongaite");

    public static final FluidEntry HERDYEEN =
            registerFluid("herdyeen");

    public static final FluidEntry HIROSWARI =
            registerFluid("hiroswari");

    public static final FluidEntry MARULITE =
            registerFluid("marulite");

    public static final FluidEntry PROXIA =
            registerFluid("proxia");

    public static final FluidEntry OUSWARI =
            registerFluid("ouswari");

    public static final FluidEntry AUROSTONE =
            registerFluid("aurostone");

    public static final FluidEntry DEEPSTEEL =
            registerFluid("deepsteel");

    public static final FluidEntry CHIISTEEL =
            registerFluid("chiisteel");

    public static final FluidEntry IOXIUM =
            registerFluid("ioxium");

    public static final FluidEntry DILONITE =
            registerFluid("dilonite");

    public static final FluidEntry TIBERITE =
            registerFluid("tiberite");

    public static final FluidEntry OSTLUM =
            registerFluid("ostlum");

    public static final FluidEntry EMERALD =
            registerFluid("emerald");

    // =========================================================
    // Fluid registration
    // =========================================================

    private static FluidEntry registerFluid(String name) {

        RegistryObject<FluidType> type =
                FLUID_TYPES.register(
                        name,
                        () -> createFluidType()
                );

        RegistryObject<ForgeFlowingFluid> source =
                FLUIDS.register(
                        name,
                        () -> new ForgeFlowingFluid.Source(
                                createProperties(type, source, flowing)
                        )
                );

        RegistryObject<ForgeFlowingFluid> flowing =
                FLUIDS.register(
                        name + "_flowing",
                        () -> new ForgeFlowingFluid.Flowing(
                                createProperties(type, source, flowing)
                        )
                );

        return new FluidEntry(type, source, flowing);
    }

    // =========================================================
    // FluidType
    // =========================================================

    private static FluidType createFluidType() {
        return new FluidType(
                FluidType.Properties.create()
                        .density(2000)
                        .viscosity(1000)
                        .temperature(1300)
                        .canSwim(false)
                        .canDrown(false)
        );
    }

    // =========================================================
    // ForgeFlowingFluid Properties
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
    // Fluid Entry
    // =========================================================

    public record FluidEntry(
            RegistryObject<FluidType> type,
            RegistryObject<ForgeFlowingFluid> source,
            RegistryObject<ForgeFlowingFluid> flowing
    ) {
    }

    // =========================================================
    // Register
    // =========================================================

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}
