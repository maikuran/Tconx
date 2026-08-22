package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class CoralianModifier extends Modifier {

    public CoralianModifier(int color) {
        super(color);
    }

    
    public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity entity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        // ツールが破損しておらず、手に持っている（またはスロットにある）場合
        // 手に持っている時限定にする場合は `if (isSelected)` を条件に追加してください
        if (!world.isClientSide && !tool.isBroken()) {
            
            // 水中息転換（水中採掘/呼吸）バフを付与 (効果時間: 220 ticks = 11秒)
            EffectInstance waterBreathing = new EffectInstance(
                    Effects.WATER_BREATHING,
                    220,
                    0,
                    true,  // ambient (ビコン等の環境エフェクトか)
                    false  // showParticles (パーティクルを表示するか)
            );

            entity.addEffect(waterBreathing);
        }
    }
}
