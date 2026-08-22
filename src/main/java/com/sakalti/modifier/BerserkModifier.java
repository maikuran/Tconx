package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class BerserkModifier extends Modifier {

    public BerserkModifier() {
        super(); // 1.16.5 では引数なしのコンストラクタを使用します
    }

    /**
     * 近接攻撃時の与ダメージ計算フック
     */
    @Override
    public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity attacker = context.getAttacker();

        // ツールが壊れている、または攻撃者が存在しない場合は処理しない
        if (tool.isBroken() || attacker == null || level <= 0) {
            return damage;
        }

        // RANDOM は Modifier クラス親定義の `RANDOM` (または RAND) をそのまま使えます
        // 33% の確率で発動
        if (RANDOM.nextFloat() < 0.33f) {
            
            // サーバー側の場合のみ追加で耐久値を 2 消費
            if (!attacker.level.isClientSide()) {
                // 1.16.5 でのツール耐久消費（attacker は LivingEntity）
                tool.damageItem(2, attacker, context.getSlotType());
            }

            // 与ダメージを3倍にする
            return damage * 3.0f;
        }

        return damage;
    }
}
