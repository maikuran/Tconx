package com.sakalti.scaling;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class HealthCrystals {

    private HealthCrystals() {}

    /*
     * =========================================================
     * Registry
     * =========================================================
     */

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(
                    ForgeRegistries.ITEMS,
                    "sakalti"
            );

    /*
     * Health Crystal
     */

    public static final RegistryObject<Item> HEALTH_CRYSTAL =
            ITEMS.register(
                    "health_crystal",
                    () -> new Item(
                            new Item.Properties()
                    ) {
                        @Override
                        public net.minecraft.world.InteractionResultHolder<ItemStack> use(
                                Level level,
                                Player player,
                                net.minecraft.world.InteractionHand hand
                        ) {

                            ItemStack stack =
                                    player.getItemInHand(hand);

                            /*
                             * 最大HP
                             */
                            AttributeInstance maxHealth =
                                    player.getAttribute(
                                            Attributes.MAX_HEALTH
                                    );

                            if (maxHealth == null) {
                                return net.minecraft.world.InteractionResultHolder.pass(
                                        stack
                                );
                            }

                            /*
                             * 最大HP +1.5
                             */
                            double newMaxHealth =
                                    maxHealth.getBaseValue()
                                            + 1.5D;

                            maxHealth.setBaseValue(
                                    newMaxHealth
                            );

                            /*
                             * 増加した1.5HPを
                             * 現在HPにも追加
                             */
                            player.setHealth(
                                    Math.min(
                                            player.getHealth()
                                                    + 1.5F,
                                            player.getMaxHealth()
                                    )
                            );

                            /*
                             * アイテムを1個消費
                             */
                            if (!player.getAbilities()
                                    .instabuild) {

                                stack.shrink(1);
                            }

                            return net.minecraft.world.InteractionResultHolder.sidedSuccess(
                                    stack,
                                    level.isClientSide()
                            );
                        }
                    }
            );


    /*
     * =========================================================
     * Registry登録
     * =========================================================
     *
     * ModMainから呼び出す。
     */

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
