package com.sakalti;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public final class ModTags {

    private ModTags() {
    }

    public static final class Blocks {

        public static final ITag.INamedTag<Block> NEEDS_SUPER_TOOL =
                BlockTags.bind(
                        new ResourceLocation(
                                "sakalti",
                                "needs_super_tool"
                        ).toString()
                );

        private Blocks() {
        }
    }
}
