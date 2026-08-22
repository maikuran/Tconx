package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

public class AuroVisionModifier extends Modifier {

    public AuroVisionModifier(int color) {
        super(color);
    }

    /**
     * 近接攻撃がヒットした後に発動（1.16.5のTCon仕様）
     */
    @Override
    public void afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();
        
        // サーバー側かつ攻撃者が存在する場合のみ処理
        if (!attacker.level.isClientSide) {
            int durationTicks = 20; // 1秒

            // Glowing V (amplifier = 4)
            EffectInstance glow = new EffectInstance(Effects.GLOWING, durationTicks, 4);
            // Resistance V
            EffectInstance resistance = new EffectInstance(Effects.DAMAGE_RESISTANCE, durationTicks, 4);

            attacker.addEffect(glow);
            attacker.addEffect(resistance);
        }
    }

    /**
     * 持っている間の毎tick処理
     */
    @Override
    public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity entity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        // ツールが壊れておらず、手に持っている時
        if (!world.isClientSide && !tool.isBroken() && isSelected) {
            EffectInstance nightVision = new EffectInstance(
                    Effects.NIGHT_VISION,
                    220,    // ちらつき防止の長め設定
                    0,
                    true,   // ambient
                    false   // showParticles
            );
            entity.addEffect(nightVision);
        }
    }
}
