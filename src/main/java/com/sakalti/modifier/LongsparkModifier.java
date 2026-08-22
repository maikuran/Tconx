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

    public LongsparkModifier() {
        super(); // 1.16.5 では引数なしのコンストラクタ
    }

    // 正面方向のみ検知 (1.16.5仕様)
    private LivingEntity findEntityInFront(LivingEntity user, double distance) {
        Vector3d lookVec = user.getLookAngle().normalize();
        Vector3d origin = user.position().add(0, user.getEyeHeight() * 0.7, 0);
        Vector3d target = origin.add(lookVec.scale(distance));
        
        AxisAlignedBB aabb = new AxisAlignedBB(
                target.x - 0.75, target.y - 1.0, target.z - 0.75,
                target.x + 0.75, target.y + 1.0, target.z + 0.75
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
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();

        if (attacker == null || attacker.level.isClientSide()) return 0;

        // プレイヤー攻撃判定の作成
        DamageSource source = (attacker instanceof PlayerEntity) 
                ? DamageSource.playerAttack((PlayerEntity) attacker) 
                : DamageSource.mobAttack(attacker);

        // 距離と対応する計算式パラメータ
        double[] distances = {10.0D, 8.0D, 6.0D, 4.0D, 2.0D};
        
        // 10mから順に判定（元コード通りのダメージ倍率設定）
        for (double dist : distances) {
            LivingEntity target = findEntityInFront(attacker, dist);
            if (target != null) {
                float extraDamage;
                if (dist == 10.0D) {
                    extraDamage = (float)(5.15 * (2.0 + level));
                } else if (dist == 8.0D) {
                    extraDamage = (float)(3.15 * (1.6 + level));
                } else if (dist == 6.0D) {
                    extraDamage = (float)(2.15 * (1.6 + level));
                } else if (dist == 4.0D) {
                    extraDamage = (float)(1.15 * (1.5 + level));
                } else { // 2.0D
                    extraDamage = (float)(0.5 * (1.5 + level));
                }

                target.hurt(source, extraDamage);
                break; // 最も遠い対象1体だけにヒットして終了
            }
        }

        return 0; // 1.16.5 では int を返します
    }
}
