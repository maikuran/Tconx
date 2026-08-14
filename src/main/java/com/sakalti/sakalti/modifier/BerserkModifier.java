package com.sakalti.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Random;

public class BerserkModifier extends Modifier {
    private static final Random RANDOM = new Random();

    /**
     * 攻撃後に呼び出す独自メソッド。
     * 33%で耐久2消費し、ダメージ3倍を返す。
     *
     * @param tool ツールスタック
     * @param level Modifierレベル
     * @param attacker 攻撃者エンティティ
     * @param baseDamage 元の与ダメージ
     * @return 修正後のダメージ
     */
    public float AfterEntityHit(ToolStack tool, int level, LivingEntity attacker, float baseDamage) {
        if (tool.isBroken() || attacker == null || level <= 0) return baseDamage;

        if (RANDOM.nextFloat() < 0.33f) {
            int currentDamage = tool.getDamage();

            // 壊れるのを防ぐため単純に最大値はInt.MAX_VALUEで代用
            if (currentDamage + 2 >= Integer.MAX_VALUE) {
                return baseDamage;
            }

            tool.setDamage(currentDamage + 2);

            return baseDamage * 3f;
        }

        return baseDamage;
    }
}
