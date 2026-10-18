package com.sakalti;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

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

    public static final DeferredRegister<Block> FLUID_BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

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
                name + "_flow",
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

        String textureName = name.equals("traits") ? "still" : (name.equals("urium") ? "uri" : name);
        ResourceLocation stillTexture = new ResourceLocation("sakalti", "fluid/" + textureName + "_still");
        ResourceLocation flowingTexture = new ResourceLocation("sakalti", "fluid/" + textureName + "_flow");

        return new FluidType(
                FluidType.Properties.create()
                        .density(2000)
                        .viscosity(1000)
                        .temperature(name.equals("molten_sulphur") ? 200 : 1900)
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
        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(
                entry.type,
                entry.source,
                entry.flowing
        ).slopeFindDistance(4).levelDecreasePerBlock(1);
        if ("itsulite".equals(entry.name)) properties.block(ITSULITE_BLOCK);
        return properties;
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

    private static final FluidEntry TIBERIUM_ENTRY =
            registerFluid("tiberium");

    public static final RegistryObject<FluidType> TIBERIUM_TYPE =
            TIBERIUM_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> TIBERIUM =
            TIBERIUM_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> TIBERIUM_FLOWING =
            TIBERIUM_ENTRY.flowing;

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
    private static final FluidEntry OURITE_ENTRY =
            registerFluid("ourite");

    public static final RegistryObject<FluidType> OURITE_TYPE =
            OURITE_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> OURITE =
            OURITE_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> OURITE_FLOWING =
            OURITE_ENTRY.flowing;
    private static final FluidEntry HIROLITE_ENTRY =
            registerFluid("hirolite");

    public static final RegistryObject<FluidType> HIROLITE_TYPE =
            HIROLITE_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> HIROLITE =
            HIROLITE_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> HIROLITE_FLOWING =
            HIROLITE_ENTRY.flowing;
    private static final FluidEntry CORAL_ENTRY =
            registerFluid("coral");

    public static final RegistryObject<FluidType> CORAL_TYPE =
            CORAL_ENTRY.type;

    public static final RegistryObject<ForgeFlowingFluid> CORAL =
            CORAL_ENTRY.source;

    public static final RegistryObject<ForgeFlowingFluid> CORAL_FLOWING =
            CORAL_ENTRY.flowing;

    // Fluids referenced by alloy/material-fluid recipes retained from the 1.20.1 resource set.
    private static final FluidEntry KERIUM_ENTRY = registerFluid("kerium");
    private static final FluidEntry NEHILIUM_ENTRY = registerFluid("nehilium");
    private static final FluidEntry URI_ENTRY = registerFluid("uri");
    private static final FluidEntry URIUM_ENTRY = registerFluid("urium");
    private static final FluidEntry TRAITS_ENTRY = registerFluid("traits");
    private static final FluidEntry VIBERIUM_ENTRY = registerFluid("viberium");
    private static final FluidEntry MOLTEN_KERIUM_ENTRY = registerFluid("molten_kerium");
    private static final FluidEntry MOLTEN_NEHILIUM_ENTRY = registerFluid("molten_nehilium");
    private static final FluidEntry MOLTEN_URI_ENTRY = registerFluid("molten_uri");
    private static final FluidEntry MOLTEN_VIBERIUM_ENTRY = registerFluid("molten_viberium");
    private static final FluidEntry MOLTEN_AUROSTONE_ENTRY = registerFluid("molten_aurostone");
    private static final FluidEntry MOLTEN_CHIISTEEL_ENTRY = registerFluid("molten_chiisteel");
    private static final FluidEntry MOLTEN_CHIRITE_ENTRY = registerFluid("molten_chirite");
    private static final FluidEntry MOLTEN_CORAL_ENTRY = registerFluid("molten_coral");
    private static final FluidEntry MOLTEN_DEEPSTEEL_ENTRY = registerFluid("molten_deepsteel");
    private static final FluidEntry MOLTEN_DILONITE_ENTRY = registerFluid("molten_dilonite");
    private static final FluidEntry MOLTEN_HACHILITE_ENTRY = registerFluid("molten_hachilite");
    private static final FluidEntry MOLTEN_HERDYEEN_ENTRY = registerFluid("molten_herdyeen");
    private static final FluidEntry MOLTEN_HIROLITE_ENTRY = registerFluid("molten_hirolite");
    private static final FluidEntry MOLTEN_HIROSWARI_ENTRY = registerFluid("molten_hiroswari");
    private static final FluidEntry MOLTEN_HOTALITE_ENTRY = registerFluid("molten_hotalite");
    private static final FluidEntry MOLTEN_IGNIZ_ENTRY = registerFluid("molten_igniz");
    private static final FluidEntry MOLTEN_IOXIUM_ENTRY = registerFluid("molten_ioxium");
    private static final FluidEntry MOLTEN_KANILITE_ENTRY = registerFluid("molten_kanilite");
    private static final FluidEntry MOLTEN_MARULITE_ENTRY = registerFluid("molten_marulite");
    private static final FluidEntry MOLTEN_MOMONGAITE_ENTRY = registerFluid("molten_momongaite");
    private static final FluidEntry MOLTEN_OSTLUM_ENTRY = registerFluid("molten_ostlum");
    private static final FluidEntry MOLTEN_OURITE_ENTRY = registerFluid("molten_ourite");
    private static final FluidEntry MOLTEN_OUSWARI_ENTRY = registerFluid("molten_ouswari");
    private static final FluidEntry MOLTEN_PROXIA_ENTRY = registerFluid("molten_proxia");
    private static final FluidEntry MOLTEN_SEIREN_ENTRY = registerFluid("molten_seiren");
    private static final FluidEntry MOLTEN_SORALITE_ENTRY = registerFluid("molten_soralite");
    private static final FluidEntry MOLTEN_STANNOIDEA_ENTRY = registerFluid("molten_stannoidea");
    private static final FluidEntry MOLTEN_TIBERIUM_ENTRY = registerFluid("molten_tiberium");
    private static final FluidEntry MOLTEN_YURU_ENTRY = registerFluid("molten_yuru");

    // Restored 1.16.5 fluid registrations
    private static final FluidEntry ITSULITE_ENTRY = registerFluid("itsulite");
    public static final RegistryObject<FluidType> ITSULITE_TYPE = ITSULITE_ENTRY.type;
    public static final RegistryObject<ForgeFlowingFluid> ITSULITE = ITSULITE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> ITSULITE_FLOWING = ITSULITE_ENTRY.flowing;
    public static final RegistryObject<LiquidBlock> ITSULITE_BLOCK = FLUID_BLOCKS.register(
            "itsulite", () -> new LiquidBlock(ITSULITE_ENTRY.source,
                    BlockBehaviour.Properties.of().noCollission().strength(100.0F).noLootTable()));


    private static final FluidEntry HOTALITE_ENTRY = registerFluid("hotalite");
    public static final RegistryObject<FluidType> HOTALITE_TYPE = HOTALITE_ENTRY.type;
    public static final RegistryObject<ForgeFlowingFluid> HOTALITE = HOTALITE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> HOTALITE_FLOWING = HOTALITE_ENTRY.flowing;

    private static final FluidEntry SORALITE_ENTRY = registerFluid("soralite");
    public static final RegistryObject<FluidType> SORALITE_TYPE = SORALITE_ENTRY.type;
    public static final RegistryObject<ForgeFlowingFluid> SORALITE = SORALITE_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> SORALITE_FLOWING = SORALITE_ENTRY.flowing;

    private static final FluidEntry STANNOIDEA_ENTRY = registerFluid("stannoidea");
    private static final FluidEntry YURU_ENTRY = registerFluid("yuru");
    private static final FluidEntry SULPHUR_ENTRY = registerFluid("molten_sulphur");
    public static final RegistryObject<FluidType> SULPHUR_TYPE = SULPHUR_ENTRY.type;
    public static final RegistryObject<ForgeFlowingFluid> SULPHUR = SULPHUR_ENTRY.source;
    public static final RegistryObject<ForgeFlowingFluid> SULPHUR_FLOWING = SULPHUR_ENTRY.flowing;

    // =========================================================
    // Register
    // Fluids referenced by alloy/material-fluid recipes retained from the 1.20.1 resource set.
    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
        FLUID_BLOCKS.register(eventBus);
    }
}
