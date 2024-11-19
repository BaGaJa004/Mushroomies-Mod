package net.bagaja.mushroomies.registry;

import net.bagaja.mushroomies.Mushroomies;
import net.bagaja.mushroomies.world.inventory.MinitraderMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Mushroomies.MOD_ID);

    public static final RegistryObject<MenuType<MinitraderMenu>> MINITRADER =
            MENU_TYPES.register("minitrader",
                    () -> IForgeMenuType.create((windowId, inv, data) ->
                            new MinitraderMenu(windowId, inv.player, null)));
}