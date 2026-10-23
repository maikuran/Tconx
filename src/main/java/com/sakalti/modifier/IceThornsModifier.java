
package com.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class IceThornsModifier extends Modifier {

    private static final int FIXED_LEVEL = 3;
    private static final int DURATION_TICKS = 20 * 3 * FIXED_LEVEL;
    private static final int AMPLIFIER = FIXED_LEVEL - 1;

    @Override
    public void onAttacked(
            ToolStack tool,
            int level,
            LivingEntity entity,
            DamageSource source,
            float amount,
            boolean isDirect,
            boolean isBlocked
    ) {
        if (entity == null || source == null) return;

        LivingEntity attacker = (LivingEntity) source.getEntity();

        if (attacker == null || attacker == entity) return;

        attacker.addEffect(
                new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        DURATION_TICKS,
                        AMPLIFIER
                )
        );
    }
}
