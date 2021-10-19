package team.rusty.bumpkinbatch.registry;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.worldgen.PumpkinPatchBiome;
import team.rusty.bumpkinbatch.worldgen.feature.CrossFeature;
import team.rusty.bumpkinbatch.worldgen.feature.GraveStoneFeature;
import team.rusty.bumpkinbatch.worldgen.structure.HalloweenStructure;
import team.rusty.util.worldgen.biome.AbstractBiome;
import team.rusty.util.worldgen.biome.AbstractBiomeRegistry;
import team.rusty.util.worldgen.structure.SimpleStructure;
import team.rusty.util.worldgen.structure.SimpleStructureRegistry;

public class BWorldGen {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BumpkinBatch.ID);
    public static final AbstractBiomeRegistry BIOMES = new AbstractBiomeRegistry(BumpkinBatch.ID);
    public static final SimpleStructureRegistry STRUCTURES = new SimpleStructureRegistry(BumpkinBatch.ID);

    /** Biomes */
    public static final AbstractBiome PUMPKIN_PATCH = BIOMES.register("pumpkin_patch", new PumpkinPatchBiome());

    /** Features */
    public static final RegistryObject<Feature<BlockStateConfiguration>> CROSS_FEATURE = FEATURES.register("cross_feature", () -> new CrossFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> GRAVESTONE_FEATURE = FEATURES.register("gravestone", () -> new GraveStoneFeature(NoneFeatureConfiguration.CODEC));

    /** Structures */
    public static final RegistryObject<SimpleStructure> CARVING_STATION = STRUCTURES.register("carving_station", HalloweenStructure::new, SimpleStructure::configured, true, 12, 4, 0x69f33d);
    public static final RegistryObject<SimpleStructure> HAUNTED_HOUSE = STRUCTURES.register("haunted_house", HalloweenStructure::new, SimpleStructure::configured, true, 15, 6, 0x69f78d);
    public static final RegistryObject<SimpleStructure> SPIDER_NEST = STRUCTURES.register("spider_nest", HalloweenStructure::new, SimpleStructure::configured, true, 8, 2, 0x34f22a);
}
