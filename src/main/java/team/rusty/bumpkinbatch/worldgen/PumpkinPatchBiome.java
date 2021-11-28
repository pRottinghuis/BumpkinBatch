package team.rusty.bumpkinbatch.worldgen;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import team.rusty.bumpkinbatch.registry.BEntities;
import team.rusty.bumpkinbatch.registry.BWorldGen;
import team.rusty.util.worldgen.biome.AbstractBiome;

import java.util.Collections;
import java.util.List;

public class PumpkinPatchBiome extends AbstractBiome {
    @Override
    public void configure(BiomeGenerationSettingsBuilder generation, MobSpawnInfo.Builder spawns) {
        // Properties
        precipitation = Biome.RainType.RAIN;
        category = Biome.Category.PLAINS;
        depth = 0.1f;
        scale = 0.05f;
        temperature = 0.9f;
        downfall = 0.5f;

        // Effects
        effects = new BiomeAmbience.Builder()
                .skyColor(0xffcdab)
                .fogColor(0xffcdab)
                .waterColor(0x3f76e4)
                .waterFogColor(0x50533)
                .grassColorOverride(0xc5d141)
                .foliageColorOverride(0xc5d141)
                .build();

        DefaultBiomeFeatures.addDefaultCarvers(generation);
        DefaultBiomeFeatures.addDefaultOres(generation);
        DefaultBiomeFeatures.addDefaultSoftDisks(generation);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(generation);
        DefaultBiomeFeatures.addDefaultLakes(generation);
        DefaultBiomeFeatures.addDefaultSprings(generation);
        DefaultBiomeFeatures.addDefaultMonsterRoom(generation);
        DefaultBiomeFeatures.addPlainGrass(generation);
        DefaultBiomeFeatures.addDefaultGrass(generation);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(generation);
        DefaultBiomeFeatures.addSurfaceFreezing(generation);


        generation.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS));

        // carving stations
        generation.addStructureStart(BWorldGen.CARVING_STATION.get().configured());
        generation.addStructureStart(BWorldGen.SPIDER_NEST.get().configured());
        generation.addStructureStart(BWorldGen.HAUNTED_HOUSE.get().configured());

        // Grave Stones
        generation.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, BWorldGen.GRAVESTONE_FEATURE.get()
                .configured(NoFeatureConfig.INSTANCE)
                .decorated(Placement.HEIGHTMAP.configured(NoPlacementConfig.INSTANCE))
                .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
                .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.5f, 1))));  // (# spawned guaranteed per chunk, chance spawn extra per chunk, count extra per chunk)
        // Pumpkins
        generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH
                .configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.PUMPKIN.defaultBlockState()), SimpleBlockPlacer.INSTANCE)
                        .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
                        .build()));
        // Cross
//        generation.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, BWorldGen.CROSS_FEATURE.get()
//                .configured(new BlockStateConfiguration(Blocks.COBBLESTONE.defaultBlockState()))
//                .decorated(Placement.HEIGHTMAP))
//                .decorated(Placement.SQUARE.configured(new NoneDecoratorConfiguration()))
//                .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.05f, 1))));
        // Hay bales
        generation.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.BLOCK_PILE
                .configured(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(Blocks.HAY_BLOCK.defaultBlockState())))
                .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
                .decorated(Placement.HEIGHTMAP.configured(NoPlacementConfig.INSTANCE))
                .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.1f, 1))));
        // Lone trees
        generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FANCY_OAK
                .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
                .decorated(Placement.HEIGHTMAP.configured(NoPlacementConfig.INSTANCE))
                .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.3f, 1))));
        // Leaf piles
        generation.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.BLOCK_PILE
                .configured(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(Blocks.OAK_LEAVES.defaultBlockState().setValue(BlockStateProperties.PERSISTENT, true))))
                .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
                .decorated(Placement.HEIGHTMAP.configured(NoPlacementConfig.INSTANCE))
                .decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(2, 0.4f, 1))));
        // Bush tree
        generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.TREE
                .configured((new BaseTreeFeatureConfig.Builder(
                        new SimpleBlockStateProvider(Blocks.OAK_LOG.defaultBlockState()),
                        new SimpleBlockStateProvider(Blocks.OAK_LEAVES.defaultBlockState()),
                        new BushFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(1), 2),
                        new StraightTrunkPlacer(1, 0, 0),
                        new TwoLayerFeature(0, 0, 0))).build())
                .decorated(Placement.SQUARE.configured(NoPlacementConfig.INSTANCE))
                .decorated(Placement.HEIGHTMAP.configured(NoPlacementConfig.INSTANCE)));

        DefaultBiomeFeatures.monsters(spawns, 19, 1, 100);
        DefaultBiomeFeatures.commonSpawns(spawns);

        spawns.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BEntities.REAPER.get(), 5, 1, 1));
    }

    @Override
    public List<SpawnEntry> getSpawnEntries() {
        return Collections.singletonList(SpawnEntry.of(BiomeManager.BiomeType.WARM, 3));
    }
}
