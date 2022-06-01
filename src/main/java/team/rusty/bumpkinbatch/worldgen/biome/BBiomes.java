package team.rusty.bumpkinbatch.worldgen.biome;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import team.rusty.bumpkinbatch.BumpkinBatch;

public class BBiomes {

    public static final ResourceKey<Biome> HOT_RED = register("hot_red");

    private static ResourceKey<Biome> register(String name)
    {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(BumpkinBatch.ID, name));
    }

}
