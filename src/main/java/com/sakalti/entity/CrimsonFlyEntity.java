package com.sakalti.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumSet;

public class CrimsonFlyEntity extends FlyingEntity {

    public static final String MODID = "sakalti";

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(
                    ForgeRegistries.ENTITIES,
                    MODID
            );

    public static final RegistryObject<EntityType<CrimsonFlyEntity>> CRIMSON_FLY =
            ENTITIES.register(
                    "crimson_fly",
                    () -> EntityType.Builder.<CrimsonFlyEntity>of(
                                    CrimsonFlyEntity::new,
                                    EntityClassification.MONSTER
                            )
                            .sized(0.8F, 0.8F)
                            .clientTrackingRange(8)
                            .updateInterval(3)
                            .build(MODID + ":crimson_fly")
            );

    public CrimsonFlyEntity(
            EntityType<? extends CrimsonFlyEntity> type,
            World world
    ) {
        super(type, world);

        this.moveControl =
                new FlyingMovementController(
                        this,
                        20,
                        true
                );

        this.setNoGravity(true);
    }

    protected PathNavigator createNavigation(World world) {
        return new FlyingPathNavigator(this, world);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    protected void registerGoals() {

        this.goalSelector.addGoal(
                1,
                new SeekHigherGroundGoal(this)
        );

        this.goalSelector.addGoal(
                2,
                new CrimsonFlyMeleeAttackGoal(this)
        );

        this.goalSelector.addGoal(
                8,
                new LookAtGoal(
                        this,
                        PlayerEntity.class,
                        8.0F
                )
        );

        this.goalSelector.addGoal(
                9,
                new LookRandomlyGoal(this)
        );

        this.targetSelector.addGoal(
                1,
                new HurtByTargetGoal(this)
        );

        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(
                        this,
                        PiglinEntity.class,
                        true
                )
        );

        this.targetSelector.addGoal(
                3,
                new NearestAttackableTargetGoal<>(
                        this,
                        HoglinEntity.class,
                        true
                )
        );
    }

    public float getCrimsonFlyAttackDamage() {
        switch (this.level.getDifficulty()) {
            case EASY:
                return 4.0F;
            case HARD:
                return 9.0F;
            case NORMAL:
            case PEACEFUL:
            default:
                return 6.0F;
        }
    }

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

        public boolean canUse() {
            if (this.cooldown > 0) {
                this.cooldown--;
                return false;
            }

            this.cooldown = 20;

            BlockPos current = this.fly.blockPosition();

            for (int i = 0; i < 16; i++) {
                int x = current.getX()
                        + this.fly.getRandom().nextInt(21)
                        - 10;

                int y = current.getY()
                        + 3
                        + this.fly.getRandom().nextInt(12);

                int z = current.getZ()
                        + this.fly.getRandom().nextInt(21)
                        - 10;

                BlockPos candidate = new BlockPos(x, y, z);

                if (candidate.getY() <= current.getY()) {
                    continue;
                }

                if (this.fly.level.isEmptyBlock(candidate)
                        && this.fly.level.isEmptyBlock(candidate.above())) {

                    this.targetPos = candidate;
                    return true;
                }
            }

            return false;
        }

        public boolean canContinueToUse() {
            if (this.targetPos == null) {
                return false;
            }

            double distance = this.fly.distanceToSqr(
                    this.targetPos.getX() + 0.5D,
                    this.targetPos.getY() + 0.5D,
                    this.targetPos.getZ() + 0.5D
            );

            return distance > 4.0D;
        }

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

        public void stop() {
            this.targetPos = null;
            this.fly.getNavigation().stop();
        }
    }

    public static class CrimsonFlyMeleeAttackGoal extends Goal {

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

        public boolean canUse() {
            LivingEntity target = this.fly.getTarget();
            return target != null && target.isAlive();
        }

        public boolean canContinueToUse() {
            LivingEntity target = this.fly.getTarget();
            return target != null && target.isAlive();
        }

        public void start() {
            this.attackCooldown = 0;
        }

        public void tick() {
            LivingEntity target = this.fly.getTarget();

            if (target == null) {
                return;
            }

            this.fly.getLookControl().setLookAt(
                    target,
                    30.0F,
                    30.0F
            );

            double distance = this.fly.distanceToSqr(target);

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

                float damage = this.fly.getCrimsonFlyAttackDamage();

                target.hurt(
                        DamageSource.mobAttack(this.fly),
                        damage
                );

                this.fly.swing(
                        Hand.MAIN_HAND
                );
            }
        }
    }

    @Mod.EventBusSubscriber(
            modid = MODID,
            bus = Mod.EventBusSubscriber.Bus.MOD
    )
    public static class AttributeHandler {

        @SubscribeEvent
        public static void registerAttributes(
                EntityAttributeCreationEvent event
        ) {
            event.put(
                    CRIMSON_FLY.get(),
                    CrimsonFlyEntity.createAttributes().build()
            );
        }
    }

    public static void register(
            IEventBus eventBus
    ) {
        ENTITIES.register(eventBus);
    }
}
