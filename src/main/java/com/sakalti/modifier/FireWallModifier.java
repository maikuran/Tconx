package com.sakalti.sakalti.modifier;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

public class FireWallModifier extends Modifier {

    /**
     * FireWall効果を適用するメカニズム
     * @param world ワールド
     * @param source Traitを持つ対象エンティティ
     * @param level Traitレベル
     */
    public static void applyFireWallEffect(Level world, LivingEntity source, int level) {
        if (world.isClientSide || source == null || level <= 0) return;

        double radius = 2.0D + level;
        float damage = 2.0F * level;

        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class,
                source.getBoundingBox().inflate(radius),
                entity -> entity != source && entity.isAlive());

        for (LivingEntity target : nearbyEntities) {
            target.hurt(world.damageSources().onFire(), damage);
        }
    }
}
