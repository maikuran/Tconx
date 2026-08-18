package com.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class CoralianModifier extends Modifier {

    public void tick(ToolStack tool, int level, LivingEntity entity) {
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
