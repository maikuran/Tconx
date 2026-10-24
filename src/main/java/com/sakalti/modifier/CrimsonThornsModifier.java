
package com.sakalti.modifier;

import java.util.List;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class CrimsonThornsModifier extends Modifier
        implements ModifyDamageModifierHook, OnAttackedModifierHook {

    private static final float DAMAGE_REDUCTION = 0.65f;
    private static final float REFLECT_DAMAGE = 0.50f;

    @Override
    public List<ModuleHook<?>> getDefaultHooks() {
        return List.of(
                ModifierHooks.MODIFY_DAMAGE,
                ModifierHooks.ON_ATTACKED
        );
    }

    // 被ダメージ65%軽減
    @Override
    public float modifyDamageTaken(
            IToolStackView tool,
            ModifierEntry modifier,
            EquipmentContext context,
            EquipmentSlot slotType,
            DamageSource source,
            float amount,
            boolean isDirectDamage
    ) {
        return amount * (1.0f - DAMAGE_REDUCTION);
    }

    // 攻撃者に50%のダメージを反射
    @Override
    public void onAttacked(
            IToolStackView tool,
            ModifierEntry modifier,
            EquipmentContext context,
            EquipmentSlot slotType,
            DamageSource source,
            float amount,
            boolean isDirectDamage
    ) {
        if (source == null || amount <= 0) return;

        LivingEntity defender = context.getEntity();

        if (defender.level().isClientSide) return;

        if (!(source.getEntity() instanceof LivingEntity attacker)) return;

        if (attacker == defender || !attacker.isAlive()) return;

        // 反射ダメージによる無限ループを防止
        if (source.is(net.minecraft.world.damagesource.DamageTypes.THORNS)) return;

        float reflectedDamage = amount * REFLECT_DAMAGE;

        attacker.hurt(
                attacker.damageSources().thorns(defender),
                reflectedDamage
        );
    }
}
