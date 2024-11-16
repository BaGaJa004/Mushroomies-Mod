package net.bagaja.mushroomies.client.renderer;

import net.bagaja.mushroomies.Mushroomies;
import net.bagaja.mushroomies.client.model.MinitraderModel;
import net.bagaja.mushroomies.entity.Minitrader;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MinitraderRenderer extends MobRenderer<Minitrader, MinitraderModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Mushroomies.MOD_ID, "textures/entity/minitrader.png");

    public MinitraderRenderer(EntityRendererProvider.Context context) {
        super(context, new MinitraderModel(context.bakeLayer(MinitraderModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Minitrader entity) {
        return TEXTURE;
    }
}