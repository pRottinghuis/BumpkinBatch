package team.rusty.bumpkinbatch.registry;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.worldgen.custom.PumpkinPatchBiome;
import team.rusty.bumpkinbatch.worldgen.feature.CrossFeature;
import team.rusty.bumpkinbatch.worldgen.feature.GraveStoneFeature;
import team.rusty.bumpkinbatch.worldgen.structure.HalloweenStructure;
import team.rusty.bumpkinbatch.worldgen.structure.HauntedHouseStructure;
import team.rusty.util.biome.AbstractBiome;
import team.rusty.util.biome.AbstractBiomeRegistry;
import team.rusty.util.feature.FeatureRegistry;

import java.util.List;

public class BWorldGen {
    //public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BumpkinBatch.ID);
    public static final AbstractBiomeRegistry BIOMES = new AbstractBiomeRegistry(BumpkinBatch.ID);
    public static final FeatureRegistry FEATURES = new FeatureRegistry(BumpkinBatch.ID);
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_FEATURE_REGISTRY, BumpkinBatch.ID);

    /** Biomes */
    public static final AbstractBiome PUMPKIN_PATCH = BIOMES.register("pumpkin_patch", new PumpkinPatchBiome());

    /** Features */
    public static final RegistryObject<CrossFeature> CROSS_FEATURE = FEATURES.feature("cross_feature", () -> new CrossFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<GraveStoneFeature> GRAVESTONE_FEATURE = FEATURES.feature("gravestone", () -> new GraveStoneFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<RandomPatchFeature> PUMPKINS = FEATURES.feature("pumpkins", () -> new RandomPatchFeature(RandomPatchConfiguration.CODEC));

    /** Configured features */
    public static final RegistryObject<ConfiguredFeature<?, ?>> GRAVESTONE_CONFIGURED_FEATURE = FEATURES.configuredFeature("gravestone", () -> new ConfiguredFeature(GRAVESTONE_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
    //public static final RegistryObject<ConfiguredFeature<?, ?>> PUMPKINS_CONFIGURED_FEATURE = FEATURES.configuredFeature("pumpkins", () -> {
    //    FeatureUtils.register("patch_grass_jungle",
    //            Feature.RANDOM_PATCH,
    //            new RandomPatchConfiguration(32,
    //                    7,
    //                    3,
    //                    PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
    //                            new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.GRASS.defaultBlockState(), 3).add(Blocks.FERN.defaultBlockState(), 1))),
    //                            BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.not(BlockPredicate.matchesBlock(Blocks.PODZOL, new BlockPos(0, -1, 0)))))))
    //    return PlacementUtils.filtered(Feature.SIMPLE_BLOCK, )
    //    //return new RandomPatchConfiguration(64,
    //    //        7,
    //    //        3,
    //    //        PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
    //    //                new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PUMPKIN)),
    //    //                BlockPredicate.allOf(BlockPredicate.replaceable(),
    //    //                        BlockPredicate.matchesBlock(Blocks.GRASS_BLOCK, new BlockPos(0, -1, 0)))));
    //});

    /** Placed features */
    public static final RegistryObject<PlacedFeature> GRAVESTONE_PLACED_FEATURE = FEATURES.placedFeature("gravestone", () -> new PlacedFeature(GRAVESTONE_CONFIGURED_FEATURE.getHolder().get(),
            List.of(HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES), InSquarePlacement.spread(), PlacementUtils.countExtra(0, 0.5f, 1))));
    public static final RegistryObject<PlacedFeature> FANCY_OAKS_PLACED_FEATURE = FEATURES.placedFeature("fancy_oaks", () -> {
        return new PlacedFeature(cast(TreeFeatures.FANCY_OAK), List.of(
            HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES), InSquarePlacement.spread(), PlacementUtils.countExtra(0, 0.3f, 1)
        ));
    });

    /** Structures */
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> HALLOWEEN_STRUCTURE = STRUCTURES.register("halloween_structure", HalloweenStructure::new);
    public static final RegistryObject<StructureFeature<?>> HAUNTED_HOUSE_STRUCTURE = STRUCTURES.register("haunted_house_structure", HauntedHouseStructure::new);

    // boo generics boo
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object thing) {
        return (T) thing;
    }
}
