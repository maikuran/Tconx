package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class JumpyModifier extends Modifier {

    public JumpyModifier() {
        super(); // 1.16.5 では引数なしのコンストラクタ
    }

    /**
     * インベントリ/手持ちにある間の毎tick処理 (1.16.5仕様)
     */
    
    public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity entity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        // ツールが破損しておらず、メイン手/オフ手に持っている時（サーバー側）
        if (!world.isClientSide() && !tool.isBroken() && isSelected) {
            
            // Jump Boost（跳躍力上昇）効果を付与
            EffectInstance jumpBoost = new EffectInstance(
                    Effects.JUMP,
                    220,                           // ちらつき防止の長め設定 (11秒)
                    Math.max(0, level - 1),        // amplifier
                    true,                          // ambient
                    false                          // showParticles
            );

            entity.addEffect(jumpBoost);
        }
    }
}
