package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;

import java.util.List;

public class LaserPrismModifier extends Modifier {

    public LaserPrismModifier(int color) {
        super(color);
    }

    /**
     * 周囲のエンティティにダメージを与えるメカニズム (1.16.5仕様)
     * @param world ワールド
     * @param source Traitを持つ対象エンティティ
     * @param level Traitレベル
     */
    public static void applyLaserPrismEffect(World world, LivingEntity source, int level) {
        if (world == null || world.isClientSide || source == null || level <= 0) return;

        double radius = 8.0D + level;
        float damage = 1.0F * level;

        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(
                LivingEntity.class,
                source.getBoundingBox().inflate(radius),
                entity -> entity != source && entity.isAlive()
        );

        for (LivingEntity target : nearbyEntities) {
            // 1.16.5 では DamageSource.ON_FIRE または DamageSource.MAGIC / DamageSource.GENERIC 等を利用
            target.hurt(DamageSource.ON_FIRE, damage);
        }
    }
}
