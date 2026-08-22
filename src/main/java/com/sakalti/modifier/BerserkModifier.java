package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class BerserkModifier extends Modifier {

    public BerserkModifier() {
        super(0xCC0000);
    }

    public float getMeleeDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity attacker = context.getAttacker();

        if (tool.isBroken() || attacker == null || level <= 0) {
            return damage;
        }

        if (RANDOM.nextFloat() < 0.33f) {
            if (!attacker.getCommandSenderWorld().isClientSide) {
                // 第4引数を削除してツールとダメージ量、エンティティを渡す
                ToolDamageUtil.damage(tool, 2, attacker);
            }
            return damage * 3.0f;
        }

        return damage;
    }
}
