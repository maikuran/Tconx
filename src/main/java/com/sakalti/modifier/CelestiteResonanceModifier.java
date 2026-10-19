package com.sakalti.modifier;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
public class CelestiteResonanceModifier extends Modifier implements MeleeDamageModifierHook {
 public CelestiteResonanceModifier(){super();}
 @Override protected void registerHooks(ModuleHookMap.Builder b){b.addHook(this, ModifierHooks.MELEE_DAMAGE);}
 @Override public float getMeleeDamage(IToolStackView tool,ModifierEntry entry,ToolAttackContext context,float baseDamage,float damage){if(!(context.getTarget() instanceof LivingEntity t))return damage;float max=t.getMaxHealth();return damage*(1f+0.15f*entry.getLevel()*(max>0?t.getHealth()/max:0));}
}
