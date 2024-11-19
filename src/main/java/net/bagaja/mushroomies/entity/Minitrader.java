package net.bagaja.mushroomies.entity;

import net.bagaja.mushroomies.world.inventory.MinitraderMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Minitrader extends AbstractVillager implements MenuProvider {
    private final SimpleContainer inventory = new SimpleContainer(8);

    public Minitrader(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.isAlive() && !this.isTrading() && !this.isBaby()) {
            if (!this.level().isClientSide() && !this.getOffers().isEmpty()) {
                this.setTradingPlayer(player);
                this.openTradingScreen(player, this.getDisplayName(), 1);
                return InteractionResult.sidedSuccess(true);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("entity.mushroomies.minitrader");
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MinitraderMenu(id, player, this);
    }

    @Override
    protected void updateTrades() {
        MerchantOffers offers = this.getOffers();

        // Trade 4 brown mushrooms for a wooden axe
        offers.add(new MerchantOffer(
                new ItemStack(Items.BROWN_MUSHROOM, 4),
                new ItemStack(Items.WOODEN_AXE, 1),
                10, 2, 0.05F));

        // Trade 2 red mushrooms for an oak sapling
        offers.add(new MerchantOffer(
                new ItemStack(Items.RED_MUSHROOM, 2),
                new ItemStack(Items.OAK_SAPLING, 1),
                10, 2, 0.05F));

        // Trade 32 brown mushrooms for 16 oak planks
        offers.add(new MerchantOffer(
                new ItemStack(Items.BROWN_MUSHROOM, 32),
                new ItemStack(Items.OAK_PLANKS, 16),
                10, 2, 0.05F));
    }


    @Override
    protected void rewardTradeXp(MerchantOffer offer) {
        Player tradingPlayer = this.getTradingPlayer();
        if (tradingPlayer != null) {
            tradingPlayer.giveExperiencePoints(3);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractVillager.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public SimpleContainer getInventory() {
        return this.inventory;
    }

    @Override
    public void playSound(SoundEvent sound) {
        // Prevent all villager-related sounds
        if (sound != SoundEvents.VILLAGER_TRADE &&
                sound != SoundEvents.VILLAGER_YES &&
                sound != SoundEvents.VILLAGER_NO &&
                sound != SoundEvents.VILLAGER_CELEBRATE &&
                sound != SoundEvents.VILLAGER_WORK_FLETCHER) {
            super.playSound(sound);
        }
    }

    @Override
    protected SoundEvent getTradeUpdatedSound(boolean isEmpty) {
        return null;  // Prevents the trade update sound
    }

    @Override
    public @NotNull SoundEvent getNotifyTradeSound() {
        return null;  // Prevents the trade notification sound
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;  // Prevents ambient villager sounds
    }
}