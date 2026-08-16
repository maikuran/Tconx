package com.sakalti.items;

import com.sakalti.enchant.ModEnchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class MirzoItem extends Item {

    private final float baseDamage;
    private final float attackSpeed;
    private final int maxChargeTicks;
    private final Tier tier;

    public MirzoItem(float baseDamage, float attackSpeed, int maxChargeTicks,
                     int durability, Tier tier) {
        super(new Item.Properties().stacksTo(1).durability(durability));
        this.baseDamage = baseDamage;
        this.attackSpeed = attackSpeed;
        this.maxChargeTicks = maxChargeTicks;
        this.tier = tier;
    }

    public Tier getTier() {
        return tier;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return maxChargeTicks;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);

        player.getPersistentData().putDouble("mirzo_lastX", player.getX());
        player.getPersistentData().putDouble("mirzo_lastZ", player.getZ());
        player.getPersistentData().putDouble("mirzo_totalMove", 0.0);

        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;

        int used = this.getUseDuration(stack) - timeLeft;
        float progress = Math.min(1.0f, used / (float) maxChargeTicks);

        double prevX = player.getPersistentData().getDouble("mirzo_lastX");
        double prevZ = player.getPersistentData().getDouble("mirzo_lastZ");
        double dx = player.getX() - prevX;
        double dz = player.getZ() - prevZ;
        double distance = Math.sqrt(dx * dx + dz * dz);
        double totalMove = player.getPersistentData().getDouble("mirzo_totalMove") + distance;
        player.getPersistentData().putDouble("mirzo_totalMove", totalMove);

        double speedBonus = 1.0 + totalMove * 0.05;

        Vec3 start = player.getEyePosition(1.0f);
        Vec3 look = player.getLookAngle();
        AABB box = player.getBoundingBox().expandTowards(look.scale(4.0)).inflate(1.0);

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, box,
                e -> e != player && e.isAlive() && !e.isSpectator());

        LivingEntity target = null;
        double minDist = 4.0;
        for (LivingEntity e : targets) {
            Vec3 center = e.position().add(0, e.getBbHeight() / 2, 0);
            double dist = start.distanceTo(center);
            if (dist < minDist) {
                minDist = dist;
                target = e;
            }
        }

        if (target != null) {
            float finalDamage = (float)(baseDamage * (0.6 + 0.4 * progress) * speedBonus);

            int hemLevel = stack.getEnchantmentLevel(ModEnchantments.HEMORRHAGE.get());
            int pierceLevel = stack.getEnchantmentLevel(ModEnchantments.PIERCE_THRUST.get());

            if (pierceLevel > 0) {
                finalDamage *= (1.0f + 0.15f * pierceLevel);
            }

            target.hurt(level.damageSources().playerAttack(player), finalDamage);

            if (hemLevel > 0) {
                target.addEffect(new MobEffectInstance(
                        MobEffects.POISON,
                        20 * hemLevel,
                        0,
                        false,
                        true
                ));
            }

            stack.hurtAndBreak(1, player,
                    p -> p.broadcastBreakEvent(p.getUsedItemHand()));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level,
                                List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§7長槍・突き専用"));
        tooltip.add(Component.literal("§bリーチ: 4m"));
        tooltip.add(Component.literal("§e右クリック長押し: 溜め突き"));
        tooltip.add(Component.literal("§8Tier: " + tier.toString()));
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment ench) {
        ResourceLocation key = ForgeRegistries.ENCHANTMENTS.getKey(ench);

        if (key == null) return false;

        String ns = key.getNamespace();
        String path = key.getPath();

        // sakalti:mirzo_* 専用エンチャント
        if (ns.equals("sakalti") && path.startsWith("mirzo_")) return true;

        // バニラ攻撃系・ユーティリティ系
        if (ns.equals("minecraft")) {
            if (path.equals("sharpness") ||
                path.equals("smite") ||
                path.equals("bane_of_arthropods") ||
                path.equals("knockback") ||
                path.equals("fire_aspect") ||
                path.equals("looting") ||
                path.equals("sweeping") ||
                path.equals("unbreaking") ||
                path.equals("mending") ||
                path.equals("vanishing_curse")) return true;
        }

        return super.canApplyAtEnchantingTable(stack, ench);
    }
}
