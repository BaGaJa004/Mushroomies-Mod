package net.bagaja.mushroomies.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.bagaja.mushroomies.entity.MiniMushroomie;
import net.bagaja.mushroomies.Mushroomies;
import net.minecraft.util.Mth;

public class MiniMushroomieModel extends HierarchicalModel<MiniMushroomie> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(Mushroomies.MOD_ID, "mini_mushroomie"), "main");

    private final ModelPart root;
    private final ModelPart minimushroomi;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public MiniMushroomieModel(ModelPart root) {
        this.root = root;
        this.minimushroomi = root.getChild("minimushroomi");
        this.head = this.minimushroomi.getChild("head");
        this.body = this.minimushroomi.getChild("body");
        this.leftArm = this.minimushroomi.getChild("left_arm");
        this.rightArm = this.minimushroomi.getChild("right_arm");
        this.leftLeg = this.minimushroomi.getChild("left_leg");
        this.rightLeg = this.minimushroomi.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition minimushroomi = partdefinition.addOrReplaceChild("minimushroomi",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 18.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition head = minimushroomi.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(16, 23).addBox(-2.0F, 2.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 14).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = minimushroomi.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 23).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition leftArm = minimushroomi.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(0, 5).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition rightArm = minimushroomi.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition leftLeg = minimushroomi.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 17).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 4.0F, -2.0F));

        PartDefinition rightLeg = minimushroomi.addOrReplaceChild("right_leg",
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
    public void setupAnim(MiniMushroomie entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        // Head rotations
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
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
        float time = ageInTicks * 0.1F;
        float bounce = Mth.sin(time * 2.0F) * 0.05F;

        // Bouncing movement
        this.minimushroomi.y = 18.0F + bounce * 20.0F;

        // Head bobbing
        this.head.xRot = (float)Math.toRadians(-12.5F);
        this.head.x = -1.0F;

        // Arm movement
        float armAngle = (float)Math.toRadians(-122.5F);
        float armWave = Mth.sin(time * 4.0F) * (float)Math.PI / 18.0F;

        this.leftArm.zRot = armAngle + armWave;
        this.rightArm.zRot = armAngle - armWave;
        this.leftArm.y = -1.0F;
        this.rightArm.y = -1.0F;
    }

    private void applyIdleAnimation(float ageInTicks) {
        float time = ageInTicks * 0.1F;
        float armAngle = Mth.sin(time) * (float)Math.PI / 5.5F;

        this.leftArm.zRot = -armAngle;
        this.rightArm.zRot = armAngle;
    }

    private void applyWalkingAnimation(float ageInTicks) {
        float time = ageInTicks * 0.1F;
        float legAngle = Mth.sin(time) * (float)Math.PI / 10.0F;
        float armAngle = Mth.sin(time) * (float)Math.PI / 10.0F;

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