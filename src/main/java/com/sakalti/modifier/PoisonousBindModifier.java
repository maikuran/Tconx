package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class PoisonousBindModifier extends Modifier {

    public PoisonousBindModifier(int color) {
        super(color);
    }

    /**
     * 近接攻撃がヒットした後に発動 (1.16.5仕様)
     */
    @Override
    public void afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getTarget();

        // サーバー側かつ攻撃対象が存在する場合のみ処理
        if (target != null && !target.level.isClientSide) {

            int fixedLevel = 3; // レベル固定
            int durationTicks = 20 * 3 * fixedLevel;  // 180 ticks (9秒)
            int amplifier = Math.max(0, fixedLevel - 1);  // 2（毒 III）

            // 1.16.5 では Effects.POISON を使用
            EffectInstance poison = new EffectInstance(Effects.POISON, durationTicks, amplifier);
            target.addEffect(poison);
        }
    }
}
