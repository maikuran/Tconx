package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.Random;

public class BerserkModifier extends Modifier {

    private static final Random RANDOM = new Random();

    public BerserkModifier(int color) {
        super(color);
    }

    /**
     * 近接攻撃時にダメージの倍率や加算を計算するフック (1.16.5仕様)
     * 
     * @param tool 攻撃に使用したツール
     * @param level このモディファイアのレベル
     * @param context 攻撃時のコンテキスト（攻撃者、対象、クリティカル有無など）
     * @param baseDamage 基礎ダメージ
     * @param damage 現在計算中の与ダメージ
     * @return 修正後のダメージ
     */
    
    public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity attacker = context.getAttacker();

        // ツールが壊れている、または攻撃者が存在しない場合は処理しない
        if (tool.isBroken() || attacker == null || level <= 0) {
            return damage;
        }

        // 33%の確率で発動
        if (RANDOM.nextFloat() < 0.33f) {
            
            // サーバー側の場合のみ追加で耐久値を 2 消費（壊れる直前の判定などもTCon側で内部処理されます）
            if (!attacker.level.isClientSide) {
                tool.damageItem(2, attacker, context.getSlotType());
            }

            // 与ダメージを3倍にする
            return damage * 3.0f;
        }

        return damage;
    }
}
