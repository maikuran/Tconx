package com.sakalti.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class LightTouchModifier extends Modifier {

    public void afterEntityHit(ToolStack tool, int level, LivingEntity target, LivingEntity attacker, float damage, boolean isCritical) {
        if (target == null) return;

        int fixedLevel = 1; // レベル固定

        int durationTicks = 180 * 1 * fixedLevel;  // 3秒 × レベル3
        int amplifier = Math.max(0, fixedLevel - 1);  // 2（Potionレベル3相当）

        MobEffectInstance glowing = new MobEffectInstance(MobEffects.GLOWING, durationTicks, amplifier);
        target.addEffect(glowing);
    }
}
