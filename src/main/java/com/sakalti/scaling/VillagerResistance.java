package com.sakalti.scaling;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public final class VillagerResistance {

    private VillagerResistance() {}

    @SubscribeEvent
    public static void onLevelTick(
            TickEvent.LevelTickEvent event
    ) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (event.level.isClientSide()) {
            return;
        }

        /*
         * 1秒ごとに更新
         */
        if (event.level.getGameTime() % 20 != 0) {
            return;
        }

        /*
         * 現在のDimension内の広範囲を検索
         */
        AABB area = new AABB(
                -30000000,
                -2048,
                -30000000,
                30000000,
                2048,
                30000000
        );

        for (Villager villager :
                event.level.getEntities(
                        EntityTypeTest.forClass(Villager.class),
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
                    new MobEffectInstance(
                            MobEffects.DAMAGE_RESISTANCE,
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
