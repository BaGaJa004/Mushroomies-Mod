package net.bagaja.mushroomies;

import net.bagaja.mushroomies.entity.MiniMushroomie;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mushroomies.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitySetup {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(Mushroomies.MINI_MUSHROOMIE.get(), MiniMushroomie.createAttributes().build());
    }
}