package com.sakalti.registry;

import com.sakalti.items.NuzwatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModNuzwats {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "sakalti");

    public static final RegistryObject<Item> WOODEN_NUZWAT = ITEMS.register("wooden_nuzwat",
            () -> new NuzwatItem(Tiers.WOOD, -2.1f, 3.5f, new Item.Properties()));
    public static final RegistryObject<Item> STONE_NUZWAT = ITEMS.register("stone_nuzwat",
            () -> new NuzwatItem(Tiers.STONE, -2.0f, 4.2f, new Item.Properties()));
    public static final RegistryObject<Item> IRON_NUZWAT = ITEMS.register("iron_nuzwat",
            () -> new NuzwatItem(Tiers.IRON, -1.9f, 4.9f, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_NUZWAT = ITEMS.register("golden_nuzwat",
            () -> new NuzwatItem(Tiers.GOLD, -1.3f, 3.5f, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_NUZWAT = ITEMS.register("diamond_nuzwat",
            () -> new NuzwatItem(Tiers.DIAMOND, -1.8f, 5.6f, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_NUZWAT = ITEMS.register("netherite_nuzwat",
            () -> new NuzwatItem(Tiers.NETHERITE, -1.7f, 6.3f, new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
