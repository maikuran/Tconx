package com.sakalti.modifier;

import java.util.UUID;
import java.util.function.BiConsumer;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class ToughModifier extends Modifier implements AttributesModifierHook {

    private static final double ARMOR_BONUS = 0.5D;

    // 頭
    private static final UUID HEAD_UUID =
            UUID.fromString("7c2a91e4-6f3b-4d85-9a12-8e5c3b7f1046");

    // 胴
    private static final UUID CHEST_UUID =
            UUID.fromString("a83f5d21-9c47-4e6b-b812-3d7a6f905c24");

    // 脚
    private static final UUID LEGS_UUID =
            UUID.fromString("d19b7e53-2a84-4c61-8f35-b6e9207a4d18");

    // 足
    private static final UUID FEET_UUID =
            UUID.fromString("4e6c2a90-b153-47d8-a921-5f3b8c6d2074");

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES);
    }

    public void addAttributes(
            IToolStackView tool,
            ModifierEntry modifier,
            EquipmentSlot slot,
            BiConsumer<Attribute, AttributeModifier> consumer) {

        UUID uuid;

        switch (slot) {
            case HEAD:
                uuid = HEAD_UUID;
                break;

            case CHEST:
                uuid = CHEST_UUID;
                break;

            case LEGS:
                uuid = LEGS_UUID;
                break;

            case FEET:
                uuid = FEET_UUID;
                break;

            default:
                return;
        }

        consumer.accept(
                Attributes.ARMOR,
                new AttributeModifier(
                        uuid,
                        "sakalti.tough.armor",
                        ARMOR_BONUS,
                        AttributeModifier.Operation.ADDITION
                )
        );
    }
}
