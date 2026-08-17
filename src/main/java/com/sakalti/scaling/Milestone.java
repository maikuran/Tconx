package com.sakalti.scaling;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber(
        modid = "sakalti",
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class Milestone extends SavedData {

    private static final String DATA_NAME =
            "sakalti_milestone";

    private static final int BASE_REQUIRED_KILLS = 10;

    /*
     * HP +1するたびに、
     * 次の必要撃破数が5増える
     */
    private static final int REQUIRED_INCREASE = 5;

    /*
     * 不定期報酬の確率
     */
    private static final double REWARD_CHANCE = 0.25D;

    private final Map<UUID, PlayerData> players =
            new HashMap<>();

    private static final Random RANDOM =
            new Random();


    /*
     * =========================================================
     * SavedData取得
     * =========================================================
     */

    public static Milestone get(ServerLevel level) {

        return level.getDataStorage().computeIfAbsent(
                Milestone::load,
                Milestone::new,
                DATA_NAME
        );
    }


    /*
     * =========================================================
     * 読み込み
     * =========================================================
     */

    public static Milestone load(
            CompoundTag tag
    ) {

        Milestone data =
                new Milestone();

        CompoundTag playersTag =
                tag.getCompound("Players");

        for (String uuidString :
                playersTag.getAllKeys()) {

            UUID uuid;

            try {
                uuid = UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                continue;
            }

            CompoundTag playerTag =
                    playersTag.getCompound(uuidString);

            PlayerData playerData =
                    new PlayerData();

            playerData.experience =
                    playerTag.getInt("Experience");

            playerData.requiredExperience =
                    playerTag.getInt("RequiredExperience");

            playerData.bonusHealth =
                    playerTag.getInt("BonusHealth");

            data.players.put(
                    uuid,
                    playerData
            );
        }

        return data;
    }


    /*
     * =========================================================
     * 保存
     * =========================================================
     */

    @Override
    public CompoundTag save(
            CompoundTag tag
    ) {

        CompoundTag playersTag =
                new CompoundTag();

        for (Map.Entry<UUID, PlayerData> entry :
                players.entrySet()) {

            CompoundTag playerTag =
                    new CompoundTag();

            PlayerData data =
                    entry.getValue();

            playerTag.putInt(
                    "Experience",
                    data.experience
            );

            playerTag.putInt(
                    "RequiredExperience",
                    data.requiredExperience
            );

            playerTag.putInt(
                    "BonusHealth",
                    data.bonusHealth
            );

            playersTag.put(
                    entry.getKey().toString(),
                    playerTag
            );
        }

        tag.put(
                "Players",
                playersTag
        );

        return tag;
    }


    /*
     * =========================================================
     * プレイヤー参加時
     * =========================================================
     *
     * 保存されていたHPボーナスを再適用
     */

    @SubscribeEvent
    public static void onPlayerLogin(
            net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event
    ) {

        if (!(event.getEntity()
                instanceof ServerPlayer player)) {
            return;
        }

        ServerLevel level =
                (ServerLevel) player.level();

        Milestone milestone =
                get(level);

        PlayerData data =
                milestone.players.computeIfAbsent(
                        player.getUUID(),
                        uuid -> new PlayerData()
                );

        milestone.applyHealth(
                player,
                data
        );
    }


    /*
     * =========================================================
     * Mob死亡
     * =========================================================
     */

    @SubscribeEvent
    public static void onLivingDeath(
            LivingDeathEvent event
    ) {

        /*
         * キラーを取得
         */
        if (!(event.getSource().getEntity()
                instanceof ServerPlayer player)) {
            return;
        }

        LivingEntity killed =
                event.getEntity();

        /*
         * プレイヤー同士の殺害はMilestone対象外
         */
        if (killed instanceof ServerPlayer) {
            return;
        }

        ServerLevel level =
                (ServerLevel) player.level();

        Milestone milestone =
                get(level);

        PlayerData data =
                milestone.players.computeIfAbsent(
                        player.getUUID(),
                        uuid -> new PlayerData()
                );

        /*
         * 1体撃破 = 1経験
         *
         * 敵Mob・友好Mobの両方が対象
         */
        data.experience++;

        /*
         * Milestone到達
         */
        if (data.experience >=
                data.requiredExperience) {

            data.experience = 0;

            /*
             * HP +1
             */
            data.bonusHealth++;

            /*
             * 次の必要撃破数増加
             */
            data.requiredExperience +=
                    REQUIRED_INCREASE;

            /*
             * HP反映
             */
            milestone.applyHealth(
                    player,
                    data
            );

            /*
             * 保存フラグ
             */
            milestone.setDirty();

            /*
             * チャット通知
             */
            player.sendSystemMessage(
                    Component.literal(
                            "[Milestone] "
                                    + "HP +1！ "
                                    + "現在のHPボーナス: "
                                    + data.bonusHealth
                    )
            );

            player.sendSystemMessage(
                    Component.literal(
                            "[Milestone] "
                                    + "次のHP +1まで "
                                    + data.requiredExperience
                                    + "体"
                    )
            );

            /*
             * 不定期報酬
             */
            giveRandomReward(
                    player
            );
        } else {

            /*
             * 経験値が増えたので保存
             */
            milestone.setDirty();
        }
    }


    /*
     * =========================================================
     * HP適用
     * =========================================================
     */

    private void applyHealth(
            ServerPlayer player,
            PlayerData data
    ) {

        var attribute =
                player.getAttribute(
                        net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH
                );

        if (attribute == null) {
            return;
        }

        /*
         * バニラ基本HP20 + Milestone HP
         */
        double newMaxHealth =
                20.0D + data.bonusHealth;

        double oldMaxHealth =
                attribute.getBaseValue();

        attribute.setBaseValue(
                newMaxHealth
        );

        /*
         * 増えたHPを現在HPにも反映
         */
        if (newMaxHealth > oldMaxHealth) {

            float newHealth =
                    player.getHealth()
                            + (float)(
                                    newMaxHealth
                                            - oldMaxHealth
                            );

            player.setHealth(
                    Math.min(
                            newHealth,
                            (float)newMaxHealth
                    )
            );
        }
    }


    /*
     * =========================================================
     * 不定期報酬
     * =========================================================
     *
     * ダイヤと鉄を独立して判定。
     *
     * どちらも25%の確率。
     * 報酬量は1～3。
     */

    private static void giveRandomReward(
            ServerPlayer player
    ) {

        /*
         * ダイヤ
         */
        if (RANDOM.nextDouble()
                < REWARD_CHANCE) {

            int amount =
                    1 + RANDOM.nextInt(3);

            giveItem(
                    player,
                    new ItemStack(
                            Items.DIAMOND,
                            amount
                    )
            );

            player.sendSystemMessage(
                    Component.literal(
                            "[Milestone] "
                                    + "不定期報酬："
                                    + "ダイヤモンド ×"
                                    + amount
                    )
            );
        }

        /*
         * 鉄
         */
        if (RANDOM.nextDouble()
                < REWARD_CHANCE) {

            int amount =
                    1 + RANDOM.nextInt(3);

            giveItem(
                    player,
                    new ItemStack(
                            Items.IRON_INGOT,
                            amount
                    )
            );

            player.sendSystemMessage(
                    Component.literal(
                            "[Milestone] "
                                    + "不定期報酬："
                                    + "鉄インゴット ×"
                                    + amount
                    )
            );
        }
    }


    /*
     * =========================================================
     * アイテム付与
     * =========================================================
     */

    private static void giveItem(
            ServerPlayer player,
            ItemStack stack
    ) {

        if (!player.getInventory()
                .add(stack)) {

            /*
             * インベントリが満杯なら
             * プレイヤーの足元に落とす
             */
            player.drop(
                    stack,
                    false
            );
        }
    }


    /*
     * =========================================================
     * PlayerData
     * =========================================================
     */

    private static class PlayerData {

        /*
         * 現在の撃破経験
         */
        int experience = 0;

        /*
         * 次のMilestoneまで必要な撃破数
         */
        int requiredExperience =
                BASE_REQUIRED_KILLS;

        /*
         * 恒久HPボーナス
         */
        int bonusHealth = 0;
    }
}
