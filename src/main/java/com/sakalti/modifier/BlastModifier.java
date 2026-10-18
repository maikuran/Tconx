package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;

/** Port of the 1.16.5 20% non-block-breaking explosion trait. */
public class BlastModifier extends Modifier implements MeleeHitModifierHook {
  public BlastModifier() { super(0xFF5A26); }
  @Override protected void registerHooks(ModuleHookMap.Builder builder) { builder.addHook(ModifierHooks.MELEE_HIT, this); }
  @Override public void afterMeleeHit(IToolStackView tool, ModifierEntry entry, ToolAttackContext context, float damageDealt) {
    LivingEntity target = context.getTarget();
    if (target == null || target.level().isClientSide || target.getRandom().nextFloat() >= 0.2f) return;
    target.level().explode(null, target.getX(), target.getY(), target.getZ(), 1.5f, Level.ExplosionInteraction.NONE);
  }
}
