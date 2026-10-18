package com.sakalti;

import com.sakalti.scaling.HealthCrystals;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/** Creative tab registration using the Minecraft 1.20.1 API. */
public final class ModCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModMain.MODID);

    public static final RegistryObject<CreativeModeTab> SAKALTI_TAB = TABS.register(
            "sakalti",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.sakalti"))
                    .icon(() -> new ItemStack(ModMetals.SEIREN_INGOT.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModMetals.YURU_BLOCK.get());
                        output.accept(ModMetals.YURU_INGOT.get());
                        output.accept(ModMetals.CORAL_BLOCK.get());
                        output.accept(ModMetals.CORAL_INGOT.get());
                        output.accept(ModMetals.HIROLITE_BLOCK.get());
                        output.accept(ModMetals.HIROLITE_INGOT.get());
                        output.accept(ModMetals.OURITE_BLOCK.get());
                        output.accept(ModMetals.OURITE_INGOT.get());
                        output.accept(ModMetals.TIBERIUM_ORE.get());
                        output.accept(ModMetals.SEIREN_ORE.get());
                        output.accept(ModMetals.OSTLUM_ORE.get());
                        output.accept(ModMetals.STANNOIDEA_ORE.get());
                        output.accept(ModMetals.ITSULITE_BLOCK.get());
                        output.accept(ModMetals.ITSULITE_INGOT.get());
                        output.accept(ModMetals.HOTALITE_BLOCK.get());
                        output.accept(ModMetals.HOTALITE_INGOT.get());
                        output.accept(ModMetals.SORALITE_BLOCK.get());
                        output.accept(ModMetals.SORALITE_INGOT.get());
                        output.accept(ModMetals.STANNOIDEA_BLOCK.get());
                        output.accept(ModMetals.STANNOIDEA_INGOT.get());
                        output.accept(ModMetals.SULPHUR_BLOCK.get());
                        output.accept(ModMetals.SULPHUR_CHUNK.get());
                        output.accept(ModMetals.SULPHUR_JEWEL.get());
                        output.accept(ModMetals.SULPHUR_INGOT.get());
                        output.accept(ModMetals.SULPHUR_ORE.get());
                        output.accept(ModMetals.HACHILITE_ORE.get());
                        output.accept(ModMetals.HACHILITE_BLOCK.get());
                        output.accept(ModMetals.HIROLITE_ORE.get());
                        output.accept(ModMetals.OURITE_ORE.get());
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
                        output.accept(HealthCrystals.HEALTH_CRYSTAL.get());
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

    private ModCreativeTabs() {}

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }
}
