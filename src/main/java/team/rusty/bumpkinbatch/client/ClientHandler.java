package team.rusty.bumpkinbatch.client;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import team.rusty.bumpkinbatch.client.renderer.ReaperRenderer;
import team.rusty.bumpkinbatch.registry.BEntities;

public class ClientHandler {

    public static void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(BEntities.REAPER.get(), ReaperRenderer::new);
    }

    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener((FMLClientSetupEvent event) -> registerEntityRenderers());
    }
}
