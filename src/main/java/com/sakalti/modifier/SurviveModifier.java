package com.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class SurviveModifier extends Modifier {

    private static final ModifierId MODIFIER_ID =
            new ModifierId("sakalti", "survive");

    private static final float REVIVE_HEALTH = 1.0F;

    private static final int PROTECTION_DURATION = 20 * 5;

    public SurviveModifier() {
        super();
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        // クライアント側では処理しない
        if (player.level().isClientSide) {
            return;
        }

        if (!player.isAlive() && player.getHealth() > 0.0F) {
            return;
        }

        // Surviveを持つ防具を装備しているか確認
        if (!hasSurviveModifier(player)) {
            return;
        }

        // 死亡をキャンセル
        event.setCanceled(true);

        // 復活時の体力
        player.setHealth(REVIVE_HEALTH);

        // 復活直後の保護
        player.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_RESISTANCE,
                PROTECTION_DURATION,
                4,
                false,
                true,
                true
        ));

        player.addEffect(new MobEffectInstance(
                MobEffects.REGENERATION,
                PROTECTION_DURATION,
                1,
                false,
                true,
                true
        ));
    }

    private static boolean hasSurviveModifier(Player player) {

        EquipmentSlot[] armorSlots = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        };

        for (EquipmentSlot slot : armorSlots) {

            ItemStack stack = player.getItemBySlot(slot);

            if (stack.isEmpty()) {
                continue;
            }

            ToolStack tool = ToolStack.from(stack);

            if (tool.getModifierLevel(MODIFIER_ID) > 0) {
                return true;
            }
        }

        return false;
    }
}
