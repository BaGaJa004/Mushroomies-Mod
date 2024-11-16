package net.bagaja.mushroomies.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.bagaja.mushroomies.entity.Minitrader;
import net.bagaja.mushroomies.Mushroomies;
import net.minecraft.util.Mth;

public class MinitraderModel extends HierarchicalModel<Minitrader> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(Mushroomies.MOD_ID, "minitrader"), "main");
    private final ModelPart root;
    private final ModelPart Minitrader;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public MinitraderModel(ModelPart root) {
        this.root = root;
        this.Minitrader = root.getChild("Minitrader");
        this.head = this.Minitrader.getChild("head");
        this.body = this.Minitrader.getChild("body");
        this.left_arm = this.Minitrader.getChild("left_arm");
        this.right_arm = this.Minitrader.getChild("right_arm");
        this.left_leg = this.Minitrader.getChild("left_leg");
        this.right_leg = this.Minitrader.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Minitrader = partdefinition.addOrReplaceChild("Minitrader",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 18.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition head = Minitrader.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(16, 23).addBox(-2.0F, 2.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 14).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = Minitrader.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 23).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 14).addBox(-4.0F, -2.0F, -2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-4.0F, 1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition left_arm = Minitrader.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(0, 14).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition right_arm = Minitrader.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition left_leg = Minitrader.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(12, 23).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 4.0F, -2.0F));

        PartDefinition right_leg = Minitrader.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 19).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 4.0F, 2.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(Minitrader entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        // Body and head rotation
        float bodyRotation = netHeadYaw * ((float)Math.PI / 180F) * 0.5F;
        this.Minitrader.yRot = bodyRotation;
        this.head.yRot = (netHeadYaw * ((float)Math.PI / 180F)) - bodyRotation;
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        // Walking animation
        if (entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D) {
            float time = ageInTicks * 0.1F;
            float legAngle = Mth.sin(time) * (float)Math.PI / 10.0F;
            float armAngle = Mth.sin(time) * (float)Math.PI / 10.0F;

            this.left_leg.zRot = -legAngle;
            this.right_leg.zRot = legAngle;
            this.left_arm.zRot = armAngle;
            this.right_arm.zRot = -armAngle;
        }
    }
}