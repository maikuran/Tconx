package com.sakalti.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Random;

public class UnstableModifier extends Modifier {
    private static final Random RANDOM = new Random();

    /**
     * 攻撃後に呼び出す不安定な効果。
     * ダメージが毎回1.0〜4.4倍に変動し、倍率に応じて耐久を消費する。
     *
     * @param tool ツールスタック
     * @param level Modifierレベル
     * @param attacker 攻撃者
     * @param baseDamage 元のダメージ
     * @return 修正後のダメージ
     */
    public float AfterEntityHit(ToolStack tool, int level, LivingEntity attacker, float baseDamage) {
        if (tool.isBroken() || attacker == null || level <= 0) return baseDamage;

        // 1.0〜4.4の範囲でランダム倍率を決定
        float multiplier = 1.0f + RANDOM.nextFloat() * 3.4f;

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

        // 壊れないようにチェック
        int currentDamage = tool.getDamage();
        if (currentDamage + durabilityCost < Integer.MAX_VALUE) {
            tool.setDamage(currentDamage + durabilityCost);
        }

        return baseDamage * multiplier;
    }
}
