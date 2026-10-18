package com.sakalti.modifier;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
public class ScorchModifier extends Modifier implements MeleeHitModifierHook {
 public ScorchModifier(){super(0xE6A33C);}
 @Override protected void registerHooks(ModuleHookMap.Builder b){b.addHook(ModifierHooks.MELEE_HIT,this);}
 @Override public void afterMeleeHit(IToolStackView tool, ModifierEntry entry, ToolAttackContext context,float damageDealt){LivingEntity t=context.getTarget();if(t!=null&&!t.level().isClientSide)t.setSecondsOnFire(4+2*entry.getLevel());}
}
