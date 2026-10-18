package com.sakalti.modifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
public class LureMeteorModifier extends Modifier implements MeleeHitModifierHook {
 public LureMeteorModifier(){super(0xFFB000);}
 @Override protected void registerHooks(ModuleHookMap.Builder b){b.addHook(ModifierHooks.MELEE_HIT,this);}
 @Override public void afterMeleeHit(IToolStackView tool,ModifierEntry entry,ToolAttackContext context,float damageDealt){LivingEntity t=context.getTarget();if(t==null||t.level().isClientSide)return;LivingEntity a=context.getAttacker();DamageSource src=a instanceof Player p?t.level().damageSources().playerAttack(p):t.level().damageSources().mobAttack(a);t.hurt(src,6.0f*entry.getLevel());}
}
