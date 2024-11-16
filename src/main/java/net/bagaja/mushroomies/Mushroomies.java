package net.bagaja.mushroomies;

import net.bagaja.mushroomies.client.ClientSetup;
import net.bagaja.mushroomies.entity.MiniMushroomie;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Mushroomies.MOD_ID)
public class Mushroomies {
    public static final String MOD_ID = "mushroomies";

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<EntityType<MiniMushroomie>> MINI_MUSHROOMIE =
            ENTITY_TYPES.register("mini_mushroomie",
                    () -> EntityType.Builder.of(MiniMushroomie::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.0f)
                            .build(MOD_ID + ":mini_mushroomie"));

    public Mushroomies() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ENTITY_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register client setup
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::init);
    }
}