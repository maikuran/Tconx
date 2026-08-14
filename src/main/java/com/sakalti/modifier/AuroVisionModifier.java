package com.sakalti.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class AuroVisionModifier extends Modifier {

    public void afterEntityHit(ToolStack tool, int level, LivingEntity target, LivingEntity attacker, float damage, boolean isCritical) {
        if (attacker == null) return;

        int durationTicks = 20; // 1秒

        // Glowing V（amplifier = 4）
        MobEffectInstance glow = new MobEffectInstance(MobEffects.GLOWING, durationTicks, 4);
        // Resistance V
        MobEffectInstance resistance = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, durationTicks, 4);

        attacker.addEffect(glow);
        attacker.addEffect(resistance);
    }

    // 常時効果を与える（毎tick呼び出される）
    public void tick(ToolStack tool, int level, LivingEntity entity) {
        if (!tool.isBroken()) {
            MobEffectInstance nightVision = new MobEffectInstance(
                MobEffects.NIGHT_VISION,
                220,    // 長めのtickでチラつきを防止
                0,
                true,   // ambient（自然に見せる）
                false   // showParticles（パーティクル非表示）
            );
            entity.addEffect(nightVision);
        }
    }
}
