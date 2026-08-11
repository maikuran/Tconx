package com.sakalti.tconx.enchant;

import net.minecraft.world.item.ItemStack;
import com.sakalti.tconx.enchant.PierceThrustEnchantment;
import com.sakalti.tconx.enchant.HemorrhageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import com.sakalti.tconx.items.NuzwatItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// エンチャント登録
public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
        DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "tconx");

    public static final RegistryObject<Enchantment> NUZWAT_DAMAGE =
        ENCHANTMENTS.register("nuzwat_damage", NuzwatDamageEnchantment::new);

    public static final RegistryObject<Enchantment> NUZWAT_UNDEAD_BANE =
        ENCHANTMENTS.register("nuzwat_undead_bane", NuzwatUndeadBaneEnchantment::new);
    public static final RegistryObject<Enchantment> HEMORRHAGE =
        ENCHANTMENTS.register("mirzo_hemorrhage", () -> new HemorrhageEnchantment(Rarity.UNCOMMON));

    public static final RegistryObject<Enchantment> PIERCE_THRUST =
        ENCHANTMENTS.register("mirzo_pierce_thrust", () -> new PierceThrustEnchantment(Rarity.UNCOMMON));


    // ヌズワトダメ増加
    public static class NuzwatDamageEnchantment extends Enchantment {
        public NuzwatDamageEnchantment() {
            super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        }
        public boolean canApply(ItemStack stack) {
            return stack.getItem() instanceof NuzwatItem;
        }
    }

    // ヌズワトアンデット特効
    public static class NuzwatUndeadBaneEnchantment extends Enchantment {
        public NuzwatUndeadBaneEnchantment() {
            super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        }
        public boolean canApply(ItemStack stack) {
            return stack.getItem() instanceof NuzwatItem;
        }
    }
}
