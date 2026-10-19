package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class AuroVisionModifier extends Modifier implements MeleeHitModifierHook, InventoryTickModifierHook {

    public AuroVisionModifier() {
        super();
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.MELEE_HIT);
        builder.addHook(this, ModifierHooks.INVENTORY_TICK);
    }

    /**
     * 近接攻撃がヒットした後に発動（1.16.5のTCon仕様）
     */
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        int level = modifier.getLevel();
        LivingEntity attacker = context.getAttacker();
        
        // サーバー側かつ攻撃者が存在する場合のみ処理
        if (attacker != null && !attacker.level().isClientSide) {
            int durationTicks = 20; // 1秒

            // Glowing V (amplifier = 4)
            MobEffectInstance glow = new MobEffectInstance(MobEffects.GLOWING, durationTicks, 4);
            // Resistance V
            MobEffectInstance resistance = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, durationTicks, 4);

            attacker.addEffect(glow);
            attacker.addEffect(resistance);
        }
    }

    /**
     * 持っている間の毎tick処理
     */
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        int level = modifier.getLevel();
        // ツールが壊れておらず、手に持っている時
        if (!world.isClientSide && !tool.isBroken() && isSelected) {
            MobEffectInstance nightVision = new MobEffectInstance(
                    MobEffects.NIGHT_VISION,
                    220,    // ちらつき防止の長め設定
                    0,
                    true,   // ambient
                    false   // showParticles
            );
            entity.addEffect(nightVision);
        }
    }
}
