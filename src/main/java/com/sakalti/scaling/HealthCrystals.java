package com.sakalti.scaling;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public final class HealthCrystals {

    private HealthCrystals() {}

    public static final String MOD_ID = "sakalti";

    private static final String BONUS_TAG =
            "sakalti_health_crystal_bonus";

    /*
     * Health Crystal専用Modifier UUID
     */
    private static final UUID HEALTH_CRYSTAL_UUID =
            UUID.fromString(
                    "7c8c0e91-6c4d-4a9a-9f7a-8e0e8c6f3a21"
            );

    /*
     * =========================================================
     * Item Registry
     * =========================================================
     */

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(
                    ForgeRegistries.ITEMS,
                    MOD_ID
            );

    public static final RegistryObject<Item> HEALTH_CRYSTAL =
            ITEMS.register(
                    "health_crystal",
                    () -> new Item(
                            new Item.Properties()
                    ) {

                        @Override
                        public InteractionResultHolder<ItemStack> use(
                                Level level,
                                Player player,
                                InteractionHand hand
                        ) {

                            ItemStack stack =
                                    player.getItemInHand(hand);

                            /*
                             * クライアントでは処理しない
                             */
                            if (!level.isClientSide()) {

                                CompoundTag data =
                                        player.getPersistentData();

                                /*
                                 * 現在のHealth Crystalボーナス
                                 */
                                double bonus =
                                        data.getDouble(
                                                BONUS_TAG
                                        );

                                /*
                                 * 1個につき +1.5 HP
                                 */
                                bonus += 1.5D;

                                /*
                                 * 永続保存
                                 */
                                data.putDouble(
                                        BONUS_TAG,
                                        bonus
                                );

                                /*
                                 * AttributeModifierを更新
                                 */
                                applyHealthCrystalBonus(
                                        player,
                                        bonus
                                );

                                /*
                                 * 現在HPにも+1.5
                                 */
                                player.setHealth(
                                        Math.min(
                                                player.getHealth()
                                                        + 1.5F,
                                                player.getMaxHealth()
                                        )
                                );

                                /*
                                 * クリスタルを1個消費
                                 */
                                if (!player.getAbilities()
                                        .instabuild) {

                                    stack.shrink(1);
                                }
                            }

                            return InteractionResultHolder.sidedSuccess(
                                    stack,
                                    level.isClientSide()
                            );
                        }
                    }
            );


    /*
     * =========================================================
     * Health Crystal HP適用
     * =========================================================
     */

    private static void applyHealthCrystalBonus(
            Player player,
            double bonus
    ) {

        AttributeInstance attribute =
                player.getAttribute(
                        Attributes.MAX_HEALTH
                );

        if (attribute == null) {
            return;
        }

        /*
         * 既存のHealth Crystal Modifierを削除
         */
        AttributeModifier oldModifier =
                attribute.getModifier(
                        HEALTH_CRYSTAL_UUID
                );

        if (oldModifier != null) {
            attribute.removeModifier(
                    HEALTH_CRYSTAL_UUID
            );
        }

        /*
         * Health Crystal分だけ加算
         */
        AttributeModifier modifier =
                new AttributeModifier(
                        HEALTH_CRYSTAL_UUID,
                        "Health Crystal Bonus",
                        bonus,
                        AttributeModifier.Operation.ADDITION
                );

        attribute.addPermanentModifier(
                modifier
        );
    }


    /*
     * =========================================================
     * ログイン時に復元
     * =========================================================
     */

    @Mod.EventBusSubscriber(
            modid = MOD_ID,
            bus = Mod.EventBusSubscriber.Bus.FORGE
    )
    public static final class Events {

        private Events() {}

        @SubscribeEvent
        public static void onPlayerLogin(
                PlayerEvent.PlayerLoggedInEvent event
        ) {

            Player player =
                    event.getEntity();

            CompoundTag data =
                    player.getPersistentData();

            double bonus =
                    data.getDouble(
                            BONUS_TAG
                    );

            if (bonus > 0.0D) {

                applyHealthCrystalBonus(
                        player,
                        bonus
                );
            }
        }
    }


    /*
     * =========================================================
     * Registry
     * =========================================================
     */

    public static void register(
            IEventBus eventBus
    ) {
        ITEMS.register(eventBus);
    }
}
