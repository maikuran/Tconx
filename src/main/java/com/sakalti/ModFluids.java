package com.sakalti.sakalti;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModFluids {

    public static final String MODID = "sakalti";

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MODID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);

    private ModFluids() {
    }

    // =========================================================
    // Fluids
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_HACHILITE_TYPE =
            registerType("molten_hachilite");
    public static final RegistryObject<FlowingFluid> MOLTEN_HACHILITE =
            registerSource("molten_hachilite", MOLTEN_HACHILITE_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_HACHILITE_FLOWING =
            registerFlowing("molten_hachilite_flowing", MOLTEN_HACHILITE_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_KANILITE_TYPE =
            registerType("molten_kanilite");
    public static final RegistryObject<FlowingFluid> MOLTEN_KANILITE =
            registerSource("molten_kanilite", MOLTEN_KANILITE_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_KANILITE_FLOWING =
            registerFlowing("molten_kanilite_flowing", MOLTEN_KANILITE_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_IGNIZ_TYPE =
            registerType("molten_igniz");
    public static final RegistryObject<FlowingFluid> MOLTEN_IGNIZ =
            registerSource("molten_igniz", MOLTEN_IGNIZ_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_IGNIZ_FLOWING =
            registerFlowing("molten_igniz_flowing", MOLTEN_IGNIZ_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_CHIRITE_TYPE =
            registerType("molten_chirite");
    public static final RegistryObject<FlowingFluid> MOLTEN_CHIRITE =
            registerSource("molten_chirite", MOLTEN_CHIRITE_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_CHIRITE_FLOWING =
            registerFlowing("molten_chirite_flowing", MOLTEN_CHIRITE_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_MOMONGAITE_TYPE =
            registerType("molten_momongaite");
    public static final RegistryObject<FlowingFluid> MOLTEN_MOMONGAITE =
            registerSource("molten_momongaite", MOLTEN_MOMONGAITE_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_MOMONGAITE_FLOWING =
            registerFlowing("molten_momongaite_flowing", MOLTEN_MOMONGAITE_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_HERDYEEN_TYPE =
            registerType("molten_herdyeen");
    public static final RegistryObject<FlowingFluid> MOLTEN_HERDYEEN =
            registerSource("molten_herdyeen", MOLTEN_HERDYEEN_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_HERDYEEN_FLOWING =
            registerFlowing("molten_herdyeen_flowing", MOLTEN_HERDYEEN_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_HIROSWARI_TYPE =
            registerType("molten_hiroswari");
    public static final RegistryObject<FlowingFluid> MOLTEN_HIROSWARI =
            registerSource("molten_hiroswari", MOLTEN_HIROSWARI_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_HIROSWARI_FLOWING =
            registerFlowing("molten_hiroswari_flowing", MOLTEN_HIROSWARI_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_MARULITE_TYPE =
            registerType("molten_marulite");
    public static final RegistryObject<FlowingFluid> MOLTEN_MARULITE =
            registerSource("molten_marulite", MOLTEN_MARULITE_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_MARULITE_FLOWING =
            registerFlowing("molten_marulite_flowing", MOLTEN_MARULITE_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_PROXIA_TYPE =
            registerType("molten_proxia");
    public static final RegistryObject<FlowingFluid> MOLTEN_PROXIA =
            registerSource("molten_proxia", MOLTEN_PROXIA_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_PROXIA_FLOWING =
            registerFlowing("molten_proxia_flowing", MOLTEN_PROXIA_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_OUSWARI_TYPE =
            registerType("molten_ouswari");
    public static final RegistryObject<FlowingFluid> MOLTEN_OUSWARI =
            registerSource("molten_ouswari", MOLTEN_OUSWARI_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_OUSWARI_FLOWING =
            registerFlowing("molten_ouswari_flowing", MOLTEN_OUSWARI_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_AUROSTONE_TYPE =
            registerType("molten_aurostone");
    public static final RegistryObject<FlowingFluid> MOLTEN_AUROSTONE =
            registerSource("molten_aurostone", MOLTEN_AUROSTONE_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_AUROSTONE_FLOWING =
            registerFlowing("molten_aurostone_flowing", MOLTEN_AUROSTONE_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_DEEPSTEEL_TYPE =
            registerType("molten_deepsteel");
    public static final RegistryObject<FlowingFluid> MOLTEN_DEEPSTEEL =
            registerSource("molten_deepsteel", MOLTEN_DEEPSTEEL_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_DEEPSTEEL_FLOWING =
            registerFlowing("molten_deepsteel_flowing", MOLTEN_DEEPSTEEL_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_CHIISTEEL_TYPE =
            registerType("molten_chiisteel");
    public static final RegistryObject<FlowingFluid> MOLTEN_CHIISTEEL =
            registerSource("molten_chiisteel", MOLTEN_CHIISTEEL_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_CHIISTEEL_FLOWING =
            registerFlowing("molten_chiisteel_flowing", MOLTEN_CHIISTEEL_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_IOXIUM_TYPE =
            registerType("molten_ioxium");
    public static final RegistryObject<FlowingFluid> MOLTEN_IOXIUM =
            registerSource("molten_ioxium", MOLTEN_IOXIUM_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_IOXIUM_FLOWING =
            registerFlowing("molten_ioxium_flowing", MOLTEN_IOXIUM_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_DILONITE_TYPE =
            registerType("molten_dilonite");
    public static final RegistryObject<FlowingFluid> MOLTEN_DILONITE =
            registerSource("molten_dilonite", MOLTEN_DILONITE_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_DILONITE_FLOWING =
            registerFlowing("molten_dilonite_flowing", MOLTEN_DILONITE_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_TIBERITE_TYPE =
            registerType("molten_tiberite");
    public static final RegistryObject<FlowingFluid> MOLTEN_TIBERITE =
            registerSource("molten_tiberite", MOLTEN_TIBERITE_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_TIBERITE_FLOWING =
            registerFlowing("molten_tiberite_flowing", MOLTEN_TIBERITE_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_OSTLUM_TYPE =
            registerType("molten_ostlum");
    public static final RegistryObject<FlowingFluid> MOLTEN_OSTLUM =
            registerSource("molten_ostlum", MOLTEN_OSTLUM_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_OSTLUM_FLOWING =
            registerFlowing("molten_ostlum_flowing", MOLTEN_OSTLUM_TYPE);

    public static final RegistryObject<FluidType> MOLTEN_EMERALD_TYPE =
            registerType("molten_emerald");
    public static final RegistryObject<FlowingFluid> MOLTEN_EMERALD =
            registerSource("molten_emerald", MOLTEN_EMERALD_TYPE);
    public static final RegistryObject<FlowingFluid> MOLTEN_EMERALD_FLOWING =
            registerFlowing("molten_emerald_flowing", MOLTEN_EMERALD_TYPE);


    // =========================================================
    // Registration
    // =========================================================

    private static RegistryObject<FluidType> registerType(String name) {
        return FLUID_TYPES.register(
                name,
                () -> new FluidType(
                        FluidType.Properties.create()
                                .density(2000)
                                .viscosity(1000)
                                .temperature(1300)
                )
        );
    }

    private static RegistryObject<FlowingFluid> registerSource(
            String name,
            RegistryObject<FluidType> type
    ) {
        return FLUIDS.register(
                name,
                () -> new Source(type)
        );
    }

    private static RegistryObject<FlowingFluid> registerFlowing(
            String name,
            RegistryObject<FluidType> type
    ) {
        return FLUIDS.register(
                name,
                () -> new Flowing(type)
        );
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }


    // =========================================================
    // Source Fluid
    // =========================================================

    private static final class Source extends FlowingFluid {

        private final RegistryObject<FluidType> type;

        private Source(RegistryObject<FluidType> type) {
            this.type = type;
        }

        @Override
        public FluidType getFluidType() {
            return type.get();
        }

        @Override
        public Fluid getFlowing() {
            throw new UnsupportedOperationException(
                    "Source fluid flowing reference must be overridden per fluid."
            );
        }

        @Override
        public Fluid getSource() {
            return this;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        protected int getDropOff(
                net.minecraft.world.level.LevelReader level
        ) {
            return 1;
        }

        @Override
        protected int getSlopeFindDistance(
                net.minecraft.world.level.LevelReader level
        ) {
            return 4;
        }

        @Override
        protected boolean canConvertToSource(
                net.minecraft.world.level.Level level
        ) {
            return true;
        }

        @Override
        public Item getBucket() {
            return net.minecraft.world.item.Items.BUCKET;
        }

        @Override
        protected net.minecraft.world.level.block.state.BlockState createLegacyBlock(
                FluidState state
        ) {
            return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
        }

        @Override
        public boolean isSame(Fluid fluid) {
            return fluid == this;
        }
    }


    // =========================================================
    // Flowing Fluid
    // =========================================================

    private static final class Flowing extends FlowingFluid {

        private final RegistryObject<FluidType> type;

        private Flowing(RegistryObject<FluidType> type) {
            this.type = type;
        }

        @Override
        public FluidType getFluidType() {
            return type.get();
        }

        @Override
        public Fluid getFlowing() {
            return this;
        }

        @Override
        public Fluid getSource() {
            return null;
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        protected int getDropOff(
                net.minecraft.world.level.LevelReader level
        ) {
            return 1;
        }

        @Override
        protected int getSlopeFindDistance(
                net.minecraft.world.level.LevelReader level
        ) {
            return 4;
        }

        @Override
        protected boolean canConvertToSource(
                net.minecraft.world.level.Level level
        ) {
            return false;
        }

        @Override
        public Item getBucket() {
            return net.minecraft.world.item.Items.BUCKET;
        }

        @Override
        protected net.minecraft.world.level.block.state.BlockState createLegacyBlock(
                FluidState state
        ) {
            return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
        }

        @Override
        public boolean isSame(Fluid fluid) {
            return fluid == this;
        }
    }
}
