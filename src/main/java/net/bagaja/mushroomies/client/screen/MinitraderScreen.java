package net.bagaja.mushroomies.client.screen;

import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.bagaja.mushroomies.Mushroomies;
import net.minecraft.client.gui.GuiGraphics;

public class MinitraderScreen extends MerchantScreen {
    private static final ResourceLocation MINITRADER_GUI_TEXTURE =
            new ResourceLocation(Mushroomies.MOD_ID, "textures/gui/container/villager.png");

    public MinitraderScreen(MerchantMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(MINITRADER_GUI_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}