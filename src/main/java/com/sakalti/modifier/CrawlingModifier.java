package com.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class CrawlingModifier extends Modifier {

    private static final ModifierId MODIFIER_ID =
            new ModifierId("sakalti", "crawling");

    private static final int EFFECT_DURATION = 40;

    public CrawlingModifier() {
        super();
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;

        if (player.level().isClientSide) return;
        if (!player.isAlive()) return;

        boolean hasCrawling = false;

        EquipmentSlot[] armorSlots = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        };

        for (EquipmentSlot slot : armorSlots) {
            ItemStack stack = player.getItemBySlot(slot);
            if (stack.isEmpty()) continue;

            ToolStack tool = ToolStack.from(stack);
            if (tool.getModifierLevel(MODIFIER_ID) > 0) {
                hasCrawling = true;
                break;
            }
        }

        if (hasCrawling) {
            // 見た目＋実際の Swimming 判定を強制
            player.setSwimming(true);
            player.setPose(Pose.SWIMMING);

            player.addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_RESISTANCE,
                    EFFECT_DURATION,
                    0,
                    false,
                    false,
                    true
            ));
        } else {
            // 解除時は通常状態へ戻す
            player.setSwimming(false);

            if (player.getPose() == Pose.SWIMMING && !player.isSwimming()) {
                player.setPose(Pose.STANDING);
            }
        }
    }
}
