package net.bagaja.mushroomies.world.inventory;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;

public class MinitraderMenu extends MerchantMenu {
    private final Merchant trader;

    public MinitraderMenu(int containerId, Player player, Merchant merchant) {
        super(containerId, player.getInventory(), merchant);
        this.trader = merchant;
    }

    public Merchant getTrader() {
        return this.trader;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.trader.getTradingPlayer() == player;
    }
}