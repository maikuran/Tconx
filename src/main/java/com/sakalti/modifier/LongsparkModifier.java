package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.List;

public class LongsparkModifier extends Modifier {

    public LongsparkModifier(int color) {
        super(color);
    }

    // 正面方向のみ検知 (1.16.5仕様)
    private LivingEntity findEntityInFront(LivingEntity user, double distance) {
        Vector3d lookVec = user.getLookAngle().normalize();
        Vector3d origin = user.position().add(0, user.getEyeHeight() * 0.7, 0);
        Vector3d target = origin.add(lookVec.scale(distance));
        AxisAlignedBB aabb = new AxisAlignedBB(
                target.x - 0.5, target.y - 1.0, target.z - 0.5,
                target.x + 0.5, target.y + 1.0, target.z + 0.5
        );

        List<LivingEntity> list = user.level.getEntitiesOfClass(
                LivingEntity.class, 
                aabb, 
                entity -> entity != user && entity.isAlive()
        );

        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 近接攻撃ヒット後の処理 (1.16.5仕様)
     */
    @Override
    public void afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();

        if (attacker == null || attacker.level.isClientSide) return;

        // プレイヤー攻撃判定の作成（安全策つき）
        DamageSource source = (attacker instanceof PlayerEntity) 
                ? DamageSource.playerAttack((PlayerEntity) attacker) 
                : DamageSource.mobAttack(attacker);

        // 2m先の判定
        LivingEntity entity2m = findEntityInFront(attacker, 2.0D);
        if (entity2m != null) {
            float extraDamage = (float)(0.5 * (1.5 + level));
            entity2m.hurt(source, extraDamage);
            return;
        }

        // 4m先の判定
        LivingEntity entity4m = findEntityInFront(attacker, 4.0D);
        if (entity4m != null) {
            float extraDamage = (float)(1.15 * (1.5 + level));
            entity4m.hurt(source, extraDamage);
            return;
        }

        // 6m先の判定
        LivingEntity entity6m = findEntityInFront(attacker, 6.0D);
        if (entity6m != null) {
            float extraDamage = (float)(2.15 * (1.6 + level));
            entity6m.hurt(source, extraDamage);
            return;
        }

        // 8m先の判定
        LivingEntity entity8m = findEntityInFront(attacker, 8.0D);
        if (entity8m != null) {
            float extraDamage = (float)(3.15 * (1.6 + level));
            entity8m.hurt(source, extraDamage);
            return;
        }

        // 10m先の判定
        LivingEntity entity10m = findEntityInFront(attacker, 10.0D);
        if (entity10m != null) {
            float extraDamage = (float)(5.15 * (2.0 + level));
            entity10m.hurt(source, extraDamage);
        }
    }
}
