package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.Random;

public class UnstableModifier extends Modifier {

    public UnstableModifier() {
        super(); // 1.16.5 では引数なしのコンストラクタ
    }

    /**
     * 近接攻撃のダメージ計算＆耐久消費 (1.16.5仕様)
     */
    @Override
    public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity attacker = context.getAttacker();

        // ツールが破損している、または攻撃者が存在しない場合は変動なし
        if (tool.isBroken() || attacker == null || level <= 0) {
            return damage;
        }

        // 1.0〜4.4の範囲でランダム倍率を決定
        Random random = attacker.getRandom();
        float multiplier = 1.0f + random.nextFloat() * 3.4f;

        // 耐久消費量を倍率に基づいて決定
        int durabilityCost;
        if (multiplier < 2.0f) {
            durabilityCost = 0;
        } else if (multiplier < 3.0f) {
            durabilityCost = 1;
        } else if (multiplier < 4.0f) {
            durabilityCost = 2;
        } else if (multiplier < 4.4f) {
            durabilityCost = 3;
        } else {
            durabilityCost = 4;
        }

        // サーバー側でのみ追加の耐久ダメージを適用 (TCon標準ヘルパーを使用)
        if (!attacker.level.isClientSide() && durabilityCost > 0) {
            ToolDamageUtil.damage(tool, durabilityCost, attacker, context.getSlotType());
        }

        // 倍率を掛けた最終ダメージを返す
        return damage * multiplier;
    }
}
