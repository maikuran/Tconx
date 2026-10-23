
package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class ReduceModifier extends Modifier {

    private static final float DAMAGE_REDUCTION = 0.15f;

    @Override
    public float onAttacked(
            ToolStack tool,
            int level,
            LivingEntity entity,
            DamageSource source,
            float amount,
            boolean isDirect,
            boolean isBlocked
    ) {
        return amount * (1.0f - DAMAGE_REDUCTION);
    }
}
