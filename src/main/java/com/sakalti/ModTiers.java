package com.sakalti;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

/** Harvest tiers preserving the old 1.16.5 levels 5, 6, and 7. */
public final class ModTiers {
    public static final Tier SUPER = TierSortingRegistry.registerTier(
            new ForgeTier(5, 0, 0.0F, 0.0F, 0, ModTags.Blocks.NEEDS_SUPER_TOOL,
                    () -> Ingredient.of(ModMetals.IGNIZ_INGOT.get())),
            new ResourceLocation(ModMetals.MODID, "super"), List.of(Tiers.NETHERITE), List.of());

    public static final Tier SUPER_2 = TierSortingRegistry.registerTier(
            new ForgeTier(6, 0, 0.0F, 0.0F, 0, ModTags.Blocks.NEEDS_SUPER_2_TOOL,
                    () -> Ingredient.of(ModMetals.IGNIZ_INGOT.get())),
            new ResourceLocation(ModMetals.MODID, "super_2"), List.of(SUPER), List.of());

    public static final Tier SUPER_3 = TierSortingRegistry.registerTier(
            new ForgeTier(7, 0, 0.0F, 0.0F, 0, ModTags.Blocks.NEEDS_SUPER_3_TOOL,
                    () -> Ingredient.of(ModMetals.IGNIZ_INGOT.get())),
            new ResourceLocation(ModMetals.MODID, "super_3"), List.of(SUPER_2), List.of());

    private ModTiers() {}
}
