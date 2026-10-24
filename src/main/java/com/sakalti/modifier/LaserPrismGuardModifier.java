
package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

import java.util.List;

public class LaserPrismGuardModifier extends Modifier
        implements OnAttackedModifierHook {

    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.ON_ATTACKED);
    }

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
        if (!holder.isAlive()) return;
        if (source == null) return;

        int level = modifier.getLevel();

        applyLaserPrismEffect(holder.level(), holder, level);
    }

    /**
     * 周囲の敵にレーザープリズムダメージを与える
     */
    public static void applyLaserPrismEffect(
            Level world,
            LivingEntity source,
            int level
    ) {
        if (world == null || world.isClientSide) return;
        if (source == null || !source.isAlive()) return;
        if (level <= 0) return;

        double radius = 8.0D + level;
        float damage = 1.0F * level;

        List<LivingEntity> nearbyEntities =
                world.getEntitiesOfClass(
                        LivingEntity.class,
                        source.getBoundingBox().inflate(radius),
                        entity ->
                                entity != source
                                && entity.isAlive()
                                && !entity.isAlliedTo(source)
                );

        for (LivingEntity target : nearbyEntities) {
            target.hurt(
                    world.damageSources().magic(),
                    damage
            );
        }
    }
}
