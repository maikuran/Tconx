package com.sakalti.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class SuperMagnetModifier extends Modifier {

    // 半径（引き寄せ範囲）を決める
    private static final double RADIUS = 6.0D;
    private static final double PULL_STRENGTH = 0.5D;
    
    public void onInventoryTick(ToolStack tool, int level, LivingEntity holder, boolean isSelected, boolean isHeld) {
        // 手に持っている場合のみ発動
        if (holder == null || holder.level().isClientSide) return;

        Level world = holder.level();
        AABB area = holder.getBoundingBox().inflate(RADIUS);

        List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, area, item -> !item.hasPickUpDelay());

        for (ItemEntity item : items) {
            double dx = holder.getX() - item.getX();
            double dy = holder.getY() + holder.getEyeHeight() / 2.0 - item.getY();
            double dz = holder.getZ() - item.getZ();
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

            // 一定距離内のみ引き寄せ
            if (distance < RADIUS && distance > 0.5) {
                double scale = PULL_STRENGTH / distance;
                item.setDeltaMovement(item.getDeltaMovement().add(dx * scale, dy * scale, dz * scale));
            }
        }
    }
}
