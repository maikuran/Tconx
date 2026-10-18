package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class WeakBurnModifier extends Modifier implements MeleeHitModifierHook {

    public WeakBurnModifier() {
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
        LivingEntity target = context.getTarget();

        // サーバー側かつ攻撃対象が存在する場合のみ処理
        if (target != null && !target.level().isClientSide) {

            int fixedLevel = 1; // レベル固定
            int durationTicks = 40 * fixedLevel;  // 40 ticks (2秒)
            int amplifier = Math.max(0, fixedLevel - 1);  // 0（弱体化 I）

            // 1.16.5 では MobEffects.WEAKNESS を使用
            MobEffectInstance weakness = new MobEffectInstance(MobEffects.WEAKNESS, durationTicks, amplifier);
            target.addEffect(weakness);
        }
    }
}
