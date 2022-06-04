package team.rusty.bumpkinbatch;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import team.rusty.bumpkinbatch.client.ClientHandler;
import team.rusty.bumpkinbatch.registry.BBlocks;
import team.rusty.bumpkinbatch.registry.BEntities;
import team.rusty.bumpkinbatch.registry.BItems;
import team.rusty.bumpkinbatch.registry.BWorldGen;
import team.rusty.bumpkinbatch.worldgen.biome.PumpkinPatchRegion;
import terrablender.api.RegionType;
import terrablender.api.Regions;

@Mod(BumpkinBatch.ID)
public class BumpkinBatch {
    public static final String ID = "bumpkinbatch";

    public BumpkinBatch() {
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

        BBlocks.BLOCKS.register(mod);
        BItems.ITEMS.register(mod);
        BEntities.ENTITIES.register(mod);
        BWorldGen.BIOMES.register(mod);
        BWorldGen.FEATURES.register(mod);
        BWorldGen.STRUCTURES.register(mod);

        mod.addListener(BEntities::addEntityAttribs);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientHandler.register();
        }
        mod.addListener(BumpkinBatch::commonSetup);
    }

    private static void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() ->
        {
            // Given we only add two biomes, we should keep our weight relatively low.
            Regions.register(new PumpkinPatchRegion(new ResourceLocation(ID, "biome_provider"), RegionType.OVERWORLD, 2));
        });
    }
}
