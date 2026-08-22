package com.sakalti;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public final class ModTags {

    private ModTags() {
    }

    public static final class Blocks {

        public static final ITag.INamed<Block> NEEDS_SUPER_TOOL =
                tag("needs_super_tool");

        private static ITag.INamed<Block> tag(String name) {
            return BlockTags.makeWrapperTag(
                    new ResourceLocation(
                            "sakalti",
                            name
                    ).toString()
            );
        }

        private Blocks() {
        }
    }
}
