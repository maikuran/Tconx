package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class SeirenCurseModifier extends Modifier {

    public SeirenCurseModifier() {
        super(); // 1.16.5 では引数なしのコンストラクタ
    }

    /**
     * 近接攻撃がヒットした後に発動 (1.16.5仕様)
     */
    @Override
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getTarget();

        // サーバー側かつ攻撃対象が存在する場合のみ処理
        if (target != null && !target.level.isClientSide()) {

            // 弱体化 III (amplifier = 2)：10秒 (200 ticks)
            target.addEffect(new EffectInstance(
                    Effects.WEAKNESS,
                    200,
                    2
            ));

            // 鈍化 VI (amplifier = 5)：6秒 (120 ticks)
            target.addEffect(new EffectInstance(
                    Effects.MOVEMENT_SLOWDOWN,
                    120,
                    5
            ));
        }

        return 0; // 1.16.5 では int を返します
    }
}
