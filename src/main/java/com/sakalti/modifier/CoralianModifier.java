package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class CoralianModifier extends Modifier {

    public CoralianModifier() {
        super(0x33FFCC);
    }

    
    public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity entity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        // ツールが破損しておらず、メイン手またはオフ手に持っている場合（isSelected）
        if (!world.isClientSide() && !tool.isBroken() && isSelected) {
            
            // 水中息転換（水中呼吸）バフを付与 (効果時間: 220 ticks = 11秒)
            EffectInstance waterBreathing = new EffectInstance(
                    Effects.WATER_BREATHING,
                    220,
                    0,
                    true,  // ambient
                    false  // showParticles
            );

            entity.addEffect(waterBreathing);
        }
    }
}
