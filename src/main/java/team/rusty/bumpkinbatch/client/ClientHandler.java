package team.rusty.bumpkinbatch.client;

import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import team.rusty.bumpkinbatch.client.model.ReaperModel;
import team.rusty.bumpkinbatch.client.renderer.ReaperRenderer;
import team.rusty.bumpkinbatch.entity.ReaperEntity;

public class ClientHandler {

    public static void addEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BEntities.REAPER.get(), ReaperRenderer::new);
    }

    public static void addEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ReaperModel.LAYER, ReaperModel::createLayer);
    }

}
