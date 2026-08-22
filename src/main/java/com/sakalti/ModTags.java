package com.sakalti;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class ModTags {

    public static class Blocks {
        // 1.16.5 では TagKey ではなく ITag.INamed を使用します
        public static final ITag.INamed<Block> NEEDS_SUPER_TOOL = tag("needs_super_tool");

        private static ITag.INamed<Block> tag(String name) {
            // BlockTags.makeWrapperTag を使って MOD 独自のタグを作成します
            return BlockTags.makeWrapperTag(new ResourceLocation(ModMetals.MODID, name).toString());
        }
    }
}
