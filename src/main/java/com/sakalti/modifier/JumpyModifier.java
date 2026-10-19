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
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class JumpyModifier extends Modifier implements InventoryTickModifierHook {

    public JumpyModifier() {
        super();
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.INVENTORY_TICK);
    }

    /**
     * インベントリ/手持ちにある間の毎tick処理 (1.16.5仕様)
     */
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        int level = modifier.getLevel();
        // ツールが破損しておらず、メイン手/オフ手に持っている時
        if (!world.isClientSide && !tool.isBroken() && isSelected) {
            
            // Jump Boost 効果を付与（amplifier は 0 が1段階目なので level - 1）
            MobEffectInstance jumpBoost = new MobEffectInstance(
                    MobEffects.JUMP,
                    220,                           // ちらつき防止の長め設定 (11秒)
                    Math.max(0, level - 1),        // amplifier
                    true,                          // ambient
                    false                          // showParticles
            );

            entity.addEffect(jumpBoost);
        }
    }
}
