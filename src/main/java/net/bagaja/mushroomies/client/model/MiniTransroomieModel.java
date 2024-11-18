package net.bagaja.mushroomies.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.bagaja.mushroomies.entity.MiniTransroomie;
import net.bagaja.mushroomies.Mushroomies;
import net.minecraft.util.Mth;

public class MiniTransroomieModel extends HierarchicalModel<MiniTransroomie> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(Mushroomies.MOD_ID, "mini_transroomie"), "main");

    private final ModelPart root;
    private final ModelPart minitransroomie;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public MiniTransroomieModel(ModelPart root) {
        this.root = root;
        this.minitransroomie = root.getChild("minitransroomie");
        this.head = this.minitransroomie.getChild("head");
        this.body = this.minitransroomie.getChild("body");
        this.leftArm = this.minitransroomie.getChild("left_arm");
        this.rightArm = this.minitransroomie.getChild("right_arm");
        this.leftLeg = this.minitransroomie.getChild("left_leg");
        this.rightLeg = this.minitransroomie.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition minitransroomie = partdefinition.addOrReplaceChild("minitransroomie",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 18.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition head = minitransroomie.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(16, 23).addBox(-2.0F, 2.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 14).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = minitransroomie.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 23).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition leftArm = minitransroomie.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(0, 5).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition rightArm = minitransroomie.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition leftLeg = minitransroomie.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 17).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 4.0F, -2.0F));

        PartDefinition rightLeg = minitransroomie.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 14).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 4.0F, 2.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(MiniTransroomie entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        // Head rotations
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);

        // Apply animations based on state
        if (entity.danceAnimationState.isStarted()) {
            applyDanceAnimation(ageInTicks);
        } else if (entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D) {
            applyWalkingAnimation(ageInTicks);
        } else {
            applyIdleAnimation(ageInTicks);
        }
    }

    private void applyDanceAnimation(float ageInTicks) {
        float time = ageInTicks * 0.2F;
        float bounce = Mth.sin(time * 2.0F) * 4.0F;

        this.minitransroomie.y = 13.0F + bounce;

        float armAngle = (float)Math.toRadians(-122.5F);
        float armWave = Mth.sin(time * 4.0F) * (float)Math.PI / 18.0F;

        this.leftArm.zRot = armAngle + armWave;
        this.rightArm.zRot = armAngle - armWave;
        this.leftArm.y = -1.0F;
        this.rightArm.y = -1.0F;
    }

    private void applyIdleAnimation(float ageInTicks) {
        float time = ageInTicks * 0.2F;
        float armAngle = Mth.sin(time) * (float)Math.PI / 5.5F;

        this.leftArm.zRot = -armAngle;
        this.rightArm.zRot = armAngle;
    }

    private void applyWalkingAnimation(float ageInTicks) {
        float legAngle = Mth.sin(ageInTicks) * (float)Math.PI / 10.0F;
        float armAngle = Mth.sin(ageInTicks) * (float)Math.PI / 10.0F;

        this.leftLeg.zRot = -legAngle;
        this.rightLeg.zRot = legAngle;
        this.leftArm.zRot = armAngle;
        this.rightArm.zRot = -armAngle;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
