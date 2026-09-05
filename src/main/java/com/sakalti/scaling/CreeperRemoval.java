package com.sakalti.scaling;

import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
public final class CreeperRemoval {

    /*
     * 100tick = 5秒
     */
    private static final int CHECK_INTERVAL = 100;

    /*
     * プレイヤーから64ブロック以内
     */
    private static final double RANGE = 64.0D;

    private CreeperRemoval() {
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {

        /*
         * ENDフェーズのみ処理
         */
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        /*
         * サーバー側のみ
         */
        if (!(event.world instanceof ServerWorld)) {
            return;
        }

        ServerWorld world = (ServerWorld) event.world;

        /*
         * 5秒ごとに実行
         */
        if (world.getGameTime() % CHECK_INTERVAL != 0L) {
            return;
        }

        /*
         * 複数プレイヤーの範囲が重なった場合、
         * 同じクリーパーを二重処理しない。
         */
        Set<CreeperEntity> processedCreepers = new HashSet<>();

        for (ServerPlayerEntity player : world.players()) {

            /*
             * プレイヤー周辺64ブロック
             */
            AxisAlignedBB box = player
                    .getBoundingBox()
                    .inflate(RANGE);

            /*
             * 範囲内のクリーパーを取得
             */
            for (CreeperEntity creeper :
                    world.getEntitiesOfClass(
                            CreeperEntity.class,
                            box,
                            creeper -> true
                    )) {

                /*
                 * すでに処理済みならスキップ
                 */
                if (!processedCreepers.add(creeper)) {
                    continue;
                }

                /*
                 * クリーパーを削除
                 */
                creeper.remove();
            }
        }
    }
}
        // クライアント側ではなく、サーバー側のワールドでのみ処理する
        if (!(event.world instanceof ServerWorld)) {
            return;
        }

        ServerWorld world = (ServerWorld) event.world;

        /*
         * 5秒ごと（ゲームタイムを基準に判定）
         */
        if (world.getGameTime() % CHECK_INTERVAL != 0) {
            return;
        }

        for (ServerPlayerEntity player : world.players()) {

            /*
             * プレイヤー周辺64ブロックの範囲
             */
            AxisAlignedBB box = player
                    .getBoundingBox()
                    .inflate(RANGE);

            /*
             * 範囲内のクリーパーを取得して削除
             */
            world.getEntitiesOfClass(
                    CreeperEntity.class,
                    box,
                    creeper -> true
            ).forEach(
                    creeper -> creeper.remove()
            );
        }
    }
}
