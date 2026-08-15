package com.sakalti.sakalti;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class ModFluids {

    public static final String MODID = "sakalti";

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MODID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    private ModFluids() {
    }

    // =========================================================
    // Hachilite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_HACHILITE_TYPE =
            registerType("molten_hachilite");

    public static final RegistryObject<FlowingFluid> MOLTEN_HACHILITE =
            registerFluid("molten_hachilite", MOLTEN_HACHILITE_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_HACHILITE_FLOWING =
            registerFlowing("molten_hachilite_flowing", MOLTEN_HACHILITE_TYPE);

    // =========================================================
    // Kanilite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_KANILITE_TYPE =
            registerType("molten_kanilite");

    public static final RegistryObject<FlowingFluid> MOLTEN_KANILITE =
            registerFluid("molten_kanilite", MOLTEN_KANILITE_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_KANILITE_FLOWING =
            registerFlowing("molten_kanilite_flowing", MOLTEN_KANILITE_TYPE);

    // =========================================================
    // Igniz
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_IGNIZ_TYPE =
            registerType("molten_igniz");

    public static final RegistryObject<FlowingFluid> MOLTEN_IGNIZ =
            registerFluid("molten_igniz", MOLTEN_IGNIZ_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_IGNIZ_FLOWING =
            registerFlowing("molten_igniz_flowing", MOLTEN_IGNIZ_TYPE);

    // =========================================================
    // Chirite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_CHIRITE_TYPE =
            registerType("molten_chirite");

    public static final RegistryObject<FlowingFluid> MOLTEN_CHIRITE =
            registerFluid("molten_chirite", MOLTEN_CHIRITE_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_CHIRITE_FLOWING =
            registerFlowing("molten_chirite_flowing", MOLTEN_CHIRITE_TYPE);

    // =========================================================
    // Momongaite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_MOMONGAITE_TYPE =
            registerType("molten_momongaite");

    public static final RegistryObject<FlowingFluid> MOLTEN_MOMONGAITE =
            registerFluid("molten_momongaite", MOLTEN_MOMONGAITE_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_MOMONGAITE_FLOWING =
            registerFlowing("molten_momongaite_flowing", MOLTEN_MOMONGAITE_TYPE);

    // =========================================================
    // Herdyeen
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_HERDYEEN_TYPE =
            registerType("molten_herdyeen");

    public static final RegistryObject<FlowingFluid> MOLTEN_HERDYEEN =
            registerFluid("molten_herdyeen", MOLTEN_HERDYEEN_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_HERDYEEN_FLOWING =
            registerFlowing("molten_herdyeen_flowing", MOLTEN_HERDYEEN_TYPE);

    // =========================================================
    // Hiroswari
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_HIROSWARI_TYPE =
            registerType("molten_hiroswari");

    public static final RegistryObject<FlowingFluid> MOLTEN_HIROSWARI =
            registerFluid("molten_hiroswari", MOLTEN_HIROSWARI_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_HIROSWARI_FLOWING =
            registerFlowing("molten_hiroswari_flowing", MOLTEN_HIROSWARI_TYPE);

    // =========================================================
    // Marulite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_MARULITE_TYPE =
            registerType("molten_marulite");

    public static final RegistryObject<FlowingFluid> MOLTEN_MARULITE =
            registerFluid("molten_marulite", MOLTEN_MARULITE_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_MARULITE_FLOWING =
            registerFlowing("molten_marulite_flowing", MOLTEN_MARULITE_TYPE);

    // =========================================================
    // Proxia
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_PROXIA_TYPE =
            registerType("molten_proxia");

    public static final RegistryObject<FlowingFluid> MOLTEN_PROXIA =
            registerFluid("molten_proxia", MOLTEN_PROXIA_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_PROXIA_FLOWING =
            registerFlowing("molten_proxia_flowing", MOLTEN_PROXIA_TYPE);

    // =========================================================
    // Ouswari
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_OUSWARI_TYPE =
            registerType("molten_ouswari");

    public static final RegistryObject<FlowingFluid> MOLTEN_OUSWARI =
            registerFluid("molten_ouswari", MOLTEN_OUSWARI_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_OUSWARI_FLOWING =
            registerFlowing("molten_ouswari_flowing", MOLTEN_OUSWARI_TYPE);

    // =========================================================
    // Aurostone
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_AUROSTONE_TYPE =
            registerType("molten_aurostone");

    public static final RegistryObject<FlowingFluid> MOLTEN_AUROSTONE =
            registerFluid("molten_aurostone", MOLTEN_AUROSTONE_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_AUROSTONE_FLOWING =
            registerFlowing("molten_aurostone_flowing", MOLTEN_AUROSTONE_TYPE);

    // =========================================================
    // Deepsteel
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_DEEPSTEEL_TYPE =
            registerType("molten_deepsteel");

    public static final RegistryObject<FlowingFluid> MOLTEN_DEEPSTEEL =
            registerFluid("molten_deepsteel", MOLTEN_DEEPSTEEL_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_DEEPSTEEL_FLOWING =
            registerFlowing("molten_deepsteel_flowing", MOLTEN_DEEPSTEEL_TYPE);

    // =========================================================
    // Chiisteel
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_CHIISTEEL_TYPE =
            registerType("molten_chiisteel");

    public static final RegistryObject<FlowingFluid> MOLTEN_CHIISTEEL =
            registerFluid("molten_chiisteel", MOLTEN_CHIISTEEL_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_CHIISTEEL_FLOWING =
            registerFlowing("molten_chiisteel_flowing", MOLTEN_CHIISTEEL_TYPE);

    // =========================================================
    // Ioxium
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_IOXIUM_TYPE =
            registerType("molten_ioxium");

    public static final RegistryObject<FlowingFluid> MOLTEN_IOXIUM =
            registerFluid("molten_ioxium", MOLTEN_IOXIUM_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_IOXIUM_FLOWING =
            registerFlowing("molten_ioxium_flowing", MOLTEN_IOXIUM_TYPE);

    // =========================================================
    // Dilonite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_DILONITE_TYPE =
            registerType("molten_dilonite");

    public static final RegistryObject<FlowingFluid> MOLTEN_DILONITE =
            registerFluid("molten_dilonite", MOLTEN_DILONITE_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_DILONITE_FLOWING =
            registerFlowing("molten_dilonite_flowing", MOLTEN_DILONITE_TYPE);

    // =========================================================
    // Tiberite
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_TIBERITE_TYPE =
            registerType("molten_tiberite");

    public static final RegistryObject<FlowingFluid> MOLTEN_TIBERITE =
            registerFluid("molten_tiberite", MOLTEN_TIBERITE_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_TIBERITE_FLOWING =
            registerFlowing("molten_tiberite_flowing", MOLTEN_TIBERITE_TYPE);

    // =========================================================
    // Ostlum
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_OSTLUM_TYPE =
            registerType("molten_ostlum");

    public static final RegistryObject<FlowingFluid> MOLTEN_OSTLUM =
            registerFluid("molten_ostlum", MOLTEN_OSTLUM_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_OSTLUM_FLOWING =
            registerFlowing("molten_ostlum_flowing", MOLTEN_OSTLUM_TYPE);

    // =========================================================
    // Emerald
    // =========================================================

    public static final RegistryObject<FluidType> MOLTEN_EMERALD_TYPE =
            registerType("molten_emerald");

    public static final RegistryObject<FlowingFluid> MOLTEN_EMERALD =
            registerFluid("molten_emerald", MOLTEN_EMERALD_TYPE);

    public static final RegistryObject<FlowingFluid> MOLTEN_EMERALD_FLOWING =
            registerFlowing("molten_emerald_flowing", MOLTEN_EMERALD_TYPE);


    // =========================================================
    // Registration helpers
    // =========================================================

    private static RegistryObject<FluidType> registerType(String name) {
        return FLUID_TYPES.register(
                name,
                () -> FluidType.Properties.create()
                        .density(2000)
                        .viscosity(1000)
                        .temperature(1300)
        );
    }

    private static RegistryObject<FlowingFluid> registerFluid(
            String name,
            RegistryObject<FluidType> type
    ) {
        return FLUIDS.register(
                name,
                () -> new Source(name, type)
        );
    }

    private static RegistryObject<FlowingFluid> registerFlowing(
            String name,
            RegistryObject<FluidType> type
    ) {
        return FLUIDS.register(
                name,
                () -> new Flowing(name, type)
        );
    }


    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }


    // =========================================================
    // Source
    // =========================================================

    private static class Source extends FlowingFluid {

        private final String name;
        private final RegistryObject<FluidType> type;

        private Source(
                String name,
                RegistryObject<FluidType> type
        ) {
            this.name = name;
            this.type = type;
        }

        @Override
        public FluidType getFluidType() {
            return type.get();
        }

        @Override
        protected void beforeDestroyingBlock(
                net.minecraft.world.level.LevelAccessor level,
                net.minecraft.core.BlockPos pos,
                net.minecraft.world.level.block.state.BlockState state
        ) {
        }

        @Override
        protected void flowInto(
                net.minecraft.world.level.LevelAccessor level,
                net.minecraft.core.BlockPos pos,
                net.minecraft.world.level.block.state.BlockState blockState,
                net.minecraft.core.Direction direction,
                FluidState fluidState
        ) {
            super.flowInto(level, pos, blockState, direction, fluidState);
        }

        @Override
        public Fluid getFlowing() {
            return getFluidByName(name + "_flowing");
        }

        @Override
        public Fluid getSource() {
            return this;
        }

        @Override
        protected boolean canConvertToSource(
                net.minecraft.world.level.Level level
        ) {
            return true;
        }

        @Override
        protected int getSlopeFindDistance(
                net.minecraft.world.level.LevelReader level
        ) {
            return 4;
        }

        @Override
        protected int getDropOff(
                net.minecraft.world.level.LevelReader level
        ) {
            return 1;
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
            return fluid == this || fluid == getFlowing();
        }
    }


    // =========================================================
    // Flowing
    // =========================================================

    private static class Flowing extends FlowingFluid {

        private final String name;
        private final RegistryObject<FluidType> type;

        private Flowing(
                String name,
                RegistryObject<FluidType> type
        ) {
            this.name = name;
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
            return getFluidByName(name.replace("_flowing", ""));
        }

        @Override
        protected boolean canConvertToSource(
                net.minecraft.world.level.Level level
        ) {
            return false;
        }

        @Override
        protected int getSlopeFindDistance(
                net.minecraft.world.level.LevelReader level
        ) {
            return 4;
        }

        @Override
        protected int getDropOff(
                net.minecraft.world.level.LevelReader level
        ) {
            return 1;
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
            return fluid == this || fluid == getSource();
        }
    }


    private static Fluid getFluidByName(String name) {
        return FLUIDS.getEntries()
                .stream()
                .filter(entry -> entry.getId().getPath().equals(name))
                .findFirst()
                .map(RegistryObject::get)
                .orElseThrow();
    }
}
