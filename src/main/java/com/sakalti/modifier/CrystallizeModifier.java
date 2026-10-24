
package com.sakalti.modifier;

import net.minecraft.nbt.CompoundTag;
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
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class CrystallizeModifier extends Modifier
        implements InventoryTickModifierHook, DamageBlockModifierHook {

    private static final float HEALTH_THRESHOLD = 0.20F;

    private static final int DURATION = 20 * 20;
    private static final int SLOWNESS_AMPLIFIER = 7;

    private static final String ACTIVE_UNTIL = "CrystallizeActiveUntil";
    private static final String TRIGGERED = "CrystallizeTriggered";

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(
                this,
                ModifierHooks.INVENTORY_TICK,
                ModifierHooks.DAMAGE_BLOCK
        );
    }

    // 結晶化の発動判定
    @Override
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

        CompoundTag data = holder.getPersistentData();

        long currentTime = world.getGameTime();

        // HPが20%を超えたら再発動可能
        if (holder.getHealth() > holder.getMaxHealth() * HEALTH_THRESHOLD) {
            data.remove(TRIGGERED);
            return;
        }

        // 発動済みなら再発動しない
        if (data.getBoolean(TRIGGERED)) return;

        // 最大耐久値を取得
        int maxDurability = tool.getStats().getInt(ToolStats.DURABILITY);

        if (maxDurability <= 0 || tool.isBroken()) return;

        // 耐久値の5%
        int durabilityCost = Math.max(
                1,
                (int) Math.ceil(maxDurability * 0.05D)
        );

        int remainingDurability = maxDurability - tool.getDamage();

        if (remainingDurability < durabilityCost) return;

        // 結晶化開始
        data.putBoolean(TRIGGERED, true);
        data.putLong(ACTIVE_UNTIL, currentTime + DURATION);

        // 防具耐久値を5%消費
        ToolDamageUtil.damage(
                tool,
                durabilityCost,
                holder,
                stack
        );

        // 鈍化VIIIを20秒付与
        holder.addEffect(
                new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        DURATION,
                        SLOWNESS_AMPLIFIER,
                        false,
                        true,
                        true
                )
        );
    }

    // 結晶化中のダメージを遮断
    @Override
    public boolean isDamageBlocked(
            IToolStackView tool,
            ModifierEntry modifier,
            slimeknights.tconstruct.library.tools.context.EquipmentContext context,
            EquipmentSlot slotType,
            DamageSource source,
            float amount
    ) {
        LivingEntity holder = context.getEntity();

        if (holder.level().isClientSide) return false;

        CompoundTag data = holder.getPersistentData();

        long activeUntil = data.getLong(ACTIVE_UNTIL);

        return activeUntil > holder.level().getGameTime();
    }
}
