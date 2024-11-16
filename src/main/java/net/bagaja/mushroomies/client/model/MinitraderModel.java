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
    private final ModelPart minitrader;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public MinitraderModel(ModelPart root) {
        this.root = root;
        this.minitrader = root.getChild("Minitrader");
        this.head = this.minitrader.getChild("head");
        this.body = this.minitrader.getChild("body");
        this.left_arm = this.minitrader.getChild("left_arm");
        this.right_arm = this.minitrader.getChild("right_arm");
        this.left_leg = this.minitrader.getChild("left_leg");
        this.right_leg = this.minitrader.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition minitrader = partdefinition.addOrReplaceChild("Minitrader",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 18.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition head = minitrader.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(16, 23).addBox(-2.0F, 2.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 14).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = minitrader.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 23).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 14).addBox(-4.0F, -2.0F, -2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-4.0F, 1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition left_arm = minitrader.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(0, 14).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition right_arm = minitrader.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(0, 3).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition left_leg = minitrader.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(12, 23).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 4.0F, -2.0F));

        PartDefinition right_leg = minitrader.addOrReplaceChild("right_leg",
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

        // Head rotation
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);

        // Animation based on movement
        if (entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D) {
            // Walking animation
            float speed = 1.0f;
            float degree = 1.0f;

            this.left_arm.zRot = Mth.cos(limbSwing * speed) * degree * limbSwingAmount;
            this.right_arm.zRot = -Mth.cos(limbSwing * speed) * degree * limbSwingAmount;
            this.left_leg.zRot = -Mth.cos(limbSwing * speed) * degree * limbSwingAmount;
            this.right_leg.zRot = Mth.cos(limbSwing * speed) * degree * limbSwingAmount;
        } else {
            // Idle animation
            float f = ageInTicks * 0.1F;
            this.left_arm.zRot = Mth.cos(f) * 0.5F;
            this.right_arm.zRot = -Mth.cos(f) * 0.5F;
        }
    }
}