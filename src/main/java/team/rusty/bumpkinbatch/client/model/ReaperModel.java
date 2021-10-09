package team.rusty.bumpkinbatch.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.entity.ReaperEntity;

import java.util.function.Function;

public class ReaperModel extends EntityModel<ReaperEntity> {

    private final ModelPart root;

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(BumpkinBatch.ID, "reaper"), "main");

    public ReaperModel(ModelPart root) {
        super();
        this.root = root;
    }

    public static LayerDefinition createLayer() {
        var mesh = new MeshDefinition();
        var root = mesh.getRoot();


        //.texOffs(same as texoff)
        //.addBox(all add box except last float and boolean)
        //,offset(all of setPos)
        /*
        left_leg = new ModelRenderer(this);
        left_leg.setPos(0.0F, 24.0F, 0.0F);
        left_leg.texOffs(6, 10).addBox(-3.0F, -11.0F, 0.0F, 2.0F, 11.0F, 1.0F, 0.0F, false);
        */

        var  left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(6, 10).addBox(-3.0F, -11.0F, 0.0F, 2.0F, 11.0F, 1.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        var  right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(0, 10).addBox(1.0F, -11.0F, 0.0F, 2.0F, 11.0F, 1.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        var  face = root.addOrReplaceChild("face", CubeListBuilder.create()
                .texOffs(0, 0).addBox(1.0F, -11.0F, 0.0F, 2.0F, 11.0F, 1.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        //mesh, height, width
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(ReaperEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public ModelPart root() {
        return root;
    }
}
