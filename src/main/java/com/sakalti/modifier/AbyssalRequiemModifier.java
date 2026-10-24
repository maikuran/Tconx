
package com.sakalti.modifier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class AbyssalRequiemModifier {

    private static final ModifierId MODIFIER_ID =
            new ModifierId("sakalti", "abyssal_requiem");

    private static final float MAX_HEAL = 4.0F;

    private static final int ABSORPTION_DURATION = 20 * 4;

    @SubscribeEvent
    public static void onTargetKilled(LivingDeathEvent event) {

        Entity killer = event.getSource().getEntity();

        if (!(killer instanceof Player player)) return;

        if (player.level().isClientSide) return;

        // 防具4部位のModifierレベルを合算
        int totalLevel = 0;

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

            totalLevel += tool.getModifierLevel(MODIFIER_ID);
        }

        if (totalLevel <= 0) return;

        // 敵の最大HPを取得
        float enemyMaxHealth =
                event.getEntity().getMaxHealth();

        // 敵の生命力を防御エネルギーに変換
        float healAmount =
                Math.min(MAX_HEAL, enemyMaxHealth * 0.1F * totalLevel);

        if (player.getHealth() < player.getMaxHealth()) {
            player.heal(healAmount);
        }

        // 吸収効果
        player.addEffect(
                new MobEffectInstance(
                        MobEffects.ABSORPTION,
                        ABSORPTION_DURATION * totalLevel,
                        Math.min(totalLevel - 1, 3),
                        false,
                        false,
                        true
                )
        );
    }
}
