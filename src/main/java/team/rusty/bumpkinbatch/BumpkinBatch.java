package team.rusty.bumpkinbatch;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import team.rusty.bumpkinbatch.client.ClientHandler;
import team.rusty.bumpkinbatch.registry.BBlocks;
import team.rusty.bumpkinbatch.registry.BEntities;
import team.rusty.bumpkinbatch.registry.BItems;
import team.rusty.bumpkinbatch.registry.BStructures;
import team.rusty.bumpkinbatch.worldgen.BWorldGen;

@Mod(BumpkinBatch.ID)
public class BumpkinBatch {
    public static final String ID = "bumpkinbatch";

    public BumpkinBatch() {
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

        BBlocks.BLOCKS.register(mod);
        BItems.ITEMS.register(mod);
        BWorldGen.BIOMES.register(mod);
        BEntities.ENTITIES.register(mod);
        BWorldGen.FEATURES.register(mod);
        BStructures.STRUCTURES.register(mod);


        mod.addListener(BEntities::addEntityAttribs);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientHandler.register();
        }
    }
}
