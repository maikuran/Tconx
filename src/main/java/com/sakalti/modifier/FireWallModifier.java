package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;

import java.util.List;

public class FireWallModifier extends Modifier {

    public FireWallModifier(int color) {
        super(color);
    }

    /**
     * FireWall効果を適用するメカニズム (1.16.5仕様)
     * @param world ワールド
     * @param source Traitを持つ対象エンティティ
     * @param level Traitレベル
     */
    public static void applyFireWallEffect(World world, LivingEntity source, int level) {
        if (world == null || world.isClientSide || source == null || level <= 0) return;

        double radius = 2.0D + level;
        float damage = 2.0F * level;

        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class,
                source.getBoundingBox().inflate(radius),
                entity -> entity != source && entity.isAlive());

        for (LivingEntity target : nearbyEntities) {
            // 1.16.5 では DamageSource.ON_FIRE または DamageSource.IN_FIRE を使用
            target.hurt(DamageSource.ON_FIRE, damage);
        }
    }
}
