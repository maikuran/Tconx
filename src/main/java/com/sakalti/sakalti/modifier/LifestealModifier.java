package com.sakalti.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class LifestealModifier extends Modifier {

    public void afterEntityHit(ToolStack tool, int level, LivingEntity target, LivingEntity attacker, float damage, boolean isCritical) {
        if (attacker == null || level <= 0) return;

        float maxHealth = attacker.getMaxHealth();
        
        // 回復量 = 最大体力の8% × レベル
        float healAmount = maxHealth * 0.08f * level;

        attacker.heal(healAmount);
    }
}
