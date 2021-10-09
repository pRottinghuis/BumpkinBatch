package team.rusty.bumpkinbatch.worldgen;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import team.rusty.util.worldgen.AbstractBiome;

import java.util.Set;

public class PumpkinPatchBiome extends AbstractBiome {
    @Override
    public void configure(BiomeGenerationSettingsBuilder generation, MobSpawnSettings.Builder spawns) {
        // Properties
        precipitation = Biome.Precipitation.RAIN;
        category = Biome.BiomeCategory.PLAINS;
        depth = 0.1f;
        scale = 0.2f;
        temperature = 0.9f;
        downfall = 1.0f;

        // Effects
        effects = new BiomeSpecialEffects.Builder()
                .skyColor(0xffcdab)
                .fogColor(0xffcdab)
                .waterColor(0x3f76e4)
                .waterFogColor(0x50533)
                .build();

        generation.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS));
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH
                .configured(new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(Blocks.PUMPKIN.defaultBlockState()), SimpleBlockPlacer.INSTANCE)
                        .whitelist(Set.of(Blocks.GRASS_BLOCK))
                        .build()));
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BWorldGen.CROSS_FEATURE.get()
                .configured(new BlockStateConfiguration(Blocks.COBBLESTONE.defaultBlockState()))
                .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.3f, 1))));

        BiomeDefaultFeatures.monsters(spawns, 19, 1, 100);
    }
}
