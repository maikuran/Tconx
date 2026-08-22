package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class LureMeteorModifier extends Modifier {

    public LureMeteorModifier() {
        super(0xFF6600); // 隕石: オレンジ
    }

    /**
     * 近接攻撃ヒット後にメテオシャワーを発動 (1.16.5 TCon 3.x 仕様)
     */
    @Override
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        LivingEntity attacker = context.getAttacker();

        // サーバー側かつ対象と攻撃者が存在する場合のみ処理
        if (target != null && attacker != null) {
            World world = target.getCommandSenderWorld();

            if (!world.isClientSide) {
                // 1. 空から降ってくる紫色の演出（エンドドラゴンのブレス / パーティクル）
                spawnMeteorParticles(world, target);

                // 2. 防具貫通30% のダメージ計算と適用
                applyArmorPiercingDamage(world, attacker, target, level);
            }
        }

        return 0; // 1.16.5 では int を返します
    }

    /**
     * 空から対象の頭上へ落ちてくるエンドラの紫パーティクル演出
     */
    private void spawnMeteorParticles(World world, LivingEntity target) {
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;

            double targetX = target.getX();
            double targetY = target.getY();
            double targetZ = target.getZ();

            // 上空10mからターゲットの位置まで紫のパーティクル（DRAGON_BREATH）を降らせる
            for (double yOffset = 10.0D; yOffset >= 0.0D; yOffset -= 0.5D) {
                serverWorld.sendParticles(
                        ParticleTypes.DRAGON_BREATH,
                        targetX + (world.random.nextDouble() - 0.5D) * 0.5D,
                        targetY + yOffset,
                        targetZ + (world.random.nextDouble() - 0.5D) * 0.5D,
                        5,    // 粒子数
                        0.1, 0.1, 0.1, // 散らばり
                        0.02  // 速度
                );
            }

            // 着弾地点の演出（広がる紫の衝撃波）
            serverWorld.sendParticles(
                    ParticleTypes.DRAGON_BREATH,
                    targetX, targetY + 0.5D, targetZ,
                    40, 1.0D, 0.5D, 1.0D, 0.05D
            );
        }
    }

    /**
     * 6.0 * level ダメージ (防具貫通30%)
     */
    private void applyArmorPiercingDamage(World world, LivingEntity attacker, LivingEntity target, int level) {
        float totalDamage = 6.0F * level;

        // 防具貫通30% = 通常ダメージ70% + 防具無視(bypassArmor)ダメージ30%
        float bypassDamage = totalDamage * 0.30F;
        float normalDamage = totalDamage * 0.70F;

        // 攻撃者の属性に合わせたダメージソース作成
        DamageSource normalSource = (attacker instanceof PlayerEntity)
                ? DamageSource.playerAttack((PlayerEntity) attacker)
                : DamageSource.mobAttack(attacker);

        // 防具無視（bypassArmor）属性のダメージソース作成
        DamageSource bypassSource = (attacker instanceof PlayerEntity)
                ? DamageSource.playerAttack((PlayerEntity) attacker).bypassArmor()
                : DamageSource.mobAttack(attacker).bypassArmor();

        // ダメージ適用
        target.hurt(normalSource, normalDamage);
        target.hurt(bypassSource, bypassDamage);
    }
}
