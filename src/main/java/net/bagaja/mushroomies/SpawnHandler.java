package net.bagaja.mushroomies;

import net.bagaja.mushroomies.entity.MiniTransroomie;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mushroomies.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpawnHandler {
    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == Mushroomies.MINI_MUSHROOMIE.get()) {
            if (entity.level().getRandom().nextFloat() < 0.05f) { // 5% chance
                MiniTransroomie transroomie = Mushroomies.MINI_TRANSROOMIE.get()
                        .create(entity.level());
                if (transroomie != null) {
                    transroomie.moveTo(entity.getX(), entity.getY(), entity.getZ());
                    entity.level().addFreshEntity(transroomie);
                    entity.discard(); // Remove the original mini mushroomie
                }
            }
        }
    }
}
