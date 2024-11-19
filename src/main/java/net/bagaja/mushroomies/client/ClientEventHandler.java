package net.bagaja.mushroomies.client;

import net.bagaja.mushroomies.client.screen.MinitraderScreen;
import net.bagaja.mushroomies.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Register the custom screen with your custom menu type
            MenuScreens.register(ModMenuTypes.MINITRADER.get(), MinitraderScreen::new);
        });
    }
}