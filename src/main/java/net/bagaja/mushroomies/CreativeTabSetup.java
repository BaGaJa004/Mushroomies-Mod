package net.bagaja.mushroomies;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mushroomies.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabSetup {
    @SubscribeEvent
    public static void addToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(Mushroomies.MINI_MUSHROOMIE_SPAWN_EGG);
            event.accept(Mushroomies.MINITRADER_SPAWN_EGG);
        }
    }
}