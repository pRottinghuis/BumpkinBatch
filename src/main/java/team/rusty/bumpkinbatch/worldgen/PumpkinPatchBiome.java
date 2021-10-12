package team.rusty.bumpkinbatch.worldgen;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Features;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.fml.loading.FMLEnvironment;
import team.rusty.util.worldgen.biome.AbstractBiome;

import java.util.List;
import java.util.Set;

public class PumpkinPatchBiome extends AbstractBiome {
    @Override
    public void configure(BiomeGenerationSettingsBuilder generation, MobSpawnSettings.Builder spawns) {
        // Properties
        precipitation = Biome.Precipitation.RAIN;
        category = Biome.BiomeCategory.PLAINS;
        depth = 0.1f;
        scale = 0.1f;
        temperature = 0.9f;
        downfall = 0.5f;

        // Effects
        effects = new BiomeSpecialEffects.Builder()
                .skyColor(0xffcdab)
                .fogColor(0xffcdab)
                .waterColor(0x3f76e4)
                .waterFogColor(0x50533)
                .grassColorOverride(0xc5d141)
                .foliageColorOverride(0xc5d141)
                .build();

        BiomeDefaultFeatures.addDefaultCrystalFormations(generation);
        BiomeDefaultFeatures.addDefaultCarvers(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generation);
        BiomeDefaultFeatures.addDefaultLakes(generation);
        BiomeDefaultFeatures.addDefaultSprings(generation);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generation);

        generation.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS));

        // carving stations
        generation.addStructureStart(BiomeDefaults.CARVING_STATION_CONFIGURED.get());
        // Grave Stones
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, BWorldGen.GRAVESTONE_FEATURE.get()
                .configured(NoneFeatureConfiguration.INSTANCE)
                .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES)))
                .decorated(FeatureDecorator.SQUARE.configured(new NoneDecoratorConfiguration()))
                .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.5f, 1))));  // (# spawned guaranteed per chunk, chance spawn extra per chunk, count extra per chunk)
        // Pumpkins
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH
                .configured(new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(Blocks.PUMPKIN.defaultBlockState()), SimpleBlockPlacer.INSTANCE)
                        .whitelist(Set.of(Blocks.GRASS_BLOCK))
                        .build()));
        // Cross
//        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, BWorldGen.CROSS_FEATURE.get()
//                .configured(new BlockStateConfiguration(Blocks.COBBLESTONE.defaultBlockState()))
//                .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES)))
//                .decorated(FeatureDecorator.SQUARE.configured(new NoneDecoratorConfiguration()))
//                .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.05f, 1))));
        // Hay bales
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Feature.BLOCK_PILE
                .configured(new BlockPileConfiguration(new SimpleStateProvider(Blocks.HAY_BLOCK.defaultBlockState())))
                .decorated(FeatureDecorator.SQUARE.configured(new NoneDecoratorConfiguration()))
                .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES)))
                .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.1f, 1))));
        // Lone trees
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.FANCY_OAK
                .decorated(FeatureDecorator.SQUARE.configured(new NoneDecoratorConfiguration()))
                .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES)))
                .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.3f, 1))));
        // Leaf piles
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Feature.BLOCK_PILE
                .configured(new BlockPileConfiguration(new SimpleStateProvider(Blocks.OAK_LEAVES.defaultBlockState().setValue(BlockStateProperties.PERSISTENT, true))))
                .decorated(FeatureDecorator.SQUARE.configured(new NoneDecoratorConfiguration()))
                .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES)))
                .decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(2, 0.4f, 1))));
        // Bush tree
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Feature.TREE
                .configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(Blocks.OAK_LOG.defaultBlockState()),
                        new StraightTrunkPlacer(1, 0, 0),
                        new SimpleStateProvider(Blocks.OAK_LEAVES.defaultBlockState()),
                        new SimpleStateProvider(Blocks.OAK_SAPLING.defaultBlockState()),
                        new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                        new TwoLayersFeatureSize(0, 0, 0))).build())
                .decorated(FeatureDecorator.SQUARE.configured(new NoneDecoratorConfiguration()))
                .decorated(FeatureDecorator.HEIGHTMAP.configured(new HeightmapConfiguration(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES))));

        BiomeDefaultFeatures.monsters(spawns, 19, 1, 100);
        BiomeDefaultFeatures.commonSpawns(spawns);
    }

    @Override
    public List<SpawnEntry> getSpawnEntries() {
        return List.of(SpawnEntry.of(BiomeManager.BiomeType.WARM, 1));
    }
}
