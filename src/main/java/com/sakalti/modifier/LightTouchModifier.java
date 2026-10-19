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

public class LightTouchModifier extends Modifier implements MeleeHitModifierHook {

    public LightTouchModifier() {
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

        // サーバー側かつ対象が存在する場合のみ処理
        if (target != null && !target.level().isClientSide) {

            int fixedLevel = 1; // レベル固定
            int durationTicks = 180 * fixedLevel; // 180 ticks (9秒)
            int amplifier = Math.max(0, fixedLevel - 1); // 0 (Glowing I)

            // 1.16.5 では MobEffects.GLOWING を使用
            MobEffectInstance glowing = new MobEffectInstance(MobEffects.GLOWING, durationTicks, amplifier);
            target.addEffect(glowing);
        }
    }
}
