package team.rusty.bumpkinbatch.worldgen;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import team.rusty.bumpkinbatch.registry.BEntities;
import team.rusty.bumpkinbatch.registry.BWorldGen;
import team.rusty.util.biome.AbstractBiome;

import java.util.List;
import java.util.Set;

public class PumpkinPatchBiome extends AbstractBiome {
    @Override
    public void configure(BiomeGenerationSettingsBuilder generation, MobSpawnSettings.Builder spawns) {
        // Properties
        precipitation = Biome.Precipitation.RAIN;
        category = Biome.BiomeCategory.PLAINS;
        //depth = 0.1f;
        //scale = 0.05f;
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

        BiomeDefaultFeatures.addDefaultCarversAndLakes(generation);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generation);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generation);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generation); // this was added twice in the other versions OOPS
        BiomeDefaultFeatures.addDefaultSprings(generation);
        BiomeDefaultFeatures.addSurfaceFreezing(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        BiomeDefaultFeatures.addPlainGrass(generation);
        BiomeDefaultFeatures.addDefaultGrass(generation);


        // generation.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS));

        // carving stations
        //generation.addStructureStart(BWorldGen.CARVING_STATION.get().configured());
        //generation.addStructureStart(BWorldGen.SPIDER_NEST.get().configured());
        //generation.addStructureStart(BWorldGen.HAUNTED_HOUSE.get().configured());

        // Grave Stones
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, BWorldGen.GRAVESTONE_FEATURE.get()
                .configured(NoneFeatureConfiguration.INSTANCE)
                        .placed(HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                                InSquarePlacement.spread(),
                                PlacementUtils.countExtra(0, 0.5f, 1)));  // (# guaranteed, extra chance, extra count)

        // Pumpkins
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH
                .configured(new RandomPatchConfiguration(new SimpleStateProvider(Blocks.PUMPKIN.defaultBlockState()), SimpleBlockPlacer.INSTANCE)
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

        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BEntities.REAPER.get(), 5, 1, 1));
    }

    @Override
    public List<SpawnEntry> getSpawnEntries() {
        return List.of(SpawnEntry.of(BiomeManager.BiomeType.WARM, 3));
    }
}
