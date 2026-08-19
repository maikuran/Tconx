package com.sakalti.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Hoglin;
import net.minecraft.world.entity.monster.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.Difficulty;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumSet;

public class CrimsonFlyEntity extends Mob {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(
                    ForgeRegistries.ENTITY_TYPES,
                    "sakalti"
            );

    public static final RegistryObject<EntityType<CrimsonFlyEntity>> CRIMSON_FLY =
            ENTITIES.register("crimson_fly",
                    () -> EntityType.Builder.of(
                                    CrimsonFlyEntity::new,
                                    MobCategory.MONSTER
                            )
                            .sized(0.8F, 0.8F)
                            .clientTrackingRange(8)
                            .updateInterval(3)
                            .build("sakalti:crimson_fly")
            );

    public CrimsonFlyEntity(
            EntityType<? extends CrimsonFlyEntity> type,
            Level level
    ) {
        super(type, level);

        this.moveControl = new FlyingMoveControl(this, 20, true);

        // 飛行するため重力を無効化
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.FLYING_SPEED, 0.40D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void registerGoals() {

        // 最優先：高い場所へ移動
        this.goalSelector.addGoal(
                1,
                new SeekHigherGroundGoal(this)
        );

        // 攻撃
        this.goalSelector.addGoal(
                2,
                new MeleeAttackGoal(this, 1.2D, true)
        );

        // 視線
        this.goalSelector.addGoal(
                8,
                new LookAtPlayerGoal(
                        this,
                        Player.class,
                        8.0F
                )
        );

        this.goalSelector.addGoal(
                9,
                new RandomLookAroundGoal(this)
        );

        // 攻撃されたら反撃
        this.targetSelector.addGoal(
                1,
                new HurtByTargetGoal(this)
        );

        // ピグリンを敵対
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(
                        this,
                        Piglin.class,
                        true
                )
        );

        // ホグリンを敵対
        this.targetSelector.addGoal(
                3,
                new NearestAttackableTargetGoal<>(
                        this,
                        Hoglin.class,
                        true
                )
        );
    }

    /**
     * 難易度による攻撃力
     *
     * Easy   = 4
     * Normal = 6
     * Hard   = 9
     */
    public float getCrimsonFlyAttackDamage() {

        Difficulty difficulty = this.level().getDifficulty();

        return switch (difficulty) {
            case EASY -> 4.0F;
            case HARD -> 9.0F;
            default -> 6.0F;
        };
    }

    /**
     * 高い場所へ向かうAI
     */
    public static class SeekHigherGroundGoal extends Goal {

        private final CrimsonFlyEntity fly;

        private BlockPos targetPos;
        private int cooldown;

        public SeekHigherGroundGoal(CrimsonFlyEntity fly) {
            this.fly = fly;

            this.setFlags(
                    EnumSet.of(Flag.MOVE)
            );
        }

        @Override
        public boolean canUse() {

            if (this.cooldown > 0) {
                this.cooldown--;
                return false;
            }

            this.cooldown = 20;

            BlockPos current =
                    this.fly.blockPosition();

            for (int i = 0; i < 16; i++) {

                int x =
                        current.getX()
                                + this.fly.getRandom()
                                .nextInt(21)
                                - 10;

                int y =
                        current.getY()
                                + 3
                                + this.fly.getRandom()
                                .nextInt(12);

                int z =
                        current.getZ()
                                + this.fly.getRandom()
                                .nextInt(21)
                                - 10;

                BlockPos candidate =
                        new BlockPos(x, y, z);

                // 現在位置より高い場所だけ
                if (candidate.getY()
                        <= current.getY()) {
                    continue;
                }

                // 空間が確保されている場所
                if (this.fly.level().isEmptyBlock(candidate)
                        && this.fly.level().isEmptyBlock(
                                candidate.above()
                )) {

                    this.targetPos = candidate;

                    return true;
                }
            }

            return false;
        }

        @Override
        public boolean canContinueToUse() {

            if (this.targetPos == null) {
                return false;
            }

            if (this.fly.distanceToSqr(
                    this.targetPos.getX() + 0.5D,
                    this.targetPos.getY() + 0.5D,
                    this.targetPos.getZ() + 0.5D
            ) <= 4.0D) {
                return false;
            }

            return true;
        }

        @Override
        public void start() {

            if (this.targetPos == null) {
                return;
            }

            this.fly.getNavigation().moveTo(
                    this.targetPos.getX() + 0.5D,
                    this.targetPos.getY() + 0.5D,
                    this.targetPos.getZ() + 0.5D,
                    1.0D
            );
        }

        @Override
        public void stop() {

            this.targetPos = null;

            this.fly.getNavigation().stop();
        }
    }

    /**
     * Entity属性登録
     */
    @Mod.EventBusSubscriber(
            modid = "sakalti",
            bus = Mod.EventBusSubscriber.Bus.MOD
    )
    public static class AttributeHandler {

        @SubscribeEvent
        public static void registerAttributes(
                EntityAttributeCreationEvent event
        ) {
            event.put(
                    CRIMSON_FLY.get(),
                    CrimsonFlyEntity.createAttributes()
                            .build()
            );
        }
    }

    /**
     * メインMODクラスから呼び出して登録する
     */
    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
