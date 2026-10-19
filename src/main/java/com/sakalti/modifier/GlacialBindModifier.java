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

public class GlacialBindModifier extends Modifier implements MeleeHitModifierHook {

    public GlacialBindModifier() {
        super();
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.MELEE_HIT);
    }

    /**
     * 近接攻撃がヒットした後に発動 (1.16.5仕様)
     */
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        int level = modifier.getLevel();
        if (!(context.getTarget() instanceof LivingEntity target)) return;

        // サーバー側かつ攻撃対象が存在する場合のみ処理
        if (target != null && !target.level().isClientSide) {

            int fixedLevel = 3; // レベル固定
            int durationTicks = 20 * 3 * fixedLevel;  // 180 ticks (9秒)
            int amplifier = Math.max(0, fixedLevel - 1);  // 2（移動低下 III）

            // 1.16.5 では MobEffects.MOVEMENT_SLOWDOWN を使用
            MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, durationTicks, amplifier);
            target.addEffect(slowness);
        }
    }
}
