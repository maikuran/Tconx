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

public class HeavyStoneModifier extends Modifier implements MeleeHitModifierHook {

    public HeavyStoneModifier() {
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
        if (attacker != null && !attacker.level().isClientSide) {

            int durationTicks = 20 * 5; // 5秒持続

            // Slowness V (amplifier = 4)
            MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, durationTicks, 4);
            // Resistance III (amplifier = 2)
            MobEffectInstance resistance = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, durationTicks, 2);

            attacker.addEffect(slowness);
            attacker.addEffect(resistance);
        }
    }
}
