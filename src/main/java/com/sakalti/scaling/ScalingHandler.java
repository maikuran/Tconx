package com.sakalti.scaling;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

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

    private static final long TICKS_PER_DAY = 24000L;

    private static final double HEALTH_PER_LEVEL = 0.10D;
    private static final double DAMAGE_PER_LEVEL = 0.05D;

    /*
     * Scaling Level
     *
     * サーバー側だけが変更する。
     */
    private static int scalingLevel = 0;

    /*
     * 経過tick
     */
    private static long scalingTicks = 0L;

    /*
     * Scaling ON/OFF
     *
     * サーバー側が本体。
     */
    private static boolean healthScaling = true;
    private static boolean damageScaling = true;

    /*
     * Mob本来の最大HP
     */
    private static final Map<UUID, Double> BASE_HEALTH =
            new HashMap<>();


    /*
     * =========================================================
     * Network
     * =========================================================
     */

    private static final String PROTOCOL_VERSION = "1";

    private static final SimpleChannel NETWORK =
            NetworkRegistry.newSimpleChannel(
                    new net.minecraft.resources.ResourceLocation(
                            "sakalti",
                            "scaling"
                    ),
                    () -> PROTOCOL_VERSION,
                    PROTOCOL_VERSION::equals,
                    PROTOCOL_VERSION::equals
            );

    private static int packetId = 0;


    /*
     * =========================================================
     * Packet
     * =========================================================
     *
     * CLIENT
     *   ↓
     * ToggleScalingPacket
     *   ↓
     * SERVER
     */

    public static final class ToggleScalingPacket {

        private final int type;

        /*
         * type
         *
         * 0 = Damage
         * 1 = Health
         */
        public ToggleScalingPacket(int type) {
            this.type = type;
        }

        public ToggleScalingPacket(FriendlyByteBuf buffer) {
            this.type = buffer.readInt();
        }

        public void encode(FriendlyByteBuf buffer) {
            buffer.writeInt(type);
        }

        public void handle(Supplier<NetworkEvent.Context> supplier) {

            NetworkEvent.Context context =
                    supplier.get();

            context.enqueueWork(() -> {

                ServerPlayer player =
                        context.getSender();

                /*
                 * 専用サーバー側でのみ変更
                 */
                if (player == null) {
                    return;
                }

                if (type == 0) {

                    damageScaling =
                            !damageScaling;

                    System.out.println(
                            "[Sakalti Scaling] Damage Scaling: "
                                    + damageScaling
                    );

                } else if (type == 1) {

                    healthScaling =
                            !healthScaling;

                    System.out.println(
                            "[Sakalti Scaling] Health Scaling: "
                                    + healthScaling
                    );

                    /*
                     * OFFにした場合は
                     * MobのHPを元に戻すのではなく、
                     * 現在の状態を維持。
                     *
                     * ONにした瞬間に全Mobへ再適用。
                     */
                    if (healthScaling) {

                        for (ServerLevel level :
                                player.getServer().getAllLevels()) {

                            for (var entity :
                                    level.getEntities().getAll()) {

                                if (entity instanceof Mob mob) {
                                    applyHealthScaling(mob);
                                }
                            }
                        }
                    }
                }
            });

            context.setPacketHandled(true);
        }
    }


    /*
     * =========================================================
     * Network Register
     * =========================================================
     */

    static {

        NETWORK.registerMessage(
                packetId++,
                ToggleScalingPacket.class,
                ToggleScalingPacket::encode,
                ToggleScalingPacket::new,
                ToggleScalingPacket::handle
        );
    }


    /*
     * =========================================================
     * Client KeyMapping
     * =========================================================
     *
     * Clientクラスを専用サーバーでロードしないように
     * ClientRegistry内に隔離。
     */

    @Mod.EventBusSubscriber(
            modid = "sakalti",
            value = Dist.CLIENT,
            bus = Mod.EventBusSubscriber.Bus.MOD
    )
    public static final class ClientRegistry {

        private ClientRegistry() {}

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
     * D
     * ↓
     * Damage Toggle Packet
     * ↓
     * Server
     *
     * H
     * ↓
     * Health Toggle Packet
     * ↓
     * Server
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

            if (ClientRegistry.DAMAGE_KEY.consumeClick()) {

                NETWORK.sendToServer(
                        new ToggleScalingPacket(0)
                );
            }

            if (ClientRegistry.HEALTH_KEY.consumeClick()) {

                NETWORK.sendToServer(
                        new ToggleScalingPacket(1)
                );
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

        /*
         * クライアントでは処理しない
         */
        if (event.getLevel().isClientSide()) {
            return;
        }

        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }

        /*
         * 元のHPを保存
         */
        BASE_HEALTH.put(
                mob.getUUID(),
                (double) mob.getMaxHealth()
        );

        /*
         * 現在のScaling Levelを適用
         */
        if (healthScaling) {
            applyHealthScaling(mob);
        }
    }


    /*
     * =========================================================
     * Server Tick
     * =========================================================
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
         * 24000tick = 1日
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
             * Health Scaling ONなら
             * 全ワールドのMobを更新
             */
            if (healthScaling) {

                for (ServerLevel level :
                        event.getServer().getAllLevels()) {

                    for (var entity :
                            level.getEntities().getAll()) {

                        if (entity instanceof Mob mob) {
                            applyHealthScaling(mob);
                        }
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
         * 元HP
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
         * 増加分を現在HPにも追加
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
         * Mobのみ
         */
        if (!(event.getEntity() instanceof Mob)) {
            return;
        }

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


    /*
     * =========================================================
     * Constructor
     * =========================================================
     */

    private ScalingHandler() {
    }
}
