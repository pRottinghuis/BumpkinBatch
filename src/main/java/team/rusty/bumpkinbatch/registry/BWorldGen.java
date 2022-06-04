package team.rusty.bumpkinbatch.registry;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
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

    /** Configured features */
    public static final RegistryObject<ConfiguredFeature<?, ?>> GRAVESTONE_CONFIGURED_FEATURE = FEATURES.configuredFeature("gravestone", () -> new ConfiguredFeature(GRAVESTONE_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));

    /** Placed features */
    public static final RegistryObject<PlacedFeature> GRAVESTONE_PLACED_FEATURE = FEATURES.placedFeature("gravestone", () -> new PlacedFeature(GRAVESTONE_CONFIGURED_FEATURE.getHolder().get(),
            List.of(HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES), InSquarePlacement.spread(), PlacementUtils.countExtra(0, 0.5f, 1))));

    /** Structures */
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> HALLOWEEN_STRUCTURE = STRUCTURES.register("halloween_structure", HalloweenStructure::new);
    public static final RegistryObject<StructureFeature<?>> HAUNTED_HOUSE_STRUCTURE = STRUCTURES.register("haunted_house_structure", HauntedHouseStructure::new);
}
