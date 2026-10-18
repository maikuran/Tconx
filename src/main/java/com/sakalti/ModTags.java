package com.sakalti;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModTags {
    private ModTags() {}

    public static final class Blocks {
        public static final TagKey<Block> NEEDS_SUPER_TOOL = tag("needs_super_tool");
        public static final TagKey<Block> NEEDS_SUPER_2_TOOL = tag("needs_super_2_tool");
        public static final TagKey<Block> NEEDS_SUPER_3_TOOL = tag("needs_super_3_tool");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(ModMain.MODID, name));
        }
        private Blocks() {}
    }
}
