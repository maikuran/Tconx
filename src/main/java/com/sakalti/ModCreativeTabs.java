package com.sakalti;

import com.sakalti.scaling.HealthCrystals;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class ModCreativeTabs {

    public static final ItemGroup SAKALTI_TAB = new ItemGroup("sakalti") {

        public ItemStack makeIcon() {
            return new ItemStack(ModMetals.SEIREN_INGOT.get());
        }

        public ITextComponent getDisplayName() {
            return new TranslationTextComponent("itemGroup.sakalti");
        }

        public void fillItemList(NonNullList<ItemStack> items) {
            // HACHILITE シリーズ
            items.add(new ItemStack(ModMetals.HACHILITE_ORE.get()));
            items.add(new ItemStack(ModMetals.HACHILITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.HACHILITE_RAW.get()));
            items.add(new ItemStack(ModMetals.HACHILITE_INGOT.get()));

            // HIROLITE シリーズ
            items.add(new ItemStack(ModMetals.HIROLITE_ORE.get()));
            items.add(new ItemStack(ModMetals.HIROLITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.HIROLITE_INGOT.get()));

            // OURITE シリーズ
            items.add(new ItemStack(ModMetals.OURITE_ORE.get()));
            items.add(new ItemStack(ModMetals.OURITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.OURITE_INGOT.get()));

            // KANILITE シリーズ
            items.add(new ItemStack(ModMetals.KANILITE_ORE.get()));
            items.add(new ItemStack(ModMetals.KANILITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.KANILITE_RAW.get()));
            items.add(new ItemStack(ModMetals.KANILITE_INGOT.get()));

            // IGNIZ シリーズ
            items.add(new ItemStack(ModMetals.IGNIZ_ORE.get()));
            items.add(new ItemStack(ModMetals.IGNIZ_BLOCK.get()));
            items.add(new ItemStack(ModMetals.IGNIZ_RAW.get()));
            items.add(new ItemStack(ModMetals.IGNIZ_INGOT.get()));

            // CHIRITE シリーズ
            items.add(new ItemStack(ModMetals.CHIRITE_ORE.get()));
            items.add(new ItemStack(ModMetals.CHIRITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.CHIRITE_RAW.get()));
            items.add(new ItemStack(ModMetals.CHIRITE_INGOT.get()));

            // MOMONGAITE シリーズ
            items.add(new ItemStack(ModMetals.MOMONGAITE_ORE.get()));
            items.add(new ItemStack(ModMetals.MOMONGAITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.MOMONGAITE_RAW.get()));
            items.add(new ItemStack(ModMetals.MOMONGAITE_INGOT.get()));

            // その他
            items.add(new ItemStack(HealthCrystals.HEALTH_CRYSTAL.get()));

            items.add(new ItemStack(ModMetals.HERDYEEN_BLOCK.get()));
            items.add(new ItemStack(ModMetals.HERDYEEN_INGOT.get()));

            items.add(new ItemStack(ModMetals.HIROSWARI_BLOCK.get()));
            items.add(new ItemStack(ModMetals.HIROSWARI_INGOT.get()));

            items.add(new ItemStack(ModMetals.MARULITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.MARULITE_INGOT.get()));

            items.add(new ItemStack(ModMetals.PROXIA_BLOCK.get()));
            items.add(new ItemStack(ModMetals.PROXIA_INGOT.get()));

            items.add(new ItemStack(ModMetals.OUSWARI_BLOCK.get()));
            items.add(new ItemStack(ModMetals.OUSWARI_INGOT.get()));

            items.add(new ItemStack(ModMetals.AUROSTONE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.AUROSTONE_INGOT.get()));

            items.add(new ItemStack(ModMetals.DEEPSTEEL_BLOCK.get()));
            items.add(new ItemStack(ModMetals.DEEPSTEEL_INGOT.get()));
            items.add(new ItemStack(ModMetals.DEEPCHUNK.get()));

            items.add(new ItemStack(ModMetals.SEIREN_BLOCK.get()));
            items.add(new ItemStack(ModMetals.SEIREN_INGOT.get()));

            items.add(new ItemStack(ModMetals.CHIISTEEL_BLOCK.get()));
            items.add(new ItemStack(ModMetals.CHIISTEEL_INGOT.get()));

            items.add(new ItemStack(ModMetals.IOXIUM_BLOCK.get()));
            items.add(new ItemStack(ModMetals.IOXIUM_INGOT.get()));

            items.add(new ItemStack(ModMetals.DILONITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.DILONITE_INGOT.get()));

            items.add(new ItemStack(ModMetals.TIBERIUM_BLOCK.get()));
            items.add(new ItemStack(ModMetals.TIBERIUM_INGOT.get()));

            items.add(new ItemStack(ModMetals.OSTLUM_BLOCK.get()));
            items.add(new ItemStack(ModMetals.OSTLUM_INGOT.get()));
        }
    };

    private ModCreativeTabs() {
    }
}
