
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

public class IncendiumModifier extends Modifier
        implements InventoryTickModifierHook, OnAttackedModifierHook {

    private static final float HEALTH_THRESHOLD = 0.50F;

    private static final int EFFECT_DURATION = 40;

    private static final int WEAKNESS_DURATION = 20 * 15;
    private static final int REGENERATION_DURATION = 20 * 3;

    private static final int WEAKNESS_AMPLIFIER = 1;
    private static final int REGENERATION_AMPLIFIER = 1;

    private static final float FIRE_HEAL_AMOUNT = 0.1F;

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(
                this,
                ModifierHooks.INVENTORY_TICK,
                ModifierHooks.ON_ATTACKED
        );
    }

    // 炎のエネルギー吸収
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
            holder.removeEffect(MobEffects.FIRE_RESISTANCE);
            return;
        }

        // 火炎耐性
        holder.addEffect(
                new MobEffectInstance(
                        MobEffects.FIRE_RESISTANCE,
                        EFFECT_DURATION,
                        0,
                        false,
                        false,
                        true
                )
        );

        // 火や溶岩からエネルギーを吸収
        if (holder.isOnFire() || holder.isInLava()) {

            if (holder.getHealth() < holder.getMaxHealth()) {
                holder.heal(FIRE_HEAL_AMOUNT);
            }

            // 炎のエネルギーを蓄積するように再生効果
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

    // 攻撃されたときの炎の反応
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

        // 攻撃者に弱体化IIを15秒
        attacker.addEffect(
                new MobEffectInstance(
                        MobEffects.WEAKNESS,
                        WEAKNESS_DURATION,
                        WEAKNESS_AMPLIFIER
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
