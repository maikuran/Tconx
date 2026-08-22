package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class LightTouchModifier extends Modifier {

    public LightTouchModifier() {
        super(); // 1.16.5 では引数なしのコンストラクタ
    }

    /**
     * 近接攻撃がヒットした後に発動 (1.16.5仕様)
     */
    @Override
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getTarget();

        // サーバー側かつ対象が存在する場合のみ処理
        if (target != null && !target.level.isClientSide()) {

            int fixedLevel = 1; // レベル固定
            int durationTicks = 180 * fixedLevel; // 180 ticks (9秒)
            int amplifier = Math.max(0, fixedLevel - 1); // 0 (Glowing I)

            // 1.16.5 では Effects.GLOWING を使用
            EffectInstance glowing = new EffectInstance(Effects.GLOWING, durationTicks, amplifier);
            target.addEffect(glowing);
        }

        return 0; // 1.16.5 では int を返します
    }
}
