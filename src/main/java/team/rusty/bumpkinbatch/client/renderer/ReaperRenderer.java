package team.rusty.bumpkinbatch.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.client.model.ReaperModel;
import team.rusty.bumpkinbatch.entity.ReaperEntity;

public class ReaperRenderer extends HumanoidMobRenderer<ReaperEntity, ReaperModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BumpkinBatch.ID, "textures/entity/reaper.png");

    public ReaperRenderer(EntityRendererProvider.Context context) {
        super(context, new ReaperModel(Minecraft.getInstance().getEntityModels().bakeLayer(ReaperModel.LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(ReaperEntity entity) {
        return TEXTURE;
    }
}
