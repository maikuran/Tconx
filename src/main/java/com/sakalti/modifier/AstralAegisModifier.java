
package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

import java.util.List;

public class AstralAegisModifier extends Modifier
        implements OnAttackedModifierHook, ModifyDamageModifierHook {

    private static final float DAMAGE_REDUCTION = 0.1F;
    private static final double RADIUS = 6.0D;

    public AstralAegisModifier() {
        super();
    }

    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(
                this,
                ModifierHooks.ON_ATTACKED,
                ModifierHooks.MODIFY_DAMAGE
        );
    }

    // 被ダメージを30%軽減
    public float modifyDamageTaken(
            IToolStackView tool,
            ModifierEntry modifier,
            EquipmentContext context,
            EquipmentSlot slotType,
            DamageSource source,
            float amount,
            boolean isDirectDamage
    ) {
        return amount * (1.0F - DAMAGE_REDUCTION);
    }

    // 攻撃を受けたとき星界反撃
    public void onAttacked(
            IToolStackView tool,
            ModifierEntry modifier,
            EquipmentContext context,
            EquipmentSlot slotType,
            DamageSource source,
            float amount,
            boolean isDirectDamage
    ) {
        LivingEntity holder = context.getEntity();

        if (holder.level().isClientSide) return;
        if (!holder.isAlive()) return;

        applyAstralAegis(holder, modifier.getLevel());
    }

    private static void applyAstralAegis(
            LivingEntity holder,
            int level
    ) {
        if (level <= 0) return;

        Level world = holder.level();
        float damage = 2.0F * level;

        List<LivingEntity> targets =
                world.getEntitiesOfClass(
                        LivingEntity.class,
                        holder.getBoundingBox().inflate(RADIUS),
                        entity ->
                                entity != holder
                                && entity.isAlive()
                                && !entity.isAlliedTo(holder)
                );

        for (LivingEntity target : targets) {
            target.hurt(
                    world.damageSources().magic(),
                    damage
            );
        }
    }
}
