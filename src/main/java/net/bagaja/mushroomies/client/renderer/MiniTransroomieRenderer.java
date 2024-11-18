package net.bagaja.mushroomies.client.renderer;

import net.bagaja.mushroomies.Mushroomies;
import net.bagaja.mushroomies.client.model.MiniTransroomieModel;
import net.bagaja.mushroomies.entity.MiniTransroomie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MiniTransroomieRenderer extends MobRenderer<MiniTransroomie, MiniTransroomieModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Mushroomies.MOD_ID, "textures/entity/transroomie.png");

    public MiniTransroomieRenderer(EntityRendererProvider.Context context) {
        super(context, new MiniTransroomieModel(context.bakeLayer(MiniTransroomieModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MiniTransroomie entity) {
        return TEXTURE;
    }
}