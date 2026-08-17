package com.sakalti.scaling;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public final class ScalingHandler {

    /*
     * =========================================================
     * 基本設定
     * =========================================================
     */

    // Minecraftの1日は24000tick
    private static final long TICKS_PER_DAY = 24000L;

    // 1日ごとの上昇率
    private static final double HEALTH_PER_LEVEL = 0.10D;
    private static final double DAMAGE_PER_LEVEL = 0.05D;

    // 現在のScaling Level
    private static int scalingLevel = 0;

    // 現在の日数を計測するtick
    private static long scalingTicks = 0L;

    // Scaling ON/OFF
    private static boolean healthScaling = true;
    private static boolean damageScaling = true;

    /*
     * Mob本来の最大HPを保存
     */
    private static final Map<UUID, Double> BASE_HEALTH =
            new HashMap<>();


    /*
     * =========================================================
     * KeyMapping
     * =========================================================
     */

    public static final KeyMapping DAMAGE_KEY =
            new KeyMapping(
                    "key.sakalti.scaling_damage",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_D,
                    "key.categories.sakalti"
            );

    public static final KeyMapping HEALTH_KEY =
            new KeyMapping(
                    "key.sakalti.scaling_health",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_H,
                    "key.categories.sakalti"
            );


    /*
     * =========================================================
     * Key Registry
     * =========================================================
     */

    @Mod.EventBusSubscriber(
            modid = "sakalti",
            value = Dist.CLIENT,
            bus = Mod.EventBusSubscriber.Bus.MOD
    )
    public static final class ClientRegistry {

        private ClientRegistry() {}

        @SubscribeEvent
        public static void registerKeys(
                RegisterKeyMappingsEvent event
        ) {
            event.register(DAMAGE_KEY);
            event.register(HEALTH_KEY);
        }
    }


    /*
     * =========================================================
     * Client Tick
     * =========================================================
     *
     * D = Damage Scaling ON/OFF
     * H = Health Scaling ON/OFF
     */

    @Mod.EventBusSubscriber(
            modid = "sakalti",
            value = Dist.CLIENT,
            bus = Mod.EventBusSubscriber.Bus.FORGE
    )
    public static final class ClientTick {

        private ClientTick() {}

        @SubscribeEvent
        public static void onClientTick(
                TickEvent.ClientTickEvent event
        ) {
            if (event.phase != TickEvent.Phase.END) {
                return;
            }

            if (DAMAGE_KEY.consumeClick()) {
                damageScaling = !damageScaling;
            }

            if (HEALTH_KEY.consumeClick()) {
                healthScaling = !healthScaling;
            }
        }
    }


    /*
     * =========================================================
     * Mob Spawn
     * =========================================================
     */

    @SubscribeEvent
    public static void onEntityJoin(
            EntityJoinLevelEvent event
    ) {

        if (event.getLevel().isClientSide()) {
            return;
        }

        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }

        /*
         * Mob本来のHPを保存
         */
        BASE_HEALTH.put(
                mob.getUUID(),
                (double) mob.getMaxHealth()
        );

        /*
         * 現在Levelが0より大きければ
         * 現在のScalingを即座に適用
         */
        if (healthScaling) {
            applyHealthScaling(mob);
        }
    }


    /*
     * =========================================================
     * Server Tick
     * =========================================================
     *
     * 24000tick = 1日
     *
     * 1日経過
     * ↓
     * Scaling Level +1
     * ↓
     * 全DimensionのMobを更新
     */

    @SubscribeEvent
    public static void onServerTick(
            TickEvent.ServerTickEvent event
    ) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        scalingTicks++;

        /*
         * 1日経過
         */
        if (scalingTicks >= TICKS_PER_DAY) {

            scalingTicks = 0L;

            scalingLevel++;

            System.out.println(
                    "[Sakalti Scaling] "
                            + "Scaling Level increased to "
                            + scalingLevel
            );

            /*
             * Health ScalingがONなら
             * 全ServerLevelのMobを更新
             */
            if (healthScaling) {

                for (ServerLevel level :
                        event.getServer().getAllLevels()) {

                    for (Mob mob :
                            level.getEntities()
                                    .getAll()
                                    .stream()
                                    .filter(entity ->
                                            entity instanceof Mob
                                    )
                                    .map(entity ->
                                            (Mob) entity
                                    )
                                    .toList()) {

                        applyHealthScaling(mob);
                    }
                }
            }
        }
    }


    /*
     * =========================================================
     * Health Scaling
     * =========================================================
     */

    private static void applyHealthScaling(
            Mob mob
    ) {

        if (!healthScaling) {
            return;
        }

        AttributeInstance maxHealth =
                mob.getAttribute(
                        Attributes.MAX_HEALTH
                );

        if (maxHealth == null) {
            return;
        }

        /*
         * 元HPを取得
         */
        double baseHealth =
                BASE_HEALTH.computeIfAbsent(
                        mob.getUUID(),
                        uuid -> (double) mob.getMaxHealth()
                );

        /*
         * Levelごとの倍率
         *
         * Level 0 = ×1.00
         * Level 1 = ×1.10
         * Level 2 = ×1.20
         * Level 3 = ×1.30
         */

        double multiplier =
                1.0D
                        + (scalingLevel * HEALTH_PER_LEVEL);

        double newMaxHealth =
                baseHealth * multiplier;

        double oldMaxHealth =
                maxHealth.getValue();

        float oldHealth =
                mob.getHealth();

        /*
         * 最大HP変更
         */
        maxHealth.setBaseValue(
                newMaxHealth
        );

        /*
         * 増加した分だけ現在HPにも追加
         */
        if (newMaxHealth > oldMaxHealth) {

            double difference =
                    newMaxHealth - oldMaxHealth;

            mob.setHealth(
                    (float) Math.min(
                            oldHealth + difference,
                            newMaxHealth
                    )
            );
        }
    }


    /*
     * =========================================================
     * Damage Scaling
     * =========================================================
     */

    @SubscribeEvent
    public static void onLivingHurt(
            LivingHurtEvent event
    ) {

        if (!damageScaling) {
            return;
        }

        /*
         * Mobだけ対象
         */
        if (!(event.getEntity() instanceof Mob)) {
            return;
        }

        /*
         * Levelごとのダメージ倍率
         *
         * Level 0 = ×1.00
         * Level 1 = ×1.05
         * Level 2 = ×1.10
         * Level 3 = ×1.15
         */

        float multiplier =
                (float) (
                        1.0D
                                + (scalingLevel * DAMAGE_PER_LEVEL)
                );

        event.setAmount(
                event.getAmount() * multiplier
        );
    }


    /*
     * =========================================================
     * Getter
     * =========================================================
     */

    public static int getScalingLevel() {
        return scalingLevel;
    }

    public static boolean isHealthScalingEnabled() {
        return healthScaling;
    }

    public static boolean isDamageScalingEnabled() {
        return damageScaling;
    }
}
