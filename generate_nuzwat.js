const fs = require("fs");
const path = require("path");

// 素材情報
const materials = [
    {name: "wooden", display: "Wood", tier: "Tiers.WOOD", damage: 1.9, speed: 3.5, craft: "minecraft:oak_planks"},
    {name: "stone", display: "Stone", tier: "Tiers.STONE", damage: 2.0, speed: 4.2, craft: "minecraft:stone"},
    {name: "iron", display: "Iron", tier: "Tiers.IRON", damage: 2.1, speed: 4.9, craft: "minecraft:iron_ingot"},
    {name: "golden", display: "Gold", tier: "Tiers.GOLD", damage: 2.7, speed: 3.5, craft: "minecraft:gold_ingot"},
    {name: "diamond", display: "Diamond", tier: "Tiers.DIAMOND", damage: 2.2, speed: 5.6, craft: "minecraft:diamond"},
    {name: "netherite", display: "Netherite", tier: "Tiers.NETHERITE", damage: 2.3, speed: 6.3, craft: "minecraft:netherite_ingot"}
];

// ディレクトリ設定
const javaItemDir = path.join(__dirname, "src/main/java/com/sakalti/sakalti/items");
const javaRegistryDir = path.join(__dirname, "src/main/java/com/sakalti/sakalti/registry");
const recipeDir = path.join(__dirname, "src/main/resources/data/sakalti/recipes");

// ディレクトリ作成
fs.mkdirSync(javaItemDir, { recursive: true });
fs.mkdirSync(javaRegistryDir, { recursive: true });
fs.mkdirSync(recipeDir, { recursive: true });

// 1️⃣ NuzwatItem.java
const nuzwatItemContent = `package com.sakalti.items;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;

public class NuzwatItem extends SwordItem {

    public NuzwatItem(Tier tier, float attackDamage, float attackSpeed, Item.Properties properties) {
        super(tier, (int) attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0));
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (!level.isClientSide() && entity instanceof Player player && selected) {
            BlockPos pos = player.blockPosition();
            level.getChunk(pos).setBlockLightValue(LightLayer.BLOCK, pos, 14);
        }
    }
}
`;

fs.writeFileSync(path.join(javaItemDir, "NuzwatItem.java"), nuzwatItemContent);
console.log("NuzwatItem.java を生成しました");

// 2️⃣ ModNuzwats.java
let javaRegistryContent = `package com.sakalti.registry;

import com.sakalti.items.NuzwatItem;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModNuzwats {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "sakalti");
`;

materials.forEach(mat => {
    javaRegistryContent += `
    public static final RegistryObject<Item> ${mat.name.toUpperCase()}_NUZWAT = ITEMS.register("${mat.name}_nuzwat",
        () -> new NuzwatItem(${mat.tier}, ${mat.damage}f, ${mat.speed}f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
`;
});

javaRegistryContent += `\n}`;
fs.writeFileSync(path.join(javaRegistryDir, "ModNuzwats.java"), javaRegistryContent);
console.log("ModNuzwats.java を生成しました");

// 3️⃣ クラフトレシピ JSON
materials.forEach(mat => {
    const recipe = {
        type: "minecraft:crafting_shaped",
        pattern: [" A ", " C ", " B "],
        key: {
            A: { item: mat.craft },
            B: { item: "minecraft:stick" },
            C: { item: "minecraft:amethyst_shard" }
        },
        result: { item: `sakalti:${mat.name}_nuzwat` }
    };
    fs.writeFileSync(path.join(recipeDir, `${mat.name}_nuzwat.json`), JSON.stringify(recipe, null, 4));
    console.log(`${mat.name}_nuzwat.json を生成しました`);
});

console.log("すべてのヌズワト素材とレシピを生成しました！");
