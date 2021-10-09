package team.rusty.bumpkinbatch;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BumpkinBatch.ID)
public class BumpkinBatch {

    public static final String ID = "bumpkinbatch";

    public BumpkinBatch() {
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

    }
}
