package net.bagaja.mushroomies;

import net.bagaja.mushroomies.client.ClientSetup;
import net.bagaja.mushroomies.entity.MiniMushroomie;
import net.bagaja.mushroomies.entity.MiniTransroomie;
import net.bagaja.mushroomies.entity.Minitrader;
import net.bagaja.mushroomies.registry.ModMenuTypes;
import net.bagaja.mushroomies.world.inventory.MinitraderMenu;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeSpawnEggItem;
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

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<EntityType<MiniMushroomie>> MINI_MUSHROOMIE =
            ENTITY_TYPES.register("mini_mushroomie",
                    () -> EntityType.Builder.of(MiniMushroomie::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.0f)
                            .clientTrackingRange(8)  // Add this
                            .updateInterval(3)       // Add this
                            .build(MOD_ID + ":mini_mushroomie"));

    public static final RegistryObject<EntityType<Minitrader>> MINITRADER =
            ENTITY_TYPES.register("minitrader",
                    () -> EntityType.Builder.of(Minitrader::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.0f)
                            .build(MOD_ID + ":minitrader"));

    public static final RegistryObject<EntityType<MiniTransroomie>> MINI_TRANSROOMIE =
            ENTITY_TYPES.register("mini_transroomie",
                    () -> EntityType.Builder.of(MiniTransroomie::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.0f)
                            .build(MOD_ID + ":mini_transroomie"));

    public static final RegistryObject<Item> MINI_MUSHROOMIE_SPAWN_EGG =
            ITEMS.register("mini_mushroomie_spawn_egg",
                    () -> new ForgeSpawnEggItem(MINI_MUSHROOMIE,
                            0xFF0000, // Red
                            0xFFFFFF, // White
                            new Item.Properties()));

    public static final RegistryObject<Item> MINITRADER_SPAWN_EGG =
            ITEMS.register("minitrader_spawn_egg",
                    () -> new ForgeSpawnEggItem(MINITRADER,
                            0x8B4513, // Green
                            0x00FF00, // Brown
                            new Item.Properties()));

    public Mushroomies() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ENTITY_TYPES.register(modEventBus);
        ITEMS.register(modEventBus);
        ModMenuTypes.MENU_TYPES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::init);
    }
}

// changing trader inv design
// adding mushroom wood
// adding new houses for them