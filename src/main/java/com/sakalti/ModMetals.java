package com.sakalti;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModMetals {

    public static final String MODID = "sakalti";

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    private ModMetals() {
    }


    // ============================================================
    // Hirolite / Ourite / Hachilite
    // ============================================================

    public static final RegistryObject<Block> HIROLITE_ORE =
            registerBlock("hirolite_ore", 41.0F);

    public static final RegistryObject<Block> OURITE_ORE =
            registerBlock("ourite_ore", 44.0F);

    public static final RegistryObject<Block> HACHILITE_ORE =
            registerBlock("hachilite_ore", 3.0F);

    public static final RegistryObject<Block> HACHILITE_BLOCK =
            registerBlock("hachilite_block", 7.0F);

    public static final RegistryObject<Item> HACHILITE_RAW =
            registerItem("hachilite_raw");

    public static final RegistryObject<Item> HACHILITE_INGOT =
            registerItem("hachilite_ingot");


    // ============================================================
    // Kanilite
    // ============================================================

    public static final RegistryObject<Block> KANILITE_ORE =
            registerBlock("kanilite_ore", 4.0F);

    public static final RegistryObject<Block> KANILITE_BLOCK =
            registerBlock("kanilite_block", 10.0F);

    public static final RegistryObject<Item> KANILITE_RAW =
            registerItem("kanilite_raw");

    public static final RegistryObject<Item> KANILITE_INGOT =
            registerItem("kanilite_ingot");


    // ============================================================
    // Igniz
    // ============================================================

    public static final RegistryObject<Block> IGNIZ_ORE =
            registerBlock("igniz_ore", 4.0F);

    public static final RegistryObject<Block> IGNIZ_BLOCK =
            registerBlock("igniz_block", 13.0F);

    public static final RegistryObject<Item> IGNIZ_RAW =
            registerItem("igniz_raw");

    public static final RegistryObject<Item> IGNIZ_INGOT =
            registerItem("igniz_ingot");


    // ============================================================
    // Chirite
    // ============================================================

    public static final RegistryObject<Block> CHIRITE_ORE =
            registerBlock("chirite_ore", 4.0F);

    public static final RegistryObject<Block> CHIRITE_BLOCK =
            registerBlock("chirite_block", 16.0F);

    public static final RegistryObject<Item> CHIRITE_RAW =
            registerItem("chirite_raw");

    public static final RegistryObject<Item> CHIRITE_INGOT =
            registerItem("chirite_ingot");


    // ============================================================
    // Momongaite
    // ============================================================

    public static final RegistryObject<Block> MOMONGAITE_ORE =
            registerBlock("momongaite_ore", 3.0F);

    public static final RegistryObject<Block> MOMONGAITE_BLOCK =
            registerBlock("momongaite_block", 11.0F);

    public static final RegistryObject<Item> MOMONGAITE_RAW =
            registerItem("momongaite_raw");

    public static final RegistryObject<Item> MOMONGAITE_INGOT =
            registerItem("momongaite_ingot");


    // ============================================================
    // Other metals
    // ============================================================

    public static final RegistryObject<Block> HERDYEEN_BLOCK =
            registerBlock("herdyeen_block", 18.0F);

    public static final RegistryObject<Item> HERDYEEN_INGOT =
            registerItem("herdyeen_ingot");


    public static final RegistryObject<Block> HIROSWARI_BLOCK =
            registerBlock("hiroswari_block", 3.0F);

    public static final RegistryObject<Item> HIROSWARI_INGOT =
            registerItem("hiroswari_ingot");


    public static final RegistryObject<Block> MARULITE_BLOCK =
            registerBlock("marulite_block", 115.0F);

    public static final RegistryObject<Item> MARULITE_INGOT =
            registerItem("marulite_ingot");


    public static final RegistryObject<Block> PROXIA_BLOCK =
            registerBlock("proxia_block", 4.0F);

    public static final RegistryObject<Item> PROXIA_INGOT =
            registerItem("proxia_ingot");


    public static final RegistryObject<Block> OUSWARI_BLOCK =
            registerBlock("ouswari_block", 96.0F);

    public static final RegistryObject<Item> OUSWARI_INGOT =
            registerItem("ouswari_ingot");


    public static final RegistryObject<Block> AUROSTONE_BLOCK =
            registerBlock("aurostone_block", 25.0F);

    public static final RegistryObject<Item> AUROSTONE_INGOT =
            registerItem("aurostone_ingot");


    public static final RegistryObject<Block> DEEPSTEEL_BLOCK =
            registerBlock("deepsteel_block", 58.0F);

    public static final RegistryObject<Item> DEEPSTEEL_INGOT =
            registerItem("deepsteel_ingot");

    public static final RegistryObject<Item> DEEPCHUNK =
            registerItem("deepchunk");


    public static final RegistryObject<Block> SEIREN_BLOCK =
            registerBlock("seiren_block", 162.0F);

    public static final RegistryObject<Item> SEIREN_INGOT =
            registerItem("seiren_ingot");


    public static final RegistryObject<Block> CHIISTEEL_BLOCK =
            registerBlock("chiisteel_block", 35.0F);

    public static final RegistryObject<Item> CHIISTEEL_INGOT =
            registerItem("chiisteel_ingot");


    public static final RegistryObject<Block> IOXIUM_BLOCK =
            registerBlock("ioxium_block", 7.0F);

    public static final RegistryObject<Item> IOXIUM_INGOT =
            registerItem("ioxium_ingot");


    public static final RegistryObject<Block> DILONITE_BLOCK =
            registerBlock("dilonite_block", 9.0F);

    public static final RegistryObject<Item> DILONITE_INGOT =
            registerItem("dilonite_ingot");


    public static final RegistryObject<Block> TIBERIUM_BLOCK =
            registerBlock("tiberium_block", 18.0F);

    public static final RegistryObject<Item> TIBERIUM_INGOT =
            registerItem("tiberium_ingot");


    public static final RegistryObject<Block> OSTLUM_BLOCK =
            registerBlock("ostlum_block", 34.0F);

    public static final RegistryObject<Item> OSTLUM_INGOT =
            registerItem("ostlum_ingot");


    // ============================================================
    // Helper methods (1.16.5 Adapted)
    // ============================================================

    private static RegistryObject<Block> registerBlock(String name, float strength) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> new Block(
                AbstractBlock.Properties.create(Material.ROCK)
                        .hardnessAndResistance(strength)
                        .harvestTool(net.minecraftforge.common.ToolType.PICKAXE)
                        .setRequiresTool()
        ));

        // BlockItem 登録時に Supplier（() -> block.get()）を使うことで
        // NullPointerException（クラッシュ）を防ぎます
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));

        return block;
    }

    private static RegistryObject<Item> registerItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }


    // ============================================================
    // Register
    // ============================================================

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
