
package com.sakalti.modifier;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.inventory.InventoryTickModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class SuperBlastModifier extends Modifier implements InventoryTickModifierHook {

    private static final String START_TIME = "SuperBlastStartTime";
    private static final String TRIGGERED = "SuperBlastTriggered";

    private static final long CHARGE_TIME = 100L;
    private static final float EXPLOSION_POWER = 5.0F;

    @Override
    protected void registerHooks(ModifierHooks.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
    }

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

        BlockPos pos = holder.blockPosition();

        boolean inHazard =
                holder.isInLava()
                || holder.isOnFire()
                || world.getBlockState(pos).is(Blocks.MAGMA_BLOCK)
                || world.getBlockState(pos).is(Blocks.FIRE)
                || world.getBlockState(pos).is(Blocks.SOUL_FIRE)
                || world.getBlockState(pos.below()).is(Blocks.MAGMA_BLOCK)
                || world.getBlockState(pos.below()).is(Blocks.FIRE)
                || world.getBlockState(pos.below()).is(Blocks.SOUL_FIRE);

        var data = holder.getPersistentData();

        if (!inHazard) {
            data.remove(START_TIME);
            data.remove(TRIGGERED);
            return;
        }

        if (data.getBoolean(TRIGGERED)) return;

        long currentTime = world.getGameTime();

        if (!data.contains(START_TIME)) {
            data.putLong(START_TIME, currentTime);
            return;
        }

        long elapsed = currentTime - data.getLong(START_TIME);

        if (elapsed >= CHARGE_TIME) {
            if (world instanceof ServerLevel serverLevel) {

                serverLevel.explode(
                        holder,
                        holder.getX(),
                        holder.getY(),
                        holder.getZ(),
                        EXPLOSION_POWER,
                        false,
                        Level.ExplosionInteraction.MOB
                );

                data.putBoolean(TRIGGERED, true);
                data.remove(START_TIME);
            }
        }
    }
}
