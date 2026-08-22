package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.List;

public class LaserPrismModifier extends Modifier {

    public LaserPrismModifier() {
        super(0x00FFFF); // レーザー: シアン
    }

    /**
     * 近接攻撃がヒットした後に発動 (1.16.5 TCon 3.x 仕様)
     */
    
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();
        
        if (attacker != null) {
            // attacker.level ではなく getCommandSenderWorld() を使用
            applyLaserPrismEffect(attacker.getCommandSenderWorld(), attacker, level);
        }
        return 0; // 1.16.5 では int を返します
    }

    /**
     * 周囲のエンティティにダメージを与えるメカニズム (1.16.5仕様)
     * @param world ワールド
     * @param source Traitを持つ対象エンティティ
     * @param level Traitレベル
     */
    public static void applyLaserPrismEffect(World world, LivingEntity source, int level) {
        // isClientSide() ではなく isClientSide (フィールド) を使用
        if (world == null || world.isClientSide || source == null || level <= 0) return;

        double radius = 8.0D + level;
        float damage = 1.0F * level;

        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(
                LivingEntity.class,
                source.getBoundingBox().inflate(radius),
                entity -> entity != source && entity.isAlive()
        );

        for (LivingEntity target : nearbyEntities) {
            // レーザー風ダメージとして魔法ダメージ(MAGIC)を使用
            target.hurt(DamageSource.MAGIC, damage);
        }
    }
}
