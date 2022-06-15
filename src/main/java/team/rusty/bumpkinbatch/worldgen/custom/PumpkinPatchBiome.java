package team.rusty.bumpkinbatch.worldgen.custom;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.data.worldgen.placement.VillagePlacements;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import team.rusty.bumpkinbatch.registry.BEntities;
import team.rusty.bumpkinbatch.registry.BWorldGen;
import team.rusty.util.biome.AbstractBiome;

import java.util.List;

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
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, BWorldGen.GRAVESTONE_PLACED_FEATURE.getHolder().get());  // (# guaranteed, extra chance, extra count)

        // Pumpkins
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, VillagePlacements.PILE_HAY_VILLAGE);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BWorldGen.FANCY_OAKS_PLACED_FEATURE.getHolder().get());
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BWorldGen.LEAF_PILE.getHolder().get());
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BWorldGen.BUSH.getHolder().get());
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BWorldGen.LONE_TREE.getHolder().get());
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BWorldGen.PUMPKIN_PILE.getHolder().get());
        /*generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH
                .configured(new RandomPatchConfiguration(new SimpleStateProvider(Blocks.PUMPKIN.defaultBlockState()), SimpleBlockPlacer.INSTANCE)
                        .whitelist(Set.of(Blocks.GRASS_BLOCK))
                        .build()));
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
                */

        BiomeDefaultFeatures.monsters(spawns, 19, 1, 100, false);
        BiomeDefaultFeatures.commonSpawns(spawns);

        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BEntities.REAPER.get(), 5, 1, 1));
    }

    @Override
    public List<SpawnEntry> getSpawnEntries() {
        return List.of(SpawnEntry.of(BiomeManager.BiomeType.WARM, 3));
    }
}
