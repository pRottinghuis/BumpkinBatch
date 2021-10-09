package team.rusty.bumpkinbatch.worldgen;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
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
        var effects = new BiomeSpecialEffects.Builder();

        // Biome Properties
        builder.precipitation(Biome.Precipitation.RAIN);
        builder.biomeCategory(Biome.BiomeCategory.PLAINS);
        builder.depth(0.1f);
        builder.scale(0.2f);
        builder.temperature(0.9f);
        builder.downfall(1.0f);

        // Biome effects
        effects.skyColor(0xffcdab);
        effects.fogColor(0xffcdab);
        effects.waterColor(0x3f76e4);
        effects.waterFogColor(0x50533);

        // World gen
        generations.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS));
        generations.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH
                .configured(new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(Blocks.PUMPKIN.defaultBlockState()), SimpleBlockPlacer.INSTANCE)
                        .whitelist(Set.of(Blocks.GRASS_BLOCK))
                        .build()));

        // Mob spawns
        BiomeDefaultFeatures.monsters(mobSpawns, 19, 1, 100);

        builder.generationSettings(generations.build());
        builder.mobSpawnSettings(mobSpawns.build());
        builder.specialEffects(effects.build());
        return builder.build();
    }
}
