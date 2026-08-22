package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class HeavyStoneModifier extends Modifier {

    public HeavyStoneModifier() {
        super(); // 1.16.5 では引数なしのコンストラクタ
    }

    /**
     * 近接攻撃がヒットした後に発動 (1.16.5仕様)
     */
    @Override
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();

        // サーバー側かつ攻撃者が存在する場合のみ処理
        if (attacker != null && !attacker.level.isClientSide()) {

            int durationTicks = 20 * 5; // 5秒持続

            // Slowness V (amplifier = 4)
            EffectInstance slowness = new EffectInstance(Effects.MOVEMENT_SLOWDOWN, durationTicks, 4);
            // Resistance III (amplifier = 2)
            EffectInstance resistance = new EffectInstance(Effects.DAMAGE_RESISTANCE, durationTicks, 2);

            attacker.addEffect(slowness);
            attacker.addEffect(resistance);
        }

        return 0; // 1.16.5 では int を返します
    }
}
