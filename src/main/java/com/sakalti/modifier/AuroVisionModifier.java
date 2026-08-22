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

    public AuroVisionModifier() {
        super(0x00FFCC);
    }

    /**
     * 近接攻撃がヒットした後の処理
     * @return ヒット時のクールダウンや耐久度消費への影響数値を返します（基本は 0）
     */
    
    public int afterMeleeHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();

        // サーバー側かつ攻撃者が存在する場合
        if (attacker != null && !attacker.level.isClientSide()) {
            int durationTicks = 20; // 1秒

            // 発光 V (Glowing V)
            EffectInstance glow = new EffectInstance(Effects.GLOWING, durationTicks, 4);
            // 耐性 V (Resistance V)
            EffectInstance resistance = new EffectInstance(Effects.DAMAGE_RESISTANCE, durationTicks, 4);

            attacker.addEffect(glow);
            attacker.addEffect(resistance);
        }

        return 0; // 1.16.5 では int を返す必要があります
    }

    /**
     * インベントリ内での毎Tick処理
     */
    
    public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity entity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        // ツールが壊れておらず、手に持っている時（サーバー側）
        if (!world.isClientSide() && !tool.isBroken() && isSelected) {
            EffectInstance nightVision = new EffectInstance(
                    Effects.NIGHT_VISION,
                    220,    // ちらつき防止の長め設定
                    0,
                    true,   // ambient (枠線表示を抑える)
                    false   // showParticles (パーティクル非表示)
            );
            entity.addEffect(nightVision);
        }
    }
}
