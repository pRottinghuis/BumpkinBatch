package team.rusty.bumpkinbatch.registry;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.worldgen.custom.PumpkinPatchBiome;
import team.rusty.bumpkinbatch.worldgen.feature.CrossFeature;
import team.rusty.bumpkinbatch.worldgen.feature.GraveStoneFeature;
import team.rusty.bumpkinbatch.worldgen.structure.HalloweenStructure;
import team.rusty.bumpkinbatch.worldgen.structure.HalloweenStructureElement;
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
    public static final DeferredRegister<StructurePoolElementType<?>> STRUCTURE_POOL_ELEMENTS = DeferredRegister.create(Registry.STRUCTURE_POOL_ELEMENT_REGISTRY, BumpkinBatch.ID);

    /** Biomes */
    public static final AbstractBiome PUMPKIN_PATCH = BIOMES.register("pumpkin_patch", new PumpkinPatchBiome());

    /** Features */
    public static final RegistryObject<CrossFeature> CROSS_FEATURE = FEATURES.feature("cross_feature", () -> new CrossFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<GraveStoneFeature> GRAVESTONE_FEATURE = FEATURES.feature("gravestone", () -> new GraveStoneFeature(NoneFeatureConfiguration.CODEC));

    /** Configured features */
    public static final RegistryObject<ConfiguredFeature<?, ?>> GRAVESTONE_CONFIGURED = FEATURES.configuredFeature("gravestone", () -> new ConfiguredFeature(GRAVESTONE_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
    public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> LEAF_PILE_CONFIGURED = FEATURES.configuredFeature("leaf_pile",
            () -> new ConfiguredFeature(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.OAK_LEAVES.defaultBlockState().setValue(BlockStateProperties.PERSISTENT, true))), List.of(Blocks.GRASS_BLOCK))));
    public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PUMPKIN_PILE_CONFIGURED = FEATURES.configuredFeature("pumpkin_pile",
            () -> new ConfiguredFeature(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PUMPKIN)), List.of(Blocks.GRASS_BLOCK))));
    public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BUSH_CONFIGURED = FEATURES.configuredFeature("bush",
            () -> new ConfiguredFeature(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(Blocks.OAK_LOG),
                    new StraightTrunkPlacer(1, 0, 0),
                    BlockStateProvider.simple(Blocks.OAK_LEAVES),
                    new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 1)).build()));
    public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> LONE_TREE_CONFIGURED = FEATURES.configuredFeature(
            "lone_tree",
            () -> new ConfiguredFeature(Feature.TREE, TreeFeatures.FANCY_OAK.value().config()));

    /** Placed features */
    public static final RegistryObject<PlacedFeature> GRAVESTONE_PLACED_FEATURE = FEATURES.placedFeature("gravestone", () -> new PlacedFeature(GRAVESTONE_CONFIGURED.getHolder().get(),
            List.of(HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES), InSquarePlacement.spread(), PlacementUtils.countExtra(0, 0.1f, 1))));
    public static final RegistryObject<PlacedFeature> FANCY_OAKS_PLACED_FEATURE = FEATURES.placedFeature("fancy_oaks", () -> new PlacedFeature(cast(TreeFeatures.FANCY_OAK),
            VegetationPlacements.treePlacement( PlacementUtils.countExtra(0, 0.1f, 1), Blocks.OAK_SAPLING)));
    public static final RegistryObject<PlacedFeature> LEAF_PILE = FEATURES.placedFeature("leaf_pile", () -> new PlacedFeature(cast(LEAF_PILE_CONFIGURED.getHolder().get()),
            List.of(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP)));
    public static final RegistryObject<PlacedFeature> PUMPKIN_PILE = FEATURES.placedFeature("pumpkin_pile", () -> new PlacedFeature(cast(PUMPKIN_PILE_CONFIGURED.getHolder().get()),
            List.of(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP)));
    public static final RegistryObject<PlacedFeature>  BUSH = FEATURES.placedFeature("bush", () -> new PlacedFeature(cast(BUSH_CONFIGURED.getHolder().get()),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.5F, 1), Blocks.OAK_SAPLING)));
    public static final RegistryObject<PlacedFeature>  LONE_TREE = FEATURES.placedFeature("lone_tree", () -> new PlacedFeature(cast(LONE_TREE_CONFIGURED.getHolder().get()),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.OAK_SAPLING)));

    /** Structures */
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> HALLOWEEN_STRUCTURE = STRUCTURES.register("halloween_structure", HalloweenStructure::new);
    public static final RegistryObject<StructureFeature<?>> HAUNTED_HOUSE_STRUCTURE = STRUCTURES.register("haunted_house_structure", HauntedHouseStructure::new);

    /** Halloween Element to keep data blocks */
    public static final RegistryObject<StructurePoolElementType<HalloweenStructureElement>> HALLOWEEN_ELEMENT = STRUCTURE_POOL_ELEMENTS.register("halloween_element", () -> (StructurePoolElementType<HalloweenStructureElement>) () -> HalloweenStructureElement.CODEC);

    // boo generics boo
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object thing) {
        return (T) thing;
    }
}
