package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;

import java.util.List;

public class LaserPrismModifier extends Modifier implements MeleeHitModifierHook {

    public LaserPrismModifier() {
        super();
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.MELEE_HIT);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();
        if (attacker != null && !attacker.level().isClientSide) {
            int level = modifier.getLevel();
            applyLaserPrismEffect(attacker.level(), attacker, level);
        }
    }

    /**
     * 周囲のエンティティにダメージを与えるメカニズム (1.16.5仕様)
     * @param world ワールド
     * @param source Traitを持つ対象エンティティ
     * @param level Traitレベル
     */
    public static void applyLaserPrismEffect(Level world, LivingEntity source, int level) {
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
            target.hurt(world.damageSources().onFire(), damage);
        }
    }
}
