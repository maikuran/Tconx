package com.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class FieldyModifier extends Modifier {

    public void afterEntityHit(ToolStack tool, int level, LivingEntity target, LivingEntity attacker, float damage, boolean isCritical) {
        if (attacker == null) return;

        int durationTicks = 20 * 1; // 1秒持続
        // Glowing (amplifier 2)
        MobEffectInstance glow = new MobEffectInstance(MobEffects.GLOWING, durationTicks, 4);
        // Resistance III (amplifier 4)
        MobEffectInstance bar = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, durationTicks, 4);

        attacker.addEffect(glow);
        attacker.addEffect(bar);
    }
}
