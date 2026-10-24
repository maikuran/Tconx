
package com.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

public class CryofreezeModifier extends Modifier
        implements InventoryTickModifierHook, OnAttackedModifierHook {

    private static final float HEALTH_THRESHOLD = 0.50F;

    private static final int EFFECT_DURATION = 40;

    private static final int SLOWNESS_DURATION = 20 * 15;
    private static final int REGENERATION_DURATION = 20 * 3;

    private static final int SLOWNESS_AMPLIFIER = 1;
    private static final int REGENERATION_AMPLIFIER = 1;

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(
                this,
                ModifierHooks.INVENTORY_TICK,
                ModifierHooks.ON_ATTACKED
        );
    }

    // 氷結エネルギーの吸収
    public void onInventoryTick(
            IToolStackView tool,
            ModifierEntry modifier,
            Level world,
            LivingEntity holder,
            int itemSlot,
            boolean isSelected,
            boolean isCorrectSlot,
            ItemStack stack
    ) {
        if (world.isClientSide) return;
        if (!isCorrectSlot) return;

        // HP50%以下で発動
        if (holder.getHealth() > holder.getMaxHealth() * HEALTH_THRESHOLD) {
            holder.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            return;
        }

        // 凍結耐性
        holder.setTicksFrozen(0);

        // 粉雪による凍結を防止
        if (holder.isInPowderSnow) {
            holder.setTicksFrozen(0);
        }

        // 氷結エネルギーを吸収して回復
        if (holder.isInPowderSnow || holder.isInWater()) {
            if (holder.getHealth() < holder.getMaxHealth()) {
                holder.heal(0.1F);
            }

            holder.addEffect(
                    new MobEffectInstance(
                            MobEffects.REGENERATION,
                            EFFECT_DURATION,
                            REGENERATION_AMPLIFIER,
                            false,
                            false,
                            true
                    )
            );
        }
    }

    // 攻撃されたときの氷結反応
    public void onAttacked(
            IToolStackView tool,
            ModifierEntry modifier,
            EquipmentContext context,
            EquipmentSlot slotType,
            DamageSource source,
            float amount,
            boolean isDirectDamage
    ) {
        LivingEntity holder = context.getEntity();

        if (holder.level().isClientSide) return;

        if (holder.getHealth() > holder.getMaxHealth() * HEALTH_THRESHOLD) return;

        if (source == null) return;

        if (!(source.getEntity() instanceof LivingEntity attacker)) return;

        if (attacker == holder || !attacker.isAlive()) return;

        // 攻撃者に移動速度低下IIを15秒
        attacker.addEffect(
                new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        SLOWNESS_DURATION,
                        SLOWNESS_AMPLIFIER
                )
        );

        // 自分に再生IIを3秒
        holder.addEffect(
                new MobEffectInstance(
                        MobEffects.REGENERATION,
                        REGENERATION_DURATION,
                        REGENERATION_AMPLIFIER
                )
        );
    }
}
