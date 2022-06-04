package team.rusty.bumpkinbatch.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import team.rusty.bumpkinbatch.registry.BWorldGen;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class PumpkinPatchRegion extends Region {
    public PumpkinPatchRegion(ResourceLocation name, RegionType type, int weight) {
        super(name, type, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addBiome(mapper,
                ParameterUtils.Temperature.NEUTRAL,
                ParameterUtils.Humidity.NEUTRAL,
                ParameterUtils.Continentalness.INLAND,
                ParameterUtils.Erosion.FULL_RANGE,
                ParameterUtils.Weirdness.FULL_RANGE,
                ParameterUtils.Depth.SURFACE,
                0.0f,
                BWorldGen.PUMPKIN_PATCH.getResourceKey());
    }
}
