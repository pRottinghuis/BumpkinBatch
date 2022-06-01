package team.rusty.bumpkinbatch.worldgen;

import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import team.rusty.bumpkinbatch.worldgen.biome.BBiomes;
import team.rusty.bumpkinbatch.worldgen.biome.BOverworldBioms;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BWorldEvents {

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event)
    {
        IForgeRegistry<Biome> registry = event.getRegistry();
        registry.register(BOverworldBioms.hotRed().setRegistryName(BBiomes.HOT_RED.location()));
    }

}
