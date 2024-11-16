package net.bagaja.mushroomies.client.renderer;

import net.bagaja.mushroomies.Mushroomies;
import net.bagaja.mushroomies.client.model.MiniMushroomieModel;
import net.bagaja.mushroomies.entity.MiniMushroomie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MiniMushroomieRenderer extends MobRenderer<MiniMushroomie, MiniMushroomieModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Mushroomies.MOD_ID, "textures/entity/mini_mushroomie.png");

    public MiniMushroomieRenderer(EntityRendererProvider.Context context) {
        super(context, new MiniMushroomieModel(context.bakeLayer(MiniMushroomieModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MiniMushroomie entity) {
        return TEXTURE;
    }
}
