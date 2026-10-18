package com.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class CoralianModifier extends Modifier implements InventoryTickModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(ModifierHooks.INVENTORY_TICK, this);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        int level = modifier.getLevel();
        if (world.isClientSide || !isSelected) return;
        if (!tool.isBroken()) {
            MobEffectInstance waterBreathing = new MobEffectInstance(
                MobEffects.WATER_BREATHING,
                220,
                0,
                true,
                false
            );

            entity.addEffect(waterBreathing);
        }
    }
}
