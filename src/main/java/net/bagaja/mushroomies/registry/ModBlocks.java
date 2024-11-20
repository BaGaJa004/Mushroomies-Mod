package net.bagaja.mushroomies.registry;

import net.bagaja.mushroomies.Mushroomies;
import net.bagaja.mushroomies.block.MushroomWoodBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Mushroomies.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Mushroomies.MOD_ID);

    public static final RegistryObject<Block> MUSHROOM_WOOD = BLOCKS.register("mushroom_wood", MushroomWoodBlock::new);
    public static final RegistryObject<Item> MUSHROOM_WOOD_ITEM = ITEMS.register("mushroom_wood", () -> new BlockItem(MUSHROOM_WOOD.get(), new Item.Properties()));

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }
}