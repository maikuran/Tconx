package com.sakalti.modifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import java.util.Random;
public class LuckyModifier extends Modifier implements InventoryTickModifierHook {
 private static final String TIMER="SakaltiLuckyTimer", LIMIT="SakaltiLuckyLimit"; private static final Random RANDOM=new Random();
 public LuckyModifier(){super(0xFFB81D);}
 @Override protected void registerHooks(ModuleHookMap.Builder b){b.addHook(ModifierHooks.INVENTORY_TICK,this);}
 @Override public void onInventoryTick(IToolStackView tool,ModifierEntry entry,Level level,LivingEntity entity,int slot,boolean selected,boolean correct,ItemStack stack){
  if(level.isClientSide||tool.isBroken()||!selected||stack.isEmpty())return;
  CompoundTag tag=stack.getOrCreateTag(); int timer=tag.getInt(TIMER), limit=tag.getInt(LIMIT);
  if(limit<20||limit>7200)limit=20+RANDOM.nextInt(7181);
  timer++;
  if(timer>=limit){slimeknights.tconstruct.library.tools.nbt.ToolStack.from(stack).setDamage(0);timer=0;limit=20+RANDOM.nextInt(7181);}
  tag.putInt(TIMER,timer);tag.putInt(LIMIT,limit);
 }
}
