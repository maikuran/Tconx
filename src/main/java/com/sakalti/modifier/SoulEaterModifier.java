package com.sakalti.modifier;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import java.util.Random;

@Mod.EventBusSubscriber(modid="sakalti", bus=Mod.EventBusSubscriber.Bus.FORGE)
public class SoulEaterModifier extends Modifier implements MeleeDamageModifierHook {
  private static final Random RANDOM = new Random();
  public SoulEaterModifier(){super(0x5B1D72);}
  @Override protected void registerHooks(ModuleHookMap.Builder b){b.addHook(ModifierHooks.MELEE_DAMAGE,this);}
  @Override public float getMeleeDamage(IToolStackView tool, ModifierEntry entry, ToolAttackContext context, float baseDamage, float damage) {
    return damage + SoulEaterData.read(tool.getPersistentData()).bonus;
  }
  @Override public Component getDisplayName(IToolStackView tool, int level) {
    SoulEaterData d = SoulEaterData.read(tool.getPersistentData());
    Component base = super.getDisplayName(tool, level);
    return d.killcount > 0 ? base.copy().append(Component.literal(" [Killed: " + d.killcount + "] [Bonus: " + d.bonus + "]")) : base;
  }
  @SubscribeEvent public static void onTargetKilled(LivingDeathEvent event) {
    Entity killer = event.getSource().getEntity();
    if (!(killer instanceof Player player) || player.level().isClientSide) return;
    var stack = player.getMainHandItem(); if (stack.isEmpty()) return;
    ToolStack tool = ToolStack.from(stack);
    if (tool.getModifierLevel(new ResourceLocation("sakalti", "soul_eater")) <= 0) return;
    SoulEaterData d = SoulEaterData.read(tool.getPersistentData());
    float maxHealth = event.getEntity().getMaxHealth();
    d.killcount++; d.health=maxHealth;
    float increment = Math.round(RANDOM.nextFloat() * maxHealth * 100.0f) / 16666.666f;
    d.bonus = Math.round((d.bonus + increment) * 100.0f) / 100.0f;
    d.write(tool.getPersistentData());
  }
}
