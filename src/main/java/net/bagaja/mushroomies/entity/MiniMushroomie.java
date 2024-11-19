package net.bagaja.mushroomies.entity;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;

public class MiniMushroomie extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState danceAnimationState = new AnimationState();

    private static final double DANCE_RADIUS = 2.0D;
    private boolean isDancing = false;

    public MiniMushroomie(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            // Check for nearby players holding emeralds
            boolean shouldDance = false;
            for (Player player : this.level().getEntitiesOfClass(Player.class,
                    this.getBoundingBox().inflate(DANCE_RADIUS))) {
                if (player.getMainHandItem().is(net.minecraft.world.item.Items.EMERALD) ||
                        player.getOffhandItem().is(net.minecraft.world.item.Items.EMERALD)) {
                    shouldDance = true;
                    break;
                }
            }

            // Update animation states and movement
            if (shouldDance) {
                if (!isDancing) {
                    isDancing = true;
                    // Stop all movement when starting to dance
                    this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
                }
                this.walkAnimationState.stop();
                this.idleAnimationState.stop();
                this.danceAnimationState.startIfStopped(this.tickCount);

                // Cancel horizontal movement while dancing
                this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
            } else {
                isDancing = false;
                if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D) {
                    this.danceAnimationState.stop();
                    this.idleAnimationState.stop();
                    this.walkAnimationState.startIfStopped(this.tickCount);
                } else {
                    this.danceAnimationState.stop();
                    this.walkAnimationState.stop();
                    this.idleAnimationState.startIfStopped(this.tickCount);
                }
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D) {
            @Override
            public boolean canUse() {
                return !isDancing && super.canUse();
            }
        });
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                return !isDancing && super.canUse();
            }
        });
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.moveControl = new MoveControl(this);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null; // Not breedable
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected void pushEntities() {
        super.pushEntities();
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
}

// @Override
//protected void defineSynchedData() {
//    super.defineSynchedData();
//}
//
//@Override
//public void aiStep() {
//    super.aiStep();
//    if (this.level().isClientSide()) {
//        // Update animation states only on client side
//        this.updateAnimationStates();
//    }
//}
//
//private void updateAnimationStates() {
//    if (this.isDancing) {
//        this.walkAnimationState.stop();
//        this.idleAnimationState.stop();
//        this.danceAnimationState.startIfStopped(this.tickCount);
//    } else if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D) {
//        this.danceAnimationState.stop();
//        this.idleAnimationState.stop();
//        this.walkAnimationState.startIfStopped(this.tickCount);
//    } else {
//        this.danceAnimationState.stop();
//        this.walkAnimationState.stop();
//        this.idleAnimationState.startIfStopped(this.tickCount);
//    }
//}
