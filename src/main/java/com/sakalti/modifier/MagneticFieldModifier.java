
package com.sakalti.modifier;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class MagneticFieldModifier extends Modifier {

    private static final ModifierId MODIFIER_ID =
            new ModifierId("sakalti", "magnetic_field");

    private static final double FIELD_RADIUS = 3.0D;

    public MagneticFieldModifier() {
        super();
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;

        if (player.level().isClientSide) return;
        if (!player.isAlive()) return;

        if (!hasMagneticField(player)) return;

        AABB field = player.getBoundingBox().inflate(FIELD_RADIUS);

        List<Projectile> projectiles =
                player.level().getEntitiesOfClass(
                        Projectile.class,
                        field,
                        projectile ->
                                projectile.isAlive()
                                && projectile.getOwner() != player
                                && isIncoming(player, projectile)
                );

        for (Projectile projectile : projectiles) {
            reflectProjectile(player, projectile);
        }
    }

    private static boolean hasMagneticField(Player player) {

        EquipmentSlot[] armorSlots = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        };

        for (EquipmentSlot slot : armorSlots) {

            ItemStack stack = player.getItemBySlot(slot);

            if (stack.isEmpty()) continue;

            ToolStack tool = ToolStack.from(stack);

            if (tool.getModifierLevel(MODIFIER_ID) > 0) {
                return true;
            }
        }

        return false;
    }

    private static boolean isIncoming(
            Player player,
            Projectile projectile
    ) {
        Vec3 velocity = projectile.getDeltaMovement();

        Vec3 directionToProjectile =
                projectile.position().subtract(player.position());

        // 停止中の飛翔体は対象外
        if (velocity.lengthSqr() < 0.0001D) {
            return false;
        }

        // プレイヤー方向へ移動している飛翔体だけ検知
        return velocity.dot(directionToProjectile) < 0.0D;
    }

    private static void reflectProjectile(
            Player player,
            Projectile projectile
    ) {
        Vec3 velocity = projectile.getDeltaMovement();

        if (velocity.lengthSqr() < 0.0001D) return;

        // 飛翔体の進行方向を反転
        projectile.setDeltaMovement(velocity.scale(-1.0D));

        // 反射後の所有者を装備者に変更
        projectile.setOwner(player);

        // 物理演算を更新
        projectile.hasImpulse = true;
        projectile.hurtMarked = true;
    }
}
