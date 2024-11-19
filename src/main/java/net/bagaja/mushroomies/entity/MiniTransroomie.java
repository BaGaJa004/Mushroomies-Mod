package net.bagaja.mushroomies.entity;

import net.bagaja.mushroomies.Mushroomies;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class MiniTransroomie extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState danceAnimationState = new AnimationState();

    private static final double DANCE_RADIUS = 2.0D;
    private boolean isDancing = false;

    public MiniTransroomie(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        // Make the entity fireproof
        this.fireImmune();
    }

    @Override
    public void tick() {
        super.tick();

        // Keep health at maximum
        this.setHealth(this.getMaxHealth());

        // Remove any harmful effects each tick
        this.removeAllEffects();

        // Prevent drowning by adding water breathing
        this.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, false, false));

        // If somehow in void, teleport back up
        if (this.getY() < -64) {
            this.teleportTo(this.getX(), 64, this.getZ());
        }

        if (this.level().isClientSide()) {
            boolean shouldDance = false;
            for (Player player : this.level().getEntitiesOfClass(Player.class,
                    this.getBoundingBox().inflate(DANCE_RADIUS))) {
                if (player.getMainHandItem().is(net.minecraft.world.item.Items.EMERALD) ||
                        player.getOffhandItem().is(net.minecraft.world.item.Items.EMERALD)) {
                    shouldDance = true;
                    break;
                }
            }

            if (shouldDance) {
                if (!isDancing) {
                    isDancing = true;
                    this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
                }
                this.walkAnimationState.stop();
                this.idleAnimationState.stop();
                this.danceAnimationState.startIfStopped(this.tickCount);

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
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    // Prevent removal
    @Override
    public void remove(RemovalReason reason) {
        if (reason == RemovalReason.KILLED) {
            return; // Prevent removal if killed
        }
        super.remove(reason);
    }

    @Override
    public void die(DamageSource damageSource) {
        // Do nothing to prevent death
    }

    // Prevent burning in fire/lava
    @Override
    public boolean fireImmune() {
        return true;
    }

    // Prevent drowning
    @Override
    public boolean canDrownInFluidType(net.minecraftforge.fluids.FluidType type) {
        return false;
    }

    // Prevent falling damage
    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
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
                .add(Attributes.MAX_HEALTH, Float.MAX_VALUE)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(Mushroomies.MINI_MUSHROOMIE_SPAWN_EGG.get());
    }
}