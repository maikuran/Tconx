package com.sakalti.scaling;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
     * 設定
     * =========================================================
     */

    // 1 Minecraft day = 24000 ticks
    private static final long TICKS_PER_DAY = 24000L;

    // 1 Levelごとの増加率
    // Level 1 = HP +10%, Damage +5%
    private static final double HEALTH_PER_LEVEL = 0.10D;
    private static final double DAMAGE_PER_LEVEL = 0.05D;

    // Scalingの現在Level
    private static int scalingLevel = 0;

    // 経過tick
    private static long scalingTicks = 0L;

    // ON/OFF
    private static boolean healthScaling = true;
    private static boolean damageScaling = true;

    /*
     * Mobごとの元HP
     *
     * 「元のHP × Scaling倍率」
     * にするために保存する。
     */
    private static final Map<UUID, Double> BASE_HEALTH =
            new HashMap<>();


    /*
     * =========================================================
     * Key Registry
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
     * Client Key Registry
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
     * D = Damage ON/OFF
     * H = Health ON/OFF
     *
     * この簡易版ではキー入力をローカルで切り替える。
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
     *
     * Mobが生成された時点のHPを記録する。
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

        BASE_HEALTH.put(
                mob.getUUID(),
                (double) mob.getMaxHealth()
        );

        /*
         * 現在のScaling Levelが0より大きい場合、
         * スポーン直後から現在Levelを適用する。
         */
        applyHealthScaling(mob);
    }


    /*
     * =========================================================
     * Server Tick
     * =========================================================
     *
     * 24000 tick = 1日
     *
     * 1日経過するたびにLevel +1
     */

    @SubscribeEvent
    public static void onServerTick(
            TickEvent.ServerTickEvent event
    ) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        scalingTicks++;

        if (scalingTicks >= TICKS_PER_DAY) {

            scalingTicks = 0L;

            scalingLevel++;

            System.out.println(
                    "[Sakalti Scaling] Scaling Level increased to "
                            + scalingLevel
            );
        }
    }


    /*
     * =========================================================
     * Level変更時のHP適用
     * =========================================================
     *
     * Levelが変わったタイミングで全ServerLevelのMobを更新する。
     */

    @SubscribeEvent
    public static void onLevelTick(
            TickEvent.LevelTickEvent event
    ) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (!(event.level instanceof ServerLevel level)) {
            return;
        }

        /*
         * 1日ごとのLevel変更を検出するための処理。
         *
         * 24000 tick単位で更新。
         */
        if (level.getGameTime() % TICKS_PER_DAY != 0) {
            return;
        }

        for (LivingEntity entity : level.getEntities()
                .getAll()
                .stream()
                .filter(e -> e instanceof Mob)
                .map(e -> (Mob) e)
                .toList()) {

            applyHealthScaling(entity);
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

        AttributeInstance attribute =
                mob.getAttribute(
                        Attributes.MAX_HEALTH
                );

        if (attribute == null) {
            return;
        }

        /*
         * 元HPがまだ登録されていなければ登録
         */
        double baseHealth =
                BASE_HEALTH.computeIfAbsent(
                        mob.getUUID(),
                        uuid -> (double) mob.getMaxHealth()
                );

        /*
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

        double oldHealth =
                mob.getHealth();

        double oldMaxHealth =
                attribute.getValue();

        attribute.setBaseValue(
                newMaxHealth
        );

        /*
         * HP増加分を現在HPにも反映
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
         * 敵Mobだけを対象にする。
         */
        if (!(event.getEntity() instanceof Mob)) {
            return;
        }

        float originalDamage =
                event.getAmount();

        /*
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
                originalDamage * multiplier
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
