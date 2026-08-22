package com.sakalti.scaling;

import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public final class CreeperRemoval {

    private CreeperRemoval() {
    }

    /*
     * 何tickごとに確認するか
     * 100tick = 5秒
     */
    private static final int CHECK_INTERVAL = 100;

    /*
     * プレイヤーから何ブロック以内を対象にするか
     */
    private static final double RANGE = 64.0D;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
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
