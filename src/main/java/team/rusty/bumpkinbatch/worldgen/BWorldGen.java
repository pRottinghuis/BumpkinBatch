package team.rusty.bumpkinbatch.worldgen;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.rusty.bumpkinbatch.BumpkinBatch;

import java.util.Set;

public class BWorldGen {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, BumpkinBatch.ID);

    public static final RegistryObject<Biome> PUMPKIN_PATCH = BIOMES.register("pumpkin_patch", BWorldGen::makePumpkinPatch);

    private static Biome makePumpkinPatch() {
        var builder = new Biome.BiomeBuilder();
        var generations = new BiomeGenerationSettings.Builder();
        var mobSpawns = new MobSpawnSettings.Builder();

        generations.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS));

        generations.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH
                .configured(new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(Blocks.PUMPKIN.defaultBlockState()), SimpleBlockPlacer.INSTANCE)
                        .tries(64)
                        .whitelist(Set.of(Blocks.GRASS_BLOCK))
                        .build()));

        BiomeDefaultFeatures.monsters(mobSpawns, 19, 1, 100);

        builder.generationSettings(generations.build());
        builder.mobSpawnSettings(mobSpawns.build());
        return builder.build();
    }
}
