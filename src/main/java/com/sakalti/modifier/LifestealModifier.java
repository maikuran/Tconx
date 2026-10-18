package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class LifestealModifier extends Modifier implements MeleeHitModifierHook {

    public LifestealModifier() {
        super();
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(ModifierHooks.MELEE_HIT, this);
    }

    /**
     * 近接攻撃がヒットした後に発動 (1.16.5仕様)
     */
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        int level = modifier.getLevel();
        LivingEntity attacker = context.getAttacker();

        // サーバー側かつ攻撃者が存在する場合のみ処理
        if (attacker != null && !attacker.level().isClientSide && level > 0) {

            float maxHealth = attacker.getMaxHealth();
            
            // 回復量 = 最大体力の8% × レベル
            float healAmount = maxHealth * 0.08f * level;

            attacker.heal(healAmount);
        }
    }
}
