package team.rusty.bumpkinbatch.client.renderer;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.client.model.ReaperModel;
import team.rusty.bumpkinbatch.entity.ReaperEntity;

public class ReaperRenderer extends BipedRenderer<ReaperEntity, ReaperModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BumpkinBatch.ID, "textures/entity/reaper.png");

    public ReaperRenderer(EntityRendererManager manager) {
        super(manager, new ReaperModel(), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(ReaperEntity entity) {
        return TEXTURE;
    }
}
