package com.sakalti.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Hoglin;
import net.minecraft.world.entity.monster.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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
            ENTITIES.register(
                    "crimson_fly",
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

        this.moveControl =
                new FlyingMoveControl(this, 20, true);

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

        // 高い場所を優先
        this.goalSelector.addGoal(
                1,
                new SeekHigherGroundGoal(this)
        );

        // 専用攻撃Goal
        this.goalSelector.addGoal(
                2,
                new CrimsonFlyMeleeAttackGoal(this)
        );

        // プレイヤーを見る
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

        // ピグリン
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(
                        this,
                        Piglin.class,
                        true
                )
        );

        // ホグリン
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
     * 難易度別攻撃力
     */
    public float getCrimsonFlyAttackDamage() {

        return switch (this.level().getDifficulty()) {
            case EASY -> 4.0F;
            case HARD -> 9.0F;
            default -> 6.0F;
        };
    }

    /**
     * 高所へ移動するAI
     */
    public static class SeekHigherGroundGoal extends Goal {

        private final CrimsonFlyEntity fly;

        private BlockPos targetPos;
        private int cooldown;

        public SeekHigherGroundGoal(
                CrimsonFlyEntity fly
        ) {
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

                if (candidate.getY()
                        <= current.getY()) {
                    continue;
                }

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

            double distance =
                    this.fly.distanceToSqr(
                            this.targetPos.getX() + 0.5D,
                            this.targetPos.getY() + 0.5D,
                            this.targetPos.getZ() + 0.5D
                    );

            return distance > 4.0D;
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
     * Crimson Fly専用近接攻撃Goal
     */
    public static class CrimsonFlyMeleeAttackGoal
            extends Goal {

        private final CrimsonFlyEntity fly;

        private int attackCooldown;

        public CrimsonFlyMeleeAttackGoal(
                CrimsonFlyEntity fly
        ) {
            this.fly = fly;

            this.setFlags(
                    EnumSet.of(
                            Flag.MOVE,
                            Flag.LOOK
                    )
            );
        }

        @Override
        public boolean canUse() {

            LivingEntity target =
                    this.fly.getTarget();

            return target != null
                    && target.isAlive();
        }

        @Override
        public boolean canContinueToUse() {

            LivingEntity target =
                    this.fly.getTarget();

            return target != null
                    && target.isAlive();
        }

        @Override
        public void start() {
            this.attackCooldown = 0;
        }

        @Override
        public void tick() {

            LivingEntity target =
                    this.fly.getTarget();

            if (target == null) {
                return;
            }

            this.fly.getLookControl().setLookAt(
                    target,
                    30.0F,
                    30.0F
            );

            double distance =
                    this.fly.distanceToSqr(target);

            // ターゲットへ接近
            if (distance > 3.0D) {

                this.fly.getNavigation().moveTo(
                        target,
                        1.2D
                );

            } else {

                this.fly.getNavigation().stop();

                if (this.attackCooldown > 0) {
                    this.attackCooldown--;
                    return;
                }

                this.attackCooldown = 20;

                // 実際の攻撃
                float damage =
                        this.fly.getCrimsonFlyAttackDamage();

                target.hurt(
                        this.fly.damageSources().mobAttack(this.fly),
                        damage
                );

                // 攻撃アニメーション
                this.fly.swing(
                        net.minecraft.world.InteractionHand.MAIN_HAND
                );
            }
        }
    }

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
                    CrimsonFlyEntity
                            .createAttributes()
                            .build()
            );
        }
    }

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
