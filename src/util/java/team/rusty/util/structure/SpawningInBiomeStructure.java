package team.rusty.util.structure;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

import java.util.List;

public interface SpawningInBiomeStructure {
    List<ResourceKey<Biome>> getSpawnBiomes();

    ConfiguredStructureFeature<?, ?> configuredStructure();
}
