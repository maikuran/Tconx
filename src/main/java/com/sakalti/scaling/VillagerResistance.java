package com.sakalti.scaling;

import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public final class VillagerResistance {

    private static final double SEARCH_RADIUS = 64.0D;

    private VillagerResistance() {
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.WorldTickEvent event) {

        // ENDフェーズだけ処理
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        // クライアント側では処理しない
        if (!(event.world instanceof ServerWorld)) {
            return;
        }

        // 1秒ごとに更新
        if (event.world.getGameTime() % 20L != 0L) {
            return;
        }

        ServerWorld world = (ServerWorld) event.world;

        /*
         * 複数プレイヤーの検索範囲が重なった場合、
         * 同じ村人を何度も処理しない。
         */
        Set<VillagerEntity> processedVillagers = new HashSet<>();

        for (ServerPlayerEntity player : world.players()) {

            /*
             * プレイヤーから64ブロック以内だけ検索。
             * 元の巨大なワールド全体検索を廃止。
             */
            AxisAlignedBB area =
                    player.getBoundingBox().inflate(SEARCH_RADIUS);

            for (VillagerEntity villager :
                    world.getEntitiesOfClass(
                            VillagerEntity.class,
                            area,
                            villager -> true
                    )) {

                // すでに処理した村人ならスキップ
                if (!processedVillagers.add(villager)) {
                    continue;
                }

                /*
                 * Resistance V
                 *
                 * Amplifier 4 = レベルV
                 * 100 tick = 5秒
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
}
