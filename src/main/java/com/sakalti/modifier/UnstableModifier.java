package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.Random;

public class UnstableModifier extends Modifier {

    public UnstableModifier() {
        super(0xCC00FF);
    }

    @Override
    public float getMeleeDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity attacker = context.getAttacker();

        if (tool.isBroken() || attacker == null || level <= 0) {
            return damage;
        }

        Random random = attacker.getRandom();
        float multiplier = 1.0f + random.nextFloat() * 3.4f;

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

        // サーバー側でのみ追加の耐久ダメージを適用 (tool.getStack() を使用)
        if (!attacker.getCommandSenderWorld().isClientSide && durabilityCost > 0) {
            ToolDamageUtil.damage(tool, durabilityCost, attacker, tool.getStack());
        }

        return damage * multiplier;
    }
}
