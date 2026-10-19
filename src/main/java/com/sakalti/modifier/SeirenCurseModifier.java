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

public class SeirenCurseModifier extends Modifier implements MeleeHitModifierHook {

    public SeirenCurseModifier() {
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

            // 弱体化 III (amplifier = 2)：10秒 (200 ticks)
            target.addEffect(new MobEffectInstance(
                    MobEffects.WEAKNESS,
                    200,
                    2
            ));

            // 鈍化 VI (amplifier = 5)：6秒 (120 ticks)
            target.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    120,
                    5
            ));
        }
    }
}
