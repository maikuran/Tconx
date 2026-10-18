package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

public class LongsparkModifier extends Modifier implements MeleeHitModifierHook {

    public LongsparkModifier() {
        super();
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(ModifierHooks.MELEE_HIT, this);
    }

    // 正面方向のみ検知 (1.16.5仕様)
    private LivingEntity findEntityInFront(LivingEntity user, double distance) {
        Vec3 lookVec = user.getLookAngle().normalize();
        Vec3 origin = user.position().add(0, user.getEyeHeight() * 0.7, 0);
        Vec3 target = origin.add(lookVec.scale(distance));
        AABB aabb = new AABB(
                target.x - 0.5, target.y - 1.0, target.z - 0.5,
                target.x + 0.5, target.y + 1.0, target.z + 0.5
        );

        List<LivingEntity> list = user.level().getEntitiesOfClass(
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
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        int level = modifier.getLevel();
        LivingEntity attacker = context.getAttacker();

        if (attacker == null || attacker.level().isClientSide) return;

        // プレイヤー攻撃判定の作成（安全策つき）
        DamageSource source = (attacker instanceof Player) 
                ? attacker.level().damageSources().playerAttack((Player) attacker) 
                : attacker.level().damageSources().mobAttack(attacker);

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
