
package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

import java.util.List;

public class LongsparkGuardModifier extends Modifier
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

        int level = modifier.getLevel();

        applyLongsparkEffect(holder.level(), holder, level);
    }

    /**
     * 正面方向に光属性の反撃を放つ
     */
    public static void applyLongsparkEffect(
            Level world,
            LivingEntity holder,
            int level
    ) {
        if (world == null || world.isClientSide) return;
        if (holder == null || !holder.isAlive()) return;
        if (level <= 0) return;

        double[] distances = {
                2.0D,
                4.0D,
                6.0D,
                8.0D,
                10.0D
        };

        float[] damageValues = {
                (float)(0.5D * (1.5D + level)),
                (float)(1.15D * (1.5D + level)),
                (float)(2.15D * (1.6D + level)),
                (float)(3.15D * (1.6D + level)),
                (float)(5.15D * (2.0D + level))
        };

        DamageSource lightDamage = world.damageSources().magic();

        for (int i = 0; i < distances.length; i++) {

            LivingEntity target = findEntityInFront(
                    holder,
                    distances[i]
            );

            if (target == null) continue;
            if (target.isAlliedTo(holder)) continue;

            target.hurt(lightDamage, damageValues[i]);
            return;
        }
    }

    /**
     * 装備者の視線方向にあるエンティティを検出
     */
    private static LivingEntity findEntityInFront(
            LivingEntity holder,
            double distance
    ) {
        Vec3 lookVec = holder.getLookAngle().normalize();

        Vec3 origin = holder.position().add(
                0,
                holder.getEyeHeight() * 0.7D,
                0
        );

        Vec3 targetPosition = origin.add(
                lookVec.scale(distance)
        );

        AABB searchArea = new AABB(
                targetPosition.x - 0.5D,
                targetPosition.y - 1.0D,
                targetPosition.z - 0.5D,
                targetPosition.x + 0.5D,
                targetPosition.y + 1.0D,
                targetPosition.z + 0.5D
        );

        List<LivingEntity> entities =
                holder.level().getEntitiesOfClass(
                        LivingEntity.class,
                        searchArea,
                        entity ->
                                entity != holder
                                && entity.isAlive()
                                && !entity.isAlliedTo(holder)
                );

        return entities.isEmpty() ? null : entities.get(0);
    }
}
