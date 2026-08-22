package com.sakalti.modifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.List;

public class SuperMagnetModifier extends Modifier {

    public SuperMagnetModifier() {
        super(0xFF3300); // 磁石: レッド
    }

    private static final double RADIUS = 6.0D;
    private static final double PULL_STRENGTH = 0.5D;

    /**
     * インベントリ内にある毎フレーム処理 (1.16.5 TCon 3.x 仕様)
     */
    
    public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        // クライアント側、または手に持っていない（isSelected == false）場合はスキップ
        // isClientSide はメソッドではなくフィールド
        if (world == null || world.isClientSide || holder == null || !isSelected) {
            return;
        }

        AxisAlignedBB area = holder.getBoundingBox().inflate(RADIUS);

        // 拾い上げクールタイム中でないドロップアイテムを取得
        List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, area, item -> !item.hasPickUpDelay());

        for (ItemEntity item : items) {
            double dx = holder.getX() - item.getX();
            double dy = (holder.getY() + holder.getEyeHeight() / 2.0D) - item.getY();
            double dz = holder.getZ() - item.getZ();
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

            // 一定距離内のみ引き寄せ
            if (distance < RADIUS && distance > 0.5D) {
                double scale = (PULL_STRENGTH * level) / distance; // レベルに応じて引き寄せ速度調整
                item.setDeltaMovement(item.getDeltaMovement().add(dx * scale, dy * scale, dz * scale));
            }
        }
    }
}
