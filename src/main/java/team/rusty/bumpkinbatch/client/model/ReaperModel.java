package team.rusty.bumpkinbatch.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.entity.ReaperEntity;

import java.util.function.Function;

public class ReaperModel extends HumanoidModel<ReaperEntity> {

    private final ModelPart root;

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(BumpkinBatch.ID, "reaper"), "main");

    public ReaperModel(ModelPart root) {
        super(root);

        this.root = root;
    }

    public static LayerDefinition createLayer() {
        var mesh = new MeshDefinition();
        var root = mesh.getRoot();

        var head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(28, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 9.0F, 8.0F),
                PartPose.offset(0.0F, -16.0F, 0.0F));

        var hat = root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        var rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(36, 30).mirror().addBox(-3.0F, 0.0F, -1.0F, 2.0F, 20.0F, 2.0F)
                .texOffs(46, 17).mirror().addBox(-4.0F, 0.0F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offset(-5.0F, -15.0F, 0.0F));

        var leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(36, 30).addBox(1.0F, 0.0F, -1.0F, 2.0F, 20.0F, 2.0F)
                .texOffs(46, 17).addBox(1.0F, 0.0F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offset(5.0F, -15.0F, 0.0F));

        var rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(28, 30).mirror().addBox(-2.0F, 2.0F, -1.0F, 2.0F, 21.0F, 2.0F)
                .texOffs(28, 17).mirror().addBox(-3.0F, 2.0F, -2.0F, 5.0F, 9.0F, 4.0F, new CubeDeformation(0.5F))
                .texOffs(44, 30).mirror().addBox(-2.0F, 2.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.5F)),
                PartPose.offset(-2.0F, 1.0F, 0.0F));

        var leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(28, 30).addBox(0.0F, 2.0F, -1.0F, 2.0F, 21.0F, 2.0F)
                .texOffs(28, 17).addBox(-2.0F, 2.0F, -2.0F, 5.0F, 9.0F, 4.0F, new CubeDeformation(0.5F))
                .texOffs(44, 30).addBox(0.0F, 2.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.5F)),
                PartPose.offset(2.0F, 1.0F, 0.0F));

        var body = root.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-5.0F, -15.0F, -2.0F, 10.0F, 18.0F, 4.0F)
                        .texOffs(0,  0).addBox(-5.0F, -15.0F, -2.0F, 10.0F, 18.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0f, 0.0f, 0.0f));


        //mesh, height, width
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(ReaperEntity reaper, float p_102867_, float p_102868_, float p_102869_, float p_102870_, float p_102871_) {
        //super.setupAnim(reaper, p_102867_, p_102868_, p_102869_, p_102870_, p_102871_);
        boolean flag = reaper.getFallFlyingTicks() > 4;
        boolean flag1 = reaper.isVisuallySwimming();
        this.head.yRot = p_102870_ * ((float)Math.PI / 180F);
        if (flag) {
            this.head.xRot = (-(float)Math.PI / 4F);
        } else if (this.swimAmount > 0.0F) {
            if (flag1) {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, (-(float)Math.PI / 4F));
            } else {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, p_102871_ * ((float)Math.PI / 180F));
            }
        } else {
            this.head.xRot = p_102871_ * ((float)Math.PI / 180F);
        }

        this.body.yRot = 0.0F;
        this.rightArm.z = 0.0F;
        this.rightArm.x = -5.0F;
        this.leftArm.z = 0.0F;
        this.leftArm.x = 5.0F;
        float f = 1.0F;
        if (flag) {
            f = (float)reaper.getDeltaMovement().lengthSqr();
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        this.rightArm.xRot = Mth.cos(p_102867_ * 0.6662F + (float)Math.PI) * 2.0F * p_102868_ * 0.5F / f;
        this.leftArm.xRot = Mth.cos(p_102867_ * 0.6662F) * 2.0F * p_102868_ * 0.5F / f;
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightLeg.xRot = Mth.cos(p_102867_ * 0.6662F) * 1.4F * p_102868_ / f;
        this.leftLeg.xRot = Mth.cos(p_102867_ * 0.6662F + (float)Math.PI) * 1.4F * p_102868_ / f;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
        this.rightLeg.zRot = 0.0F;
        this.leftLeg.zRot = 0.0F;
        if (this.riding) {
            this.rightArm.xRot += (-(float)Math.PI / 5F);
            this.leftArm.xRot += (-(float)Math.PI / 5F);
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
        }

        this.rightArm.yRot = 0.0F;
        this.leftArm.yRot = 0.0F;
        boolean flag2 = reaper.getMainArm() == HumanoidArm.RIGHT;
        /*if (reaper.isUsingItem()) {
            boolean flag3 = reaper.getUsedItemHand() == InteractionHand.MAIN_HAND;
            if (flag3 == flag2) {
                this.poseRightArm(reaper);
            } else {
                this.poseLeftArm(reaper);
            }
        } else {
            boolean flag4 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
            if (flag2 != flag4) {
                this.poseLeftArm(reaper);
                this.poseRightArm(reaper);
            } else {
                this.poseRightArm(reaper);
                this.poseLeftArm(reaper);
            }
        }*/

        this.setupAttackAnimation(reaper, p_102869_);

        if (this.swimAmount > 0.0F) {
            float f5 = p_102867_ % 26.0F;
            HumanoidArm humanoidarm = reaper.swingingArm == InteractionHand.MAIN_HAND ? reaper.getMainArm() : reaper.getMainArm().getOpposite();
            float f1 = humanoidarm == HumanoidArm.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
            float f2 = humanoidarm == HumanoidArm.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
            if (!reaper.isUsingItem()) {
                if (f5 < 14.0F) {
                    this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, 0.0F);
                    this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, 0.0F);
                    this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float)Math.PI);
                    this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float)Math.PI);

                    float f6 = 1.8707964F * (-65.0F * f5 + f5 * f5) / (-65.0F * 14.0f + 14.0f * 14.0f);
                    this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, (float)Math.PI + f6);
                    this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, (float)Math.PI - f6);
                } else if (f5 >= 14.0F && f5 < 22.0F) {

                    float f6 = (f5 - 14.0F) / 8.0F;
                    this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, ((float)Math.PI / 2F) * f6);
                    this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, ((float)Math.PI / 2F) * f6);
                    this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float)Math.PI);
                    this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float)Math.PI);
                    this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, 5.0f - 1.8707964F * f6);
                    this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, 1.27f + 1.8707964F * f6);
                } else if (f5 >= 22.0F && f5 < 26.0F) {
                    float f3 = (f5 - 22.0F) / 4.0F;
                    this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f3);
                    this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f3);
                    this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float)Math.PI);
                    this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float)Math.PI);
                    this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, (float)Math.PI);
                    this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, (float)Math.PI);
                }
            }

            this.leftLeg.xRot = Mth.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * Mth.cos(p_102867_ * 0.3f + (float)Math.PI));
            this.rightLeg.xRot = Mth.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * Mth.cos(p_102867_ * 0.3f));
        }

        this.hat.copyFrom(this.head);
    }

    @Override
    public void translateToHand(HumanoidArm p_103778_, PoseStack p_103779_) {
        float f = p_103778_ == HumanoidArm.RIGHT ? -1.0F : 0.75F;
        ModelPart modelpart = this.getArm(p_103778_);
        modelpart.x += f;
        modelpart.translateAndRotate(p_103779_);
        p_103779_.translate(0.0, 0.625, 0.0);
        modelpart.x -= f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
