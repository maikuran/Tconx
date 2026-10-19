package com.sakalti.modifier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Keeps the 1.16.5 Gradually trait's cumulative mining progression in the 1.20.1 event API. */
@Mod.EventBusSubscriber(modid = "sakalti", bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class GraduallyEvents {
  private static final Map<UUID, Long> BLOCKS = new ConcurrentHashMap<>();
  private static final ResourceLocation GRADUALLY = new ResourceLocation("sakalti", "gradually");
  private GraduallyEvents() {}

  @SubscribeEvent
  public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
    ItemStack stack = event.getEntity().getMainHandItem();
    if (!stack.isEmpty() && !ToolStack.from(stack).isBroken() && ToolStack.from(stack).getModifierLevel(new ModifierId(GRADUALLY)) > 0) {
      long count = BLOCKS.getOrDefault(event.getEntity().getUUID(), 0L);
      float bonus = (float)Math.min(3.0, Math.log1p(count) * 0.08);
      event.setNewSpeed(event.getNewSpeed() * (1.0f + bonus));
    }
  }

  @SubscribeEvent
  public static void onBlockBreak(BlockEvent.BreakEvent event) {
    if (!(event.getPlayer() instanceof ServerPlayer player)) return;
    ItemStack stack = player.getMainHandItem();
    if (stack.isEmpty()) return;
    ToolStack tool = ToolStack.from(stack);
    if (!tool.isBroken() && tool.getModifierLevel(new ModifierId(GRADUALLY)) > 0) {
      BLOCKS.merge(player.getUUID(), 1L, Long::sum);
    }
  }
}
