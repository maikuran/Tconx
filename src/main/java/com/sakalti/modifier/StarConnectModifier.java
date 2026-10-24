
package com.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class StarConnectModifier extends Modifier
        implements InventoryTickModifierHook {

    private static final int EFFECT_DURATION = 40;
    private static final int REGENERATION_AMPLIFIER = 3;

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(
                this,
                ModifierHooks.INVENTORY_TICK
        );
    }

    public void onInventoryTick(
            IToolStackView tool,
            ModifierEntry modifier,
            Level world,
            LivingEntity holder,
            int itemSlot,
            boolean isSelected,
            boolean isCorrectSlot,
            ItemStack stack
    ) {
        if (world.isClientSide) return;
        if (!isCorrectSlot) return;

        // 夜間かどうか
        boolean isNight = world.isNight();

        // 雨が降っているか
        boolean isRaining = world.isRaining();

        if (isNight && !isRaining) {
            holder.addEffect(
                    new MobEffectInstance(
                            MobEffects.REGENERATION,
                            EFFECT_DURATION,
                            REGENERATION_AMPLIFIER,
                            false,
                            false,
                            true
                    )
            );
        }
    }
}
