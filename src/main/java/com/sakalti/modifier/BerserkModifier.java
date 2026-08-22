package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class BerserkModifier extends Modifier {

    public BerserkModifier() {
        super(0xCC0000); // 狂戦士: ブラッドレッド
    }

    /**
     * 近接攻撃時の与ダメージ計算フック (1.16.5 TCon 3.x 仕様)
     */
    
    public float getMeleeDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity attacker = context.getAttacker();

        // ツールが壊れている、または攻撃者が存在しない場合は処理しない
        if (tool.isBroken() || attacker == null || level <= 0) {
            return damage;
        }

        // 33% の確率で発動
        if (RANDOM.nextFloat() < 0.33f) {
            
            // サーバー側の場合のみ追加で耐久値を 2 消費
            if (!attacker.getCommandSenderWorld().isClientSide) {
                // 1.16.5 TCon 3.x での正しい耐久ダメージ処理 (第4引数には ToolStack 自体または手持ちの ItemStack を渡す)
                ToolDamageUtil.damage(tool, 2, attacker, context.getItemInHand());
            }

            // 与ダメージを3倍にする
            return damage * 3.0f;
        }

        return damage;
    }
}
