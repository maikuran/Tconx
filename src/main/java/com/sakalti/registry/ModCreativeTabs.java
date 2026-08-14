package com.sakalti.sakalti.registry;

import com.sakalti.sakalti.registry.ModMirzo;
import com.sakalti.sakalti.registry.ModNuzwats;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {

    public static final CreativeModeTab ORIGINAL_WEAPONS = CreativeModeTab.builder()
            .title(Component.literal("Original Weapons"))
            .icon(() -> new ItemStack(ModMirzo.DIAMOND_MIRZO.get()))
            .displayItems((displayParams, output) -> {
                // Mirzo 6種類
                output.accept(ModMirzo.WOODEN_MIRZO.get());
                output.accept(ModMirzo.STONE_MIRZO.get());
                output.accept(ModMirzo.IRON_MIRZO.get());
                output.accept(ModMirzo.GOLDEN_MIRZO.get());
                output.accept(ModMirzo.DIAMOND_MIRZO.get());
                output.accept(ModMirzo.NETHERITE_MIRZO.get());

                // Nuzwat 6種類
                output.accept(ModNuzwats.WOODEN_NUZWAT.get());
                output.accept(ModNuzwats.STONE_NUZWAT.get());
                output.accept(ModNuzwats.IRON_NUZWAT.get());
                output.accept(ModNuzwats.GOLDEN_NUZWAT.get());
                output.accept(ModNuzwats.DIAMOND_NUZWAT.get());
                output.accept(ModNuzwats.NETHERITE_NUZWAT.get());
            })
            .build();
}
