package com.sakalti.modifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
public class SaturationModifier extends Modifier implements InventoryTickModifierHook {
 public SaturationModifier(){super();}
 @Override protected void registerHooks(ModuleHookMap.Builder b){b.addHook(this, ModifierHooks.INVENTORY_TICK);}
 @Override public void onInventoryTick(IToolStackView tool,ModifierEntry entry,Level level,LivingEntity entity,int slot,boolean selected,boolean correct,ItemStack stack){if(level.isClientSide||tool.isBroken()||!selected||entity.tickCount%20!=0||!(entity instanceof Player p))return;p.getFoodData().eat(entry.getLevel(),entry.getLevel()*0.5f);}
}
