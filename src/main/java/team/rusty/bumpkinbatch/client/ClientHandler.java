package team.rusty.bumpkinbatch.client;

import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import team.rusty.bumpkinbatch.client.model.ReaperModel;
import team.rusty.bumpkinbatch.client.renderer.ReaperRenderer;
import team.rusty.bumpkinbatch.entity.ReaperEntity;
import team.rusty.bumpkinbatch.registry.BEntities;

public class ClientHandler {

    public static void addEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BEntities.REAPER.get(), ReaperRenderer::new);
    }

    public static void addEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ReaperModel.LAYER, ReaperModel::createLayer);
    }

    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ClientHandler::addEntityLayers);
        bus.addListener(ClientHandler::addEntityRenderers);
    }
}
