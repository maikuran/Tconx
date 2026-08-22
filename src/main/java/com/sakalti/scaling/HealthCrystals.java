package com.sakalti.scaling;

import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public final class HealthCrystals {

    private HealthCrystals() {
    }

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

                        
                        public ActionResult<ItemStack> use(
                                World world,
                                PlayerEntity player,
                                Hand hand
                        ) {

                            ItemStack stack =
                                    player.getItemInHand(hand);

                            /*
                             * クライアントでは処理しない
                             */
                            if (!world.isClientSide) {

                                CompoundNBT data =
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
                                if (!player.abilities.instabuild) {
                                    stack.shrink(1);
                                }
                            }

                            return ActionResult.sidedSuccess(
                                    stack,
                                    world.isClientSide
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
            PlayerEntity player,
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

        private Events() {
        }

        @SubscribeEvent
        public static void onPlayerLogin(
                PlayerEvent.PlayerLoggedInEvent event
        ) {

            PlayerEntity player =
                    event.getPlayer();

            CompoundNBT data =
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
