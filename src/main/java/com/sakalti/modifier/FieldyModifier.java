package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class FieldyModifier extends Modifier {

    public FieldyModifier(int color) {
        super(color);
    }

    /**
     * 近接攻撃がヒットした後に発動 (1.16.5仕様)
     */
    @Override
    public void afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();

        // サーバー側かつ攻撃者が存在する場合のみ処理
        if (attacker != null && !attacker.level.isClientSide) {

            int durationTicks = 20 * 1; // 1秒持続

            // Glowing V (amplifier = 4)
            EffectInstance glow = new EffectInstance(Effects.GLOWING, durationTicks, 4);
            // Resistance V (amplifier = 4)
            EffectInstance bar = new EffectInstance(Effects.DAMAGE_RESISTANCE, durationTicks, 4);

            attacker.addEffect(glow);
            attacker.addEffect(bar);
        }
    }
}
