package team.rusty.bumpkinbatch.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.worldgen.custom.PumpkinPatchBiome;
import team.rusty.bumpkinbatch.worldgen.feature.CrossFeature;
import team.rusty.bumpkinbatch.worldgen.feature.GraveStoneFeature;
import team.rusty.bumpkinbatch.worldgen.structure.HalloweenStructure;
import team.rusty.util.biome.AbstractBiome;
import team.rusty.util.biome.AbstractBiomeRegistry;
import team.rusty.util.feature.WorldGenRegistry;
import team.rusty.util.structure.SimpleStructureRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BWorldGen {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BumpkinBatch.ID);
    public static final AbstractBiomeRegistry BIOMES = new AbstractBiomeRegistry(BumpkinBatch.ID);
    public static final WorldGenRegistry WORLD_GEN = new WorldGenRegistry(BumpkinBatch.ID);
    public static final SimpleStructureRegistry STRUCTURES = new SimpleStructureRegistry(BumpkinBatch.ID);

    /** Biomes */
    public static final AbstractBiome PUMPKIN_PATCH = BIOMES.register("pumpkin_patch", new PumpkinPatchBiome());

    /** Features */
    public static final RegistryObject<Feature<BlockStateConfiguration>> CROSS_FEATURE = FEATURES.register("cross_feature", () -> new CrossFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> GRAVESTONE_FEATURE = FEATURES.register("gravestone", () -> new GraveStoneFeature(NoneFeatureConfiguration.CODEC));

    /** Structures */
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> CARVING_STATION = STRUCTURES.register("carving_station", () -> (new HalloweenStructure(JigsawConfiguration.CODEC)));
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> HAUNTED_HOUSE = STRUCTURES.register("haunted_house", () -> (new HalloweenStructure(JigsawConfiguration.CODEC)));
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> SPIDER_NEST = STRUCTURES.register("spider_nest", () -> (new HalloweenStructure(JigsawConfiguration.CODEC)));

    /** Configured features */
    public static final Lazy<? extends ConfiguredFeature<RandomPatchConfiguration, ?>> SCORCHED_FIRE_PATCH_CONFIGURED = WORLD_GEN.configuredFeature("scorched_fire_patch_configured", () -> {
        return Feature.RANDOM_PATCH.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.SOUL_FIRE))), List.of(ABlocks.SCORCHED_EARTH.get())));/*Feature.RANDOM_PATCH
                .configured(new RandomPatchConfiguration(64, 7, 3, () -> {
                    new SimpleBlockFeature()
                }).whitelist(ImmutableSet.of(ABlocks.SCORCHED_EARTH.get())).noProjection().build()).decorated(Features.Decorators.FIRE).countRandom(5);*/
    });

    public static void setupStructures() {
        setupMapSpacingAndLand(
                HAUNTED_HOUSE.get(), /* The instance of the structure */
                new StructureFeatureConfiguration(15 /* average distance apart in chunks between spawn attempts */,
                        6 /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE*/,
                        0x69f33d /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                true);

        setupMapSpacingAndLand(
                CARVING_STATION.get(),
                new StructureFeatureConfiguration(12,
                        4,
                        0x69f78d),
                true);

        setupMapSpacingAndLand(
                SPIDER_NEST.get(),
                new StructureFeatureConfiguration(8,
                        2,
                        0x69f22d),
                true);
    }

    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(
            F structure,
            StructureFeatureConfiguration StructureFeatureConfiguration,
            boolean transformSurroundingLand)
    {
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        if(transformSurroundingLand){
            StructureFeature.NOISE_AFFECTING_FEATURES =
                    ImmutableList.<StructureFeature<?>>builder()
                            .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
                            .add(structure)
                            .build();
        }

        StructureSettings.DEFAULTS =
                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structure, StructureFeatureConfiguration)
                        .build();

        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();

            if(structureMap instanceof ImmutableMap){
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, StructureFeatureConfiguration);
                settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, StructureFeatureConfiguration);
            }
        });
    }
}
