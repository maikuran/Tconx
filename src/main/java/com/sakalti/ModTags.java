package com.sakalti.sakalti;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> NEEDS_SUPER_TOOL = tag("needs_super_tool");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation(ModMetals.MODID, name));
        }
    }
}
