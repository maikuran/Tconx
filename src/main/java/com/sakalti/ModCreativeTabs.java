package com.sakalti;

import net.minecraft.core.registries.Registries;
import com.sakalti.scaling.HealthCrystals;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "sakalti");

    public static final RegistryObject<CreativeModeTab> SAKALTI_TAB =
            CREATIVE_MODE_TABS.register("sakalti", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.sakalti"))
                            .icon(() -> new ItemStack(ModMetals.SEIREN_INGOT.get()))
                            .displayItems((parameters, output) -> {

                                output.accept(ModMetals.HACHILITE_ORE.get());
                                output.accept(ModMetals.HACHILITE_BLOCK.get());
                                output.accept(ModMetals.HACHILITE_RAW.get());
                                output.accept(ModMetals.HACHILITE_INGOT.get());

                                output.accept(ModMetals.KANILITE_ORE.get());
                                output.accept(ModMetals.KANILITE_BLOCK.get());
                                output.accept(ModMetals.KANILITE_RAW.get());
                                output.accept(ModMetals.KANILITE_INGOT.get());

                                output.accept(ModMetals.IGNIZ_ORE.get());
                                output.accept(ModMetals.IGNIZ_BLOCK.get());
                                output.accept(ModMetals.IGNIZ_RAW.get());
                                output.accept(ModMetals.IGNIZ_INGOT.get());

                                output.accept(ModMetals.CHIRITE_ORE.get());
                                output.accept(ModMetals.CHIRITE_BLOCK.get());
                                output.accept(ModMetals.CHIRITE_RAW.get());
                                output.accept(ModMetals.CHIRITE_INGOT.get());

                                output.accept(ModMetals.MOMONGAITE_ORE.get());
                                output.accept(ModMetals.MOMONGAITE_BLOCK.get());
                                output.accept(ModMetals.MOMONGAITE_RAW.get());
                                output.accept(ModMetals.MOMONGAITE_INGOT.get());
                                output.accept(HealthCrystals_HEALTH_CRYSTAL.get());
                                output.accept(ModMetals.HERDYEEN_BLOCK.get());
                                output.accept(ModMetals.HERDYEEN_INGOT.get());

                                output.accept(ModMetals.HIROSWARI_BLOCK.get());
                                output.accept(ModMetals.HIROSWARI_INGOT.get());

                                output.accept(ModMetals.MARULITE_BLOCK.get());
                                output.accept(ModMetals.MARULITE_INGOT.get());

                                
                                output.accept(ModMetals.PROXIA_BLOCK.get());
                                output.accept(ModMetals.PROXIA_INGOT.get());

                                output.accept(ModMetals.OUSWARI_BLOCK.get());
                                output.accept(ModMetals.OUSWARI_INGOT.get());

                                output.accept(ModMetals.AUROSTONE_BLOCK.get());
                                output.accept(ModMetals.AUROSTONE_INGOT.get());

                                output.accept(ModMetals.DEEPSTEEL_BLOCK.get());
                                output.accept(ModMetals.DEEPSTEEL_INGOT.get());
                                output.accept(ModMetals.DEEPCHUNK.get());

                                output.accept(ModMetals.SEIREN_BLOCK.get());
                                output.accept(ModMetals.SEIREN_INGOT.get());

                                output.accept(ModMetals.CHIISTEEL_BLOCK.get());
                                output.accept(ModMetals.CHIISTEEL_INGOT.get());

                                output.accept(ModMetals.IOXIUM_BLOCK.get());
                                output.accept(ModMetals.IOXIUM_INGOT.get());

                                output.accept(ModMetals.DILONITE_BLOCK.get());
                                output.accept(ModMetals.DILONITE_INGOT.get());

                                output.accept(ModMetals.TIBERIUM_BLOCK.get());
                                output.accept(ModMetals.TIBERIUM_INGOT.get());

                                output.accept(ModMetals.OSTLUM_BLOCK.get());
                                output.accept(ModMetals.OSTLUM_INGOT.get());
                            })
                            .build()
            );

    private ModCreativeTabs() {
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
