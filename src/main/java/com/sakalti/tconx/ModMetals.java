package com.sakalti.tconx;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModMetals {

    public static final String MODID = "tconx";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    private static final List<String> autoRegisterItemModels = new ArrayList<>();

    // ---- ブロック & アイテム 登録 ----

    public static final RegistryObject<Block> HACHILITE_ORE = registerBlock("hachilite_ore", 3f);
    public static final RegistryObject<Block> HACHILITE_BLOCK = registerBlock("hachilite_block", 7f);
    public static final RegistryObject<Item> HACHILITE_RAW = registerSimpleItem("hachilite_raw");
    public static final RegistryObject<Item> HACHILITE_INGOT = registerSimpleItem("hachilite_ingot");

    public static final RegistryObject<Block> KANILITE_ORE = registerBlock("kanilite_ore", 4f);
    public static final RegistryObject<Block> KANILITE_BLOCK = registerBlock("kanilite_block", 10f);
    public static final RegistryObject<Item> KANILITE_RAW = registerSimpleItem("kanilite_raw");
    public static final RegistryObject<Item> KANILITE_INGOT = registerSimpleItem("kanilite_ingot");

    public static final RegistryObject<Block> IGNIZ_ORE = registerBlock("igniz_ore", 4f);
    public static final RegistryObject<Block> IGNIZ_BLOCK = registerBlock("igniz_block", 13f);
    public static final RegistryObject<Item> IGNIZ_RAW = registerSimpleItem("igniz_raw");
    public static final RegistryObject<Item> IGNIZ_INGOT = registerSimpleItem("igniz_ingot");
    
    public static final RegistryObject<Block> CHIRITE_ORE = registerBlock("chirite_ore", 4f);
    public static final RegistryObject<Block> CHIRITE_BLOCK = registerBlock("chirite_block", 16f);
    public static final RegistryObject<Item> CHIRITE_RAW = registerSimpleItem("chirite_raw");
    public static final RegistryObject<Item> CHIRITE_INGOT = registerSimpleItem("chirite_ingot");
    
    public static final RegistryObject<Block> MOMONGAITE_ORE = registerBlock("momongaite_ore", 3f);
    public static final RegistryObject<Block> MOMONGAITE_BLOCK = registerBlock("momongaite_block", 11f);
    public static final RegistryObject<Item> MOMONGAITE_RAW = registerSimpleItem("momongaite_raw");
    public static final RegistryObject<Item> MOMONGAITE_INGOT = registerSimpleItem("momongaite_ingot");

    public static final RegistryObject<Block> HERDYEEN_BLOCK = registerBlock("herdyeen_block", 18f);
    public static final RegistryObject<Item> HERDYEEN_INGOT = registerSimpleItem("herdyeen_ingot");
    public static final RegistryObject<Block> HIROSWARI_BLOCK = registerBlock("hiroswari_block", 3f);
    public static final RegistryObject<Item> HIROSWARI_INGOT = registerSimpleItem("hiroswari_ingot");

    public static final RegistryObject<Block> MARULITE_BLOCK = registerBlock("marulite_block", 115f);
    public static final RegistryObject<Item> MARULITE_INGOT = registerSimpleItem("marulite_ingot");

    public static final RegistryObject<Block> PROXIA_BLOCK = registerBlock("proxia_block", 4f);
    public static final RegistryObject<Item> PROXIA_INGOT = registerSimpleItem("proxia_ingot");

    public static final RegistryObject<Block> OUSWARI_BLOCK = registerBlock("ouswari_block", 96f);
    public static final RegistryObject<Item> OUSWARI_INGOT = registerSimpleItem("ouswari_ingot");

    public static final RegistryObject<Block> AUROSTONE_BLOCK = registerBlock("aurostone_block", 25f);
    public static final RegistryObject<Item> AUROSTONE_INGOT = registerSimpleItem("aurostone_ingot");

    public static final RegistryObject<Block> DEEPSTEEL_BLOCK = registerBlock("deepsteel_block", 58f);
    public static final RegistryObject<Item> DEEPSTEEL_INGOT = registerSimpleItem("deepsteel_ingot");
    public static final RegistryObject<Item> DEEPCHUNK = registerSimpleItem("deepchunk");

    public static final RegistryObject<Block> CHIISTEEL_BLOCK = registerBlock("chiisteel_block", 35f);
    public static final RegistryObject<Item> CHIISTEEL_INGOT = registerSimpleItem("chiisteel_ingot");
    
    public static final RegistryObject<Block> IOXIUM_BLOCK = registerBlock("ioxium_block", 7f);
    public static final RegistryObject<Item> IOXIUM_INGOT = registerSimpleItem("ioxium_ingot");

    public static final RegistryObject<Block> DILONITE_BLOCK = registerBlock("dilonite_block", 9f);
    public static final RegistryObject<Item> DILONITE_INGOT = registerSimpleItem("dilonite_ingot");

    public static final RegistryObject<Block> TIBERITE_BLOCK = registerBlock("tiberite_block", 18f);
    public static final RegistryObject<Item> TIBERITE_INGOT = registerSimpleItem("tiberite_ingot");
    
    public static final RegistryObject<Block> OSTLUM_BLOCK = registerBlock("ostlum_block", 34f);   
    public static final RegistryObject<Item> OSTLUM_INGOT = registerSimpleItem("ostlum_ingot");

    // ---- 登録用 共通関数 ----

    private static RegistryObject<Block> registerBlock(String name, float strength) {
        RegistryObject<Block> block = BLOCKS.register(name,
                () -> new Block(BlockBehaviour.Properties.of()
                        .strength(strength)
                        .requiresCorrectToolForDrops()));
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static RegistryObject<Item> registerSimpleItem(String name) {
        autoRegisterItemModels.add(name);
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    // ---- 初期化 ----
    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientOnly::init);
    }

    // ---- クライアント専用 ----
    @OnlyIn(Dist.CLIENT)
    private static class ClientOnly {
        static void init() {
            MinecraftForge.EVENT_BUS.addListener(ClientOnly::onRegisterModels);
        }

        private static void onRegisterModels(ModelEvent.RegisterAdditional event) {
            for (String name : autoRegisterItemModels) {
                event.register(ResourceLocation.fromNamespaceAndPath(MODID, "item/" + name));
            }
        }
    }
}
