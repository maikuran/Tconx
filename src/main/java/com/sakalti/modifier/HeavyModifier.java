
package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class HeavyModifier extends Modifier implements InventoryTickModifierHook {

    private static final double EXTRA_GRAVITY = 0.016D;

    @Override
    public void onInventoryTick(
            IToolStackView tool,
            ModifierEntry modifier,
            Level world,
            LivingEntity holder,
            int itemSlot,
            boolean isSelected,
            boolean isCorrectSlot,
            ItemStack stack
    ) {
        if (world.isClientSide) return;
        if (!isCorrectSlot) return;
        if (holder.onGround()) return;
        if (holder.isNoGravity()) return;
        if (holder.isFallFlying()) return;

        Vec3 velocity = holder.getDeltaMovement();

        holder.setDeltaMovement(
                velocity.x,
                velocity.y - EXTRA_GRAVITY,
                velocity.z
        );

        holder.hurtMarked = true;
    }
}
