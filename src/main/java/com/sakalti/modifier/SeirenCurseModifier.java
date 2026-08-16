package com.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class SeirenCurseModifier extends Modifier {

    @Override
    public void afterEntityHit(
            ToolStack tool,
            int level,
            LivingEntity target,
            LivingEntity attacker,
            float damage,
            boolean isCritical
    ) {
        if (target == null) return;

        // 弱体化 III：10秒
        target.addEffect(new MobEffectInstance(
                MobEffects.WEAKNESS,
                200,
                2
        ));

        // 鈍化 VI：6秒
        target.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SLOWDOWN,
                120,
                5
        ));
    }
}
