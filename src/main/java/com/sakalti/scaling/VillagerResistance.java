package com.sakalti.scaling;

import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public final class VillagerResistance {

    private VillagerResistance() {
    }

    @SubscribeEvent
    public static void onLevelTick(
            TickEvent.WorldTickEvent event
    ) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (event.world.isClientSide()) {
            return;
        }

        /*
         * 1秒ごとに更新
         */
        if (event.world.getGameTime() % 20 != 0) {
            return;
        }

        /*
         * 現在のDimension内の広範囲を検索
         */
        AxisAlignedBB area = new AxisAlignedBB(
                -30000000,
                -2048,
                -30000000,
                30000000,
                2048,
                30000000
        );

        for (VillagerEntity villager :
                event.world.getEntitiesOfClass(
                        VillagerEntity.class,
                        area,
                        villager -> true
                )) {

            /*
             * Resistance V
             *
             * Amplifier 4 = レベルV
             * 100tick = 5秒
             */
            villager.addEffect(
                    new EffectInstance(
                            Effects.DAMAGE_RESISTANCE,
                            100,
                            4,
                            false,
                            false,
                            false
                    )
            );
        }
    }
}
