package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class PoisonousBindModifier extends Modifier {

    public PoisonousBindModifier() {
        super(0x00FF33); // 毒: ポイズングリーン
    }

    /**
     * 近接攻撃がヒットした後に発動 (1.16.5 TCon 3.x 仕様)
     */
    @Override
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();

        // サーバー側かつ攻撃対象が存在する場合のみ処理
        if (target != null && !target.getCommandSenderWorld().isClientSide) {

            int fixedLevel = 3; // レベル固定
            int durationTicks = 20 * 3 * fixedLevel;  // 180 ticks (9秒)
            int amplifier = Math.max(0, fixedLevel - 1);  // 2（毒 III）

            // 1.16.5 では Effects.POISON を使用
            EffectInstance poison = new EffectInstance(Effects.POISON, durationTicks, amplifier);
            target.addEffect(poison);
        }

        return 0; // 1.16.5 では int を返します
    }
}
