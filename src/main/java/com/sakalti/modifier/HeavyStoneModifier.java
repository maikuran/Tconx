package com.sakalti.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class HeavyStoneModifier extends Modifier {

    public void afterEntityHit(ToolStack tool, int level, LivingEntity target, LivingEntity attacker, float damage, boolean isCritical) {
        if (attacker == null) return;

        int durationTicks = 20 * 5; // 5秒持続
        // Slowness V (amplifier 4)
        MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, durationTicks, 4);
        // Resistance III (amplifier 2)
        MobEffectInstance resistance = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, durationTicks, 2);

        attacker.addEffect(slowness);
        attacker.addEffect(resistance);
    }
}
