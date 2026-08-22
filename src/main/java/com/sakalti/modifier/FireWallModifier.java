package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.List;

public class FireWallModifier extends Modifier {

    public FireWallModifier() {
        super(); // 1.16.5 では引数なしのコンストラクタ
    }

    /**
     * 攻撃ヒット時にFireWall効果を呼び出す場合（1.16.5仕様）
     */
    
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();
        if (attacker != null) {
            applyFireWallEffect(attacker.level, attacker, level);
        }
        return 0;
    }

    /**
     * FireWall効果を適用するメカニズム (1.16.5仕様)
     * @param world ワールド
     * @param source Traitを持つ対象エンティティ
     * @param level Traitレベル
     */
    public static void applyFireWallEffect(World world, LivingEntity source, int level) {
        if (world == null || world.isClientSide() || source == null || level <= 0) return;

        double radius = 2.0D + level;
        float damage = 2.0F * level;

        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class,
                source.getBoundingBox().inflate(radius),
                entity -> entity != source && entity.isAlive());

        for (LivingEntity target : nearbyEntities) {
            // 1.16.5 では DamageSource.ON_FIRE または inFire(source) を使用
            target.hurt(DamageSource.ON_FIRE, damage);
            // オプション: 相手を火だるまにする場合は以下を追加
            // target.setSecondsOnFire(3 * level);
        }
    }
}
