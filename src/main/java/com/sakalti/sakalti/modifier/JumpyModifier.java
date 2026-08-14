package com.sakalti.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class JumpyModifier extends Modifier {

    // 常時ジャンプブーストを付与する（毎tick呼び出し）
    public void tick(ToolStack tool, int level, LivingEntity entity) {
        if (tool.isBroken()) {
            return;
        }
        // Jump Boost 効果を付与。amplifier は 0 が1段階目なので level-1。
        entity.addEffect(new MobEffectInstance(
            MobEffects.JUMP,
            220,
            Math.max(0, level - 1),
            true,   // ambient
            false   // showParticles
        ));
    }
}
