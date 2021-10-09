package team.rusty.bumpkinbatch.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.client.model.ReaperModel;

public class ReaperRenderer extends MobRenderer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BumpkinBatch.ID, "textures/entity/reaper.png");

    public ReaperRenderer(EntityRendererProvider.Context context) {
        super(context, new ReaperModel(Minecraft.getInstance().getEntityModels().bakeLayer(ReaperModel.LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity entity) {
        return TEXTURE;
    }
}
