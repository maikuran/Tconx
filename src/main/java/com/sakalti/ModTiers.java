package com.sakalti.sakalti;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModTiers {

    public static final Tier SUPER = TierSortingRegistry.registerTier(
            new ForgeTier(5, 0, 0.0f, 0.0f, 0,
                    ModTags.Blocks.NEEDS_SUPER_TOOL, () -> Ingredient.of(ModMetals.IGNIZ_INGOT.get())),
            new net.minecraft.resources.ResourceLocation(ModMetals.MODID, "igniz"),
            List.of(net.minecraft.world.item.Tiers.NETHERITE),
            List.of()
    );
}
