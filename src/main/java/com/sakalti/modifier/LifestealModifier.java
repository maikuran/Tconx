package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class LifestealModifier extends Modifier {

    public LifestealModifier() {
        super(); // 1.16.5 では引数なしのコンストラクタ
    }

    /**
     * 近接攻撃がヒットした後に発動 (1.16.5仕様)
     */
    
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();

        // サーバー側かつ攻撃者が存在する場合のみ処理
        if (attacker != null && !attacker.level.isClientSide() && level > 0) {

            float maxHealth = attacker.getMaxHealth();
            
            // 回復量 = 最大体力の8% × レベル
            float healAmount = maxHealth * 0.08f * level;

            attacker.heal(healAmount);
        }

        return 0; // 1.16.5 では int を返します
    }
}
