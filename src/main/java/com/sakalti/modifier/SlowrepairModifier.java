package com.sakalti.modifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
public class SlowrepairModifier extends Modifier implements InventoryTickModifierHook {
 public SlowrepairModifier(){super(0x5A2D83);}
 @Override protected void registerHooks(ModuleHookMap.Builder b){b.addHook(ModifierHooks.INVENTORY_TICK,this);}
 @Override public void onInventoryTick(IToolStackView tool,ModifierEntry entry,Level level,LivingEntity entity,int slot,boolean selected,boolean correct,ItemStack stack){if(!level.isClientSide&&!tool.isBroken()&&stack.isDamaged()&&entity.tickCount%120==0)stack.setDamageValue(Math.max(0,stack.getDamageValue()-1));}
}
