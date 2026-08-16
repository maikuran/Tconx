package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class IceBindModifier extends Modifier {

    // 正面方向のみ検知
    private LivingEntity findEntityInFront(LivingEntity user, double distance) {
        Vec3 lookVec = user.getLookAngle().normalize();
        Vec3 origin = user.position().add(0, user.getEyeHeight() * 0.7, 0);
        Vec3 target = origin.add(lookVec.scale(distance));
        AABB aabb = new AABB(target.x - 0.5, target.y - 1, target.z - 0.5, target.x + 0.5, target.y + 1, target.z + 0.5);

        List<LivingEntity> list = user.level().getEntitiesOfClass(LivingEntity.class, aabb, 
            (entity) -> entity != user && entity.isAlive());
        return list.isEmpty() ? null : list.get(0);
    }

    public void afterEntityHit(ToolStack tool, int level, LivingEntity target, LivingEntity attacker, float damage, boolean isCritical) {
        if (attacker == null) return;

        // 2m先の判定
        LivingEntity entity2m = findEntityInFront(attacker, 2.0);
        if (entity2m != null) {
            float extraDamage = (float)(4.5 * (1.5 + level));
            entity2m.hurt(attacker.damageSources().playerAttack((net.minecraft.world.entity.player.Player)attacker), extraDamage);
            return;
        }

        // 4m先の判定
        LivingEntity entity4m = findEntityInFront(attacker, 4.0);
        if (entity4m != null) {
            float extraDamage = (float)(3.15 * (1.5 + level));
            entity4m.hurt(attacker.damageSources().playerAttack((net.minecraft.world.entity.player.Player)attacker), extraDamage);
        }
    }
}
