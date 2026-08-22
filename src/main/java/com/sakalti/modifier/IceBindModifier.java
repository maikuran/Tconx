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

public class IceBindModifier extends Modifier {

    public IceBindModifier() {
        super(); // 1.16.5 では引数なしのコンストラクタ
    }

    // 正面方向の特定距離にいるエンティティを検知 (1.16.5仕様)
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
    
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();

        if (attacker == null || attacker.level.isClientSide()) return 0;

        // ダメージソースの構築
        DamageSource source = (attacker instanceof PlayerEntity) 
                ? DamageSource.playerAttack((PlayerEntity) attacker) 
                : DamageSource.mobAttack(attacker);

        // 遠い順（10m → 8m → 6m → 4m → 2m）に判定することで、遠距離ヒットを優先判定＆最大ダメージに設定
        double[] distances = {10.0D, 8.0D, 6.0D, 4.0D, 2.0D};
        
        // 距離ごとの基礎ダメージ倍率（10mが最高、手前に行くほど減衰）
        float[] baseMultipliers = {6.0f, 5.0f, 4.0f, 3.0f, 2.0f};

        for (int i = 0; i < distances.length; i++) {
            double dist = distances[i];
            LivingEntity target = findEntityInFront(attacker, dist);
            
            if (target != null) {
                // 10m（index 0）の時に最高ダメージが発生
                float extraDamage = (float) (baseMultipliers[i] * (1.5 + level));
                target.hurt(source, extraDamage);
                break; // 最初に見つかった最も遠い対象1体だけに発動して終了
            }
        }

        return 0; // 1.16.5 では int を返します
    }
}
