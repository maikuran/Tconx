package com.sakalti.scaling;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public final class CreeperRemoval {

    private CreeperRemoval() {}

    /*
     * 何tickごとに確認するか
     *
     * 100tick = 5秒
     */
    private static final int CHECK_INTERVAL = 100;

    /*
     * プレイヤーから何ブロック以内を対象にするか
     */
    private static final double RANGE = 64.0D;

    @SubscribeEvent
    public static void onServerTick(
            TickEvent.ServerTickEvent event
    ) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        /*
         * 5秒ごと
         */
        if (event.getServer().getTickCount()
                % CHECK_INTERVAL != 0) {
            return;
        }

        /*
         * 全Dimensionのプレイヤーを確認
         */
        for (ServerLevel level :
                event.getServer().getAllLevels()) {

            for (ServerPlayer player :
                    level.players()) {

                /*
                 * プレイヤー周辺のクリーパーを取得
                 */
                level.getEntities(
                        net.minecraft.world.level.entity.EntityTypeTest.forClass(
                                Creeper.class
                        ),
                        player.getBoundingBox().inflate(RANGE),
                        creeper -> true
                ).forEach(
                        creeper -> creeper.discard()
                );
            }
        }
    }
}
