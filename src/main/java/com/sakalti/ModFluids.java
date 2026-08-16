package com.sakalti;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

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
    // Fluid Entry
    // =========================================================

    private static final class FluidEntry {

        final String name;

        RegistryObject<FluidType> type;
        RegistryObject<ForgeFlowingFluid> source;
        RegistryObject<ForgeFlowingFluid> flowing;

        FluidEntry(String name) {
            this.name = name;
        }
    }

    // =========================================================
    // 共通登録関数
    // =========================================================

    private static FluidEntry registerFluid(String name) {

        FluidEntry entry = new FluidEntry(name);

        // -----------------------------------------------------
        // FluidType
        // -----------------------------------------------------

        entry.type = FLUID_TYPES.register(
                name,
                () -> createFluidType(name)
        );

        // -----------------------------------------------------
        // Source
        // -----------------------------------------------------

        entry.source = FLUIDS.register(
                name,
                () -> new ForgeFlowingFluid.Source(
                        createProperties(entry)
                )
        );

        // -----------------------------------------------------
        // Flowing
        // -----------------------------------------------------

        entry.flowing = FLUIDS.register(
                name + "_flowing",
                () -> new ForgeFlowingFluid.Flowing(
                        createProperties(entry)
                )
        );

        return entry;
    }

    // =========================================================
    // FluidType
    // =========================================================

    private static FluidType createFluidType(String name) {

        ResourceLocation stillTexture =
                new ResourceLocation(
                        MODID,
                        "fluid/" + name + "_still"
                );

        ResourceLocation flowingTexture =
                new ResourceLocation(
                        MODID,
                        "fluid/" + name + "_flowing"
                );

        return new FluidType(
                FluidType.Properties.create()
                        .density(2000)
                        .viscosity(1000)
                        .temperature(1300)
                        .canSwim(false)
                        .canDrown(false)
        ) {

            @Override
            public void initializeClient(
                    Consumer<IClientFluidTypeExtensions> consumer
            ) {
                consumer.accept(new IClientFluidTypeExtensions() {

                    @Override
                    public ResourceLocation getStillTexture() {
                        return stillTexture;
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return flowingTexture;
                    }
                });
            }
        };
    }

    // =========================================================
    // ForgeFlowingFluid Properties
    // =========================================================

    private static ForgeFlowingFluid.Properties createProperties(
            FluidEntry entry
    ) {
        return new ForgeFlowingFluid.Properties(
                entry.type,
                entry.source,
                entry.flowing
        )
                .slopeFindDistance(4)
                .levelDecreasePerBlock(1);
    }

    // =========================================================
    // Hachilite
    // =========================================================

    private static final FluidEntry HACHILITE_ENTRY =
            registerFluid("hachilite");

    public static final RegistryObject<FluidType> HACHILITE_TYPE =
            HACHILITE_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> HACHILITE =
            HACHILITE_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> HACHILITE_FLOWING =
            HACHILITE_ENTRY.flowing;

    // =========================================================
    // Kanilite
    // =========================================================

    private static final FluidEntry KANILITE_ENTRY =
            registerFluid("kanilite");

    public static final RegistryObject<FluidType> KANILITE_TYPE =
            KANILITE_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> KANILITE =
            KANILITE_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> KANILITE_FLOWING =
            KANILITE_ENTRY.flowing;

    // =========================================================
    // Igniz
    // =========================================================

    private static final FluidEntry IGNIZ_ENTRY =
            registerFluid("igniz");

    public static final RegistryObject<FluidType> IGNIZ_TYPE =
            IGNIZ_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> IGNIZ =
            IGNIZ_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> IGNIZ_FLOWING =
            IGNIZ_ENTRY.flowing;

    // =========================================================
    // Chirite
    // =========================================================

    private static final FluidEntry CHIRITE_ENTRY =
            registerFluid("chirite");

    public static final RegistryObject<FluidType> CHIRITE_TYPE =
            CHIRITE_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> CHIRITE =
            CHIRITE_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> CHIRITE_FLOWING =
            CHIRITE_ENTRY.flowing;

    // =========================================================
    // Momongaite
    // =========================================================

    private static final FluidEntry MOMONGAITE_ENTRY =
            registerFluid("momongaite");

    public static final RegistryObject<FluidType> MOMONGAITE_TYPE =
            MOMONGAITE_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> MOMONGAITE =
            MOMONGAITE_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> MOMONGAITE_FLOWING =
            MOMONGAITE_ENTRY.flowing;

    // =========================================================
    // Herdyeen
    // =========================================================

    private static final FluidEntry HERDYEEN_ENTRY =
            registerFluid("herdyeen");

    public static final RegistryObject<FluidType> HERDYEEN_TYPE =
            HERDYEEN_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> HERDYEEN =
            HERDYEEN_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> HERDYEEN_FLOWING =
            HERDYEEN_ENTRY.flowing;

    // =========================================================
    // Hiroswari
    // =========================================================

    private static final FluidEntry HIROSWARI_ENTRY =
            registerFluid("hiroswari");

    public static final RegistryObject<FluidType> HIROSWARI_TYPE =
            HIROSWARI_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> HIROSWARI =
            HIROSWARI_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> HIROSWARI_FLOWING =
            HIROSWARI_ENTRY.flowing;

    // =========================================================
    // Marulite
    // =========================================================

    private static final FluidEntry MARULITE_ENTRY =
            registerFluid("marulite");

    public static final RegistryObject<FluidType> MARULITE_TYPE =
            MARULITE_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> MARULITE =
            MARULITE_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> MARULITE_FLOWING =
            MARULITE_ENTRY.flowing;

    // =========================================================
    // Proxia
    // =========================================================

    private static final FluidEntry PROXIA_ENTRY =
            registerFluid("proxia");

    public static final RegistryObject<FluidType> PROXIA_TYPE =
            PROXIA_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> PROXIA =
            PROXIA_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> PROXIA_FLOWING =
            PROXIA_ENTRY.flowing;

    // =========================================================
    // Ouswari
    // =========================================================

    private static final FluidEntry OUSWARI_ENTRY =
            registerFluid("ouswari");

    public static final RegistryObject<FluidType> OUSWARI_TYPE =
            OUSWARI_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> OUSWARI =
            OUSWARI_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> OUSWARI_FLOWING =
            OUSWARI_ENTRY.flowing;

    // =========================================================
    // Aurostone
    // =========================================================

    private static final FluidEntry AUROSTONE_ENTRY =
            registerFluid("aurostone");

    public static final RegistryObject<FluidType> AUROSTONE_TYPE =
            AUROSTONE_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> AUROSTONE =
            AUROSTONE_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> AUROSTONE_FLOWING =
            AUROSTONE_ENTRY.flowing;

    // =========================================================
    // Deepsteel
    // =========================================================

    private static final FluidEntry DEEPSTEEL_ENTRY =
            registerFluid("deepsteel");

    public static final RegistryObject<FluidType> DEEPSTEEL_TYPE =
            DEEPSTEEL_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> DEEPSTEEL =
            DEEPSTEEL_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> DEEPSTEEL_FLOWING =
            DEEPSTEEL_ENTRY.flowing;

    // =========================================================
    // Chiisteel
    // =========================================================

    private static final FluidEntry CHIISTEEL_ENTRY =
            registerFluid("chiisteel");

    public static final RegistryObject<FluidType> CHIISTEEL_TYPE =
            CHIISTEEL_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> CHIISTEEL =
            CHIISTEEL_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> CHIISTEEL_FLOWING =
            CHIISTEEL_ENTRY.flowing;

    // =========================================================
    // Ioxium
    // =========================================================

    private static final FluidEntry IOXIUM_ENTRY =
            registerFluid("ioxium");

    public static final RegistryObject<FluidType> IOXIUM_TYPE =
            IOXIUM_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> IOXIUM =
            IOXIUM_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> IOXIUM_FLOWING =
            IOXIUM_ENTRY.flowing;

    // =========================================================
    // Dilonite
    // =========================================================

    private static final FluidEntry DILONITE_ENTRY =
            registerFluid("dilonite");

    public static final RegistryObject<FluidType> DILONITE_TYPE =
            DILONITE_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> DILONITE =
            DILONITE_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> DILONITE_FLOWING =
            DILONITE_ENTRY.flowing;

    // =========================================================
    // Tiberite
    // =========================================================

    private static final FluidEntry TIBERITE_ENTRY =
            registerFluid("tiberite");

    public static final RegistryObject<FluidType> TIBERITE_TYPE =
            TIBERITE_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> TIBERITE =
            TIBERITE_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> TIBERITE_FLOWING =
            TIBERITE_ENTRY.flowing;

    // =========================================================
    // Ostlum
    // =========================================================

    private static final FluidEntry OSTLUM_ENTRY =
            registerFluid("ostlum");

    public static final RegistryObject<FluidType> OSTLUM_TYPE =
            OSTLUM_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> OSTLUM =
            OSTLUM_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> OSTLUM_FLOWING =
            OSTLUM_ENTRY.flowing;

    // =========================================================
    // Emerald
    // =========================================================

    private static final FluidEntry SEIREN_ENTRY =
            registerFluid("seiren");

    public static final RegistryObject<FluidType> SEIREN_TYPE =
            SEIREN_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> SEIREN =
            SEIREN_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> SEIREN_FLOWING =
            SEIREN_ENTRY.flowing;

    // =========================================================
    // Register
    // =========================================================

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}
