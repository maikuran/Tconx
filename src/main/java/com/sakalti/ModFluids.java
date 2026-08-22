package com.sakalti;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModFluids {

    public static final String MODID = "sakalti";

    private ModFluids() {
    }

    // =========================================================
    // Registers (1.16.5 では FLUIDS のみ登録します)
    // =========================================================

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);

    // =========================================================
    // Fluid Entry
    // =========================================================

    private static final class FluidEntry {

        final String name;

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

        // Source
        entry.source = FLUIDS.register(
                name,
                () -> new ForgeFlowingFluid.Source(
                        createProperties(entry)
                )
        );

        // Flowing
        entry.flowing = FLUIDS.register(
                name + "_flow",
                () -> new ForgeFlowingFluid.Flowing(
                        createProperties(entry)
                )
        );

        return entry;
    }

    // =========================================================
    // ForgeFlowingFluid Properties (1.16.5 方式)
    // =========================================================

    private static ForgeFlowingFluid.Properties createProperties(FluidEntry entry) {
        ResourceLocation stillTexture = new ResourceLocation(MODID, "fluid/" + entry.name + "_still");
        ResourceLocation flowingTexture = new ResourceLocation(MODID, "fluid/" + entry.name + "_flowing");

        // 1.16.5 では FluidAttributes.builder を使ってテクスチャや物性を指定します
        FluidAttributes.Builder attributes = FluidAttributes.builder(stillTexture, flowingTexture)
                .density(2000)
                .viscosity(1000)
                .temperature(1900);

        return new ForgeFlowingFluid.Properties(
                entry.source,
                entry.flowing,
                attributes
        )
                .slopeFindDistance(4)
                .levelDecreasePerBlock(1);
    }

    // =========================================================
    // Fluid Entries
    // =========================================================

    private static final FluidEntry HACHILITE_ENTRY = registerFluid("hachilite");
    public static final RegistryObject<ForgeFlowingFluid> HACHILITE = HACHILITE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> HACHILITE_FLOWING = HACHILITE_ENTRY.flowing;

    private static final FluidEntry KANILITE_ENTRY = registerFluid("kanilite");
    public static final RegistryObject<ForgeFlowingFluid> KANILITE = KANILITE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> KANILITE_FLOWING = KANILITE_ENTRY.flowing;

    private static final FluidEntry IGNIZ_ENTRY = registerFluid("igniz");
    public static final RegistryObject<ForgeFlowingFluid> IGNIZ = IGNIZ_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> IGNIZ_FLOWING = IGNIZ_ENTRY.flowing;

    private static final FluidEntry CHIRITE_ENTRY = registerFluid("chirite");
    public static final RegistryObject<ForgeFlowingFluid> CHIRITE = CHIRITE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> CHIRITE_FLOWING = CHIRITE_ENTRY.flowing;

    private static final FluidEntry MOMONGAITE_ENTRY = registerFluid("momongaite");
    public static final RegistryObject<ForgeFlowingFluid> MOMONGAITE = MOMONGAITE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> MOMONGAITE_FLOWING = MOMONGAITE_ENTRY.flowing;

    private static final FluidEntry HERDYEEN_ENTRY = registerFluid("herdyeen");
    public static final RegistryObject<ForgeFlowingFluid> HERDYEEN = HERDYEEN_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> HERDYEEN_FLOWING = HERDYEEN_ENTRY.flowing;

    private static final FluidEntry HIROSWARI_ENTRY = registerFluid("hiroswari");
    public static final RegistryObject<ForgeFlowingFluid> HIROSWARI = HIROSWARI_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> HIROSWARI_FLOWING = HIROSWARI_ENTRY.flowing;

    private static final FluidEntry MARULITE_ENTRY = registerFluid("marulite");
    public static final RegistryObject<ForgeFlowingFluid> MARULITE = MARULITE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> MARULITE_FLOWING = MARULITE_ENTRY.flowing;

    private static final FluidEntry PROXIA_ENTRY = registerFluid("proxia");
    public static final RegistryObject<ForgeFlowingFluid> PROXIA = PROXIA_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> PROXIA_FLOWING = PROXIA_ENTRY.flowing;

    private static final FluidEntry OUSWARI_ENTRY = registerFluid("ouswari");
    public static final RegistryObject<ForgeFlowingFluid> OUSWARI = OUSWARI_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> OUSWARI_FLOWING = OUSWARI_ENTRY.flowing;

    private static final FluidEntry AUROSTONE_ENTRY = registerFluid("aurostone");
    public static final RegistryObject<ForgeFlowingFluid> AUROSTONE = AUROSTONE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> AUROSTONE_FLOWING = AUROSTONE_ENTRY.flowing;

    private static final FluidEntry DEEPSTEEL_ENTRY = registerFluid("deepsteel");
    public static final RegistryObject<ForgeFlowingFluid> DEEPSTEEL = DEEPSTEEL_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> DEEPSTEEL_FLOWING = DEEPSTEEL_ENTRY.flowing;

    private static final FluidEntry CHIISTEEL_ENTRY = registerFluid("chiisteel");
    public static final RegistryObject<ForgeFlowingFluid> CHIISTEEL = CHIISTEEL_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> CHIISTEEL_FLOWING = CHIISTEEL_ENTRY.flowing;

    private static final FluidEntry IOXIUM_ENTRY = registerFluid("ioxium");
    public static final RegistryObject<ForgeFlowingFluid> IOXIUM = IOXIUM_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> IOXIUM_FLOWING = IOXIUM_ENTRY.flowing;

    private static final FluidEntry DILONITE_ENTRY = registerFluid("dilonite");
    public static final RegistryObject<ForgeFlowingFluid> DILONITE = DILONITE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> DILONITE_FLOWING = DILONITE_ENTRY.flowing;

    private static final FluidEntry TIBERIUM_ENTRY = registerFluid("tiberium");
    public static final RegistryObject<ForgeFlowingFluid> TIBERIUM = TIBERIUM_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> TIBERIUM_FLOWING = TIBERIUM_ENTRY.flowing;

    private static final FluidEntry OSTLUM_ENTRY = registerFluid("ostlum");
    public static final RegistryObject<ForgeFlowingFluid> OSTLUM = OSTLUM_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> OSTLUM_FLOWING = OSTLUM_ENTRY.flowing;

    private static final FluidEntry SEIREN_ENTRY = registerFluid("seiren");
    public static final RegistryObject<ForgeFlowingFluid> SEIREN = SEIREN_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> SEIREN_FLOWING = SEIREN_ENTRY.flowing;

    private static final FluidEntry OURITE_ENTRY = registerFluid("ourite");
    public static final RegistryObject<ForgeFlowingFluid> OURITE = OURITE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> OURITE_FLOWING = OURITE_ENTRY.flowing;

    private static final FluidEntry HIROLITE_ENTRY = registerFluid("hirolite");
    public static final RegistryObject<ForgeFlowingFluid> HIROLITE = HIROLITE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> HIROLITE_FLOWING = HIROLITE_ENTRY.flowing;

    private static final FluidEntry CORAL_ENTRY = registerFluid("coral");
    public static final RegistryObject<ForgeFlowingFluid> CORAL = CORAL_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> CORAL_FLOWING = CORAL_ENTRY.flowing;

    // =========================================================
    // Register
    // =========================================================

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
