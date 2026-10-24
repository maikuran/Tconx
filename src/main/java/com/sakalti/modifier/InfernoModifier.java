
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

public class InfernoModifier extends Modifier
        implements InventoryTickModifierHook, OnAttackedModifierHook {

    private static final float HEALTH_THRESHOLD = 0.50F;

    private static final int EFFECT_DURATION = 40;

    private static final int STRENGTH_AMPLIFIER = 4;
    private static final int SLOWNESS_AMPLIFIER = 1;

    private static final int REFLECT_FIRE_SECONDS = 5;
    private static final int SLOWNESS_DURATION = 20 * 15;

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(
                this,
                ModifierHooks.INVENTORY_TICK,
                ModifierHooks.ON_ATTACKED
        );
    }

    // 炎の身体・水による即死
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

        // 水に触れると即死
        if (holder.isInWaterOrBubble()) {
            DamageSource drowning = holder.damageSources().drown();

            holder.setHealth(0.0F);
            holder.die(drowning);
            return;
        }

        // HP50%未満なら炎の身体を解除
        if (holder.getHealth() < holder.getMaxHealth() * HEALTH_THRESHOLD) {
            holder.removeEffect(MobEffects.FIRE_RESISTANCE);
            holder.removeEffect(MobEffects.DAMAGE_BOOST);
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

        // 攻撃力上昇V
        holder.addEffect(
                new MobEffectInstance(
                        MobEffects.DAMAGE_BOOST,
                        EFFECT_DURATION,
                        STRENGTH_AMPLIFIER,
                        false,
                        false,
                        true
                )
        );
    }

    // 攻撃してきた相手への反撃
    public void onAttacked(
            IToolStackView tool,
            ModifierEntry modifier,
            LivingEntity holder,
            DamageSource source,
            float amount,
            boolean isDirect,
            boolean isBlocked
    ) {
        if (holder.level().isClientSide) return;

        // HP50%以上でのみ反撃
        if (holder.getHealth() < holder.getMaxHealth() * HEALTH_THRESHOLD) return;

        if (source == null) return;

        if (!(source.getEntity() instanceof LivingEntity attacker)) return;

        if (attacker == holder || !attacker.isAlive()) return;

        // 鈍化IIを15秒付与
        attacker.addEffect(
                new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        SLOWNESS_DURATION,
                        SLOWNESS_AMPLIFIER
                )
        );

        // 相手に着火
        attacker.setSecondsOnFire(REFLECT_FIRE_SECONDS);
    }
}
