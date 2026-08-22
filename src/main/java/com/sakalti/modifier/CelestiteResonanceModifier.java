package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.List;

public class CelestiteResonanceModifier extends Modifier {

    public CelestiteResonanceModifier() {
        super(); // 1.16.5 仕様の無引数コンストラクタ
    }

    /**
     * ダメージ計算時：対象の体力比例でボーナスダメージ（最大+50%）
     */
    @Override
    public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity target = context.getTarget();
        if (target != null) {
            // 対象の現在HPの割合に応じてダメージ倍率アップ（タフなボスほど強い）
            float healthRatio = target.getHealth() / target.getMaxHealth();
            float bonusMultiplier = 1.0f + (0.15f * level * healthRatio); // Lv1で最大+15%、Lv3で+45%
            return damage * bonusMultiplier;
        }
        return damage;
    }

    /**
     * 攻撃ヒット後：青い衝撃波を周囲に放ち、範囲追撃
     */
    @Override
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getTarget();
        LivingEntity attacker = context.getAttacker();

        if (target != null && attacker != null && !target.level.isClientSide()) {
            World world = target.level;

            // 青い炎・青い光の粒子エフェクトを周囲に展開
            if (world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld) world;
                
                // 青い炎のリング演出
                for (int i = 0; i < 16; i++) {
                    double angle = i * Math.PI / 8;
                    double x = target.getX() + Math.cos(angle) * 2.5D;
                    double z = target.getZ() + Math.sin(angle) * 2.5D;
                    serverWorld.sendParticles(
                            ParticleTypes.SOUL_FIRE_FLAME,
                            x, target.getY() + 0.5D, z,
                            2, 0.1, 0.2, 0.1, 0.02
                    );
                }
            }

            // 周囲 3.5m 以内の敵に青い共鳴追撃（防御無視ダメージ）
            double range = 3.5D;
            AxisAlignedBB area = target.getBoundingBox().inflate(range);
            List<LivingEntity> nearbyEnemies = world.getEntitiesOfClass(
                    LivingEntity.class, area,
                    e -> e != attacker && e != target && e.isAlive()
            );

            float splashDamage = damageDealt * (0.20f * level); // 1回あたりの威力の20%*レベルを撒き散らす
            DamageSource soulDamage = (attacker instanceof PlayerEntity) 
                    ? DamageSource.playerAttack((PlayerEntity) attacker).bypassArmor() 
                    : DamageSource.mobAttack(attacker).bypassArmor();

            for (LivingEntity enemy : nearbyEnemies) {
                enemy.hurt(soulDamage, splashDamage);
            }
        }

        return 0;
    }
}
