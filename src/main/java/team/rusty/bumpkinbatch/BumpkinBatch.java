package team.rusty.bumpkinbatch;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import team.rusty.bumpkinbatch.registry.BBlocks;
import team.rusty.bumpkinbatch.registry.BItems;
import team.rusty.bumpkinbatch.worldgen.BWorldGen;

@Mod(BumpkinBatch.ID)
public class BumpkinBatch {
    public static final String ID = "bumpkinbatch";

    public BumpkinBatch() {
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

        BBlocks.BLOCKS.register(mod);
        BItems.ITEMS.register(mod);
        BWorldGen.BIOMES.register(mod);
        BWorldGen.FEATURES.register(mod);
    }
}
