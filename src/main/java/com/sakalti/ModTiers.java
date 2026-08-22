package com.sakalti;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum ModTiers implements IItemTier {
    
    SUPER(5, 0, 0.0F, 0.0F, 0, () -> Ingredient.of(ModMetals.IGNIZ_INGOT.get()));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamageBonus;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    ModTiers(int harvestLevel, int maxUses, float efficiency, float attackDamageBonus, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    
    public int getUses() {
        return this.maxUses;
    }

    
    public float getSpeed() {
        return this.efficiency;
    }

    
    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    
    public int getLevel() {
        return this.harvestLevel;
    }

    
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
