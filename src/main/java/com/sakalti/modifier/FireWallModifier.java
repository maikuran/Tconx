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

public class FireWallModifier extends Modifier implements MeleeHitModifierHook {

    public FireWallModifier() {
        super();
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(ModifierHooks.MELEE_HIT, this);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();
        if (attacker != null && !attacker.level().isClientSide) {
            int level = modifier.getLevel();
            applyFireWallEffect(attacker.level(), attacker, level);
        }
    }

    /**
     * FireWall効果を適用するメカニズム (1.16.5仕様)
     * @param world ワールド
     * @param source Traitを持つ対象エンティティ
     * @param level Traitレベル
     */
    public static void applyFireWallEffect(Level world, LivingEntity source, int level) {
        if (world == null || world.isClientSide || source == null || level <= 0) return;

        double radius = 2.0D + level;
        float damage = 2.0F * level;

        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class,
                source.getBoundingBox().inflate(radius),
                entity -> entity != source && entity.isAlive());

        for (LivingEntity target : nearbyEntities) {
            // 1.16.5 では DamageSource.ON_FIRE または DamageSource.IN_FIRE を使用
            target.hurt(world.damageSources().onFire(), damage);
        }
    }
}
