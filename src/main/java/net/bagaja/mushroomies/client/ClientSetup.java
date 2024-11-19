package net.bagaja.mushroomies.client;

import net.bagaja.mushroomies.Mushroomies;
import net.bagaja.mushroomies.client.model.MiniMushroomieModel;
import net.bagaja.mushroomies.client.model.MiniTransroomieModel;
import net.bagaja.mushroomies.client.model.MinitraderModel;
import net.bagaja.mushroomies.client.renderer.MiniMushroomieRenderer;
import net.bagaja.mushroomies.client.renderer.MiniTransroomieRenderer;
import net.bagaja.mushroomies.client.renderer.MinitraderRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = Mushroomies.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(ClientSetup::registerRenderers);
        modEventBus.addListener(ClientSetup::registerLayerDefinitions);
        modEventBus.addListener(ClientEventHandler::onClientSetup);
    }

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Mushroomies.MINI_MUSHROOMIE.get(), MiniMushroomieRenderer::new);
        event.registerEntityRenderer(Mushroomies.MINITRADER.get(), MinitraderRenderer::new);
        event.registerEntityRenderer(Mushroomies.MINI_TRANSROOMIE.get(), MiniTransroomieRenderer::new);
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MiniMushroomieModel.LAYER_LOCATION,
                MiniMushroomieModel::createBodyLayer);
        event.registerLayerDefinition(MinitraderModel.LAYER_LOCATION,
                MinitraderModel::createBodyLayer);
        event.registerLayerDefinition(MiniTransroomieModel.LAYER_LOCATION,
                MiniTransroomieModel::createBodyLayer);
    }
}