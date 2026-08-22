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

        // 1.16.5でのメソッド名は fillItemList です
        // ※各アイテム定義側で .group(ModCreativeTabs.SAKALTI_TAB) を指定していれば、
        // この fillItemList をオーバーライドして手動追加する必要は通常ありません。
        
        public void fillItemList(NonNullList<ItemStack> items) {
            items.add(new ItemStack(ModMetals.HACHILITE_ORE.get()));
            items.add(new ItemStack(ModMetals.HACHILITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.HIROLITE_ORE.get()));
            items.add(new ItemStack(ModMetals.OURITE_ORE.get()));
            items.add(new ItemStack(ModMetals.HACHILITE_RAW.get()));
            items.add(new ItemStack(ModMetals.HACHILITE_INGOT.get()));

            items.add(new ItemStack(ModMetals.KANILITE_ORE.get()));
            items.add(new ItemStack(ModMetals.KANILITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.KANILITE_RAW.get()));
            items.add(new ItemStack(ModMetals.KANILITE_INGOT.get()));

            items.add(new ItemStack(ModMetals.IGNIZ_ORE.get()));
            items.add(new ItemStack(ModMetals.IGNIZ_BLOCK.get()));
            items.add(new ItemStack(ModMetals.IGNIZ_RAW.get()));
            items.add(new ItemStack(ModMetals.IGNIZ_INGOT.get()));

            items.add(new ItemStack(ModMetals.CHIRITE_ORE.get()));
            items.add(new ItemStack(ModMetals.CHIRITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.CHIRITE_RAW.get()));
            items.add(new ItemStack(ModMetals.CHIRITE_INGOT.get()));

            items.add(new ItemStack(ModMetals.MOMONGAITE_ORE.get()));
            items.add(new ItemStack(ModMetals.MOMONGAITE_BLOCK.get()));
            items.add(new ItemStack(ModMetals.MOMONGAITE_RAW.get()));
            items.add(new ItemStack(ModMetals.MOMONGAITE_INGOT.get()));

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
