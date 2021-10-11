package team.rusty.bumpkinbatch.worldgen;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.worldgen.feature.CrossFeature;
import team.rusty.bumpkinbatch.worldgen.feature.GraveStoneFeature;
import team.rusty.util.worldgen.biome.AbstractBiome;
import team.rusty.util.worldgen.biome.AbstractBiomeRegistry;

public class BWorldGen {
    public static final AbstractBiomeRegistry BIOMES = new AbstractBiomeRegistry(BumpkinBatch.ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BumpkinBatch.ID);

    /** Biomes */
    public static final AbstractBiome PUMPKIN_PATCH = BIOMES.register("pumpkin_patch", new PumpkinPatchBiome());

    /** Features */
    public static final RegistryObject<Feature<BlockStateConfiguration>> CROSS_FEATURE = FEATURES.register("cross_feature", () -> new CrossFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> GRAVESTONE_FEATURE = FEATURES.register("gravestone", () -> new GraveStoneFeature(NoneFeatureConfiguration.CODEC));

}
