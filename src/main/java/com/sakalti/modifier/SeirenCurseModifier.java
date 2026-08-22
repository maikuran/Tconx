package com.sakalti.modifier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class SeirenCurseModifier extends Modifier {

    public SeirenCurseModifier() {
        super(0x006699); // セイレーンの呪い: ディープブルー
    }

    /**
     * 近接攻撃がヒットした後に発動 (1.16.5 TCon 3.x 仕様)
     */
    
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        // context.getLivingTarget() を使用、または Entity から安全にキャスト
        LivingEntity target = context.getLivingTarget();

        // 1. 攻撃対象が LivingEntity であること
        // 2. クライアント側ではなくサーバー側での処理であること (getCommandSenderWorld().isClientSide)
        if (target != null && !target.getCommandSenderWorld().isClientSide()) {

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

        return 0;
    }
}
