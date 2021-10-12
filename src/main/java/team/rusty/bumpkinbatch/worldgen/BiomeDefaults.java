package team.rusty.bumpkinbatch.worldgen;

import com.google.common.base.Suppliers;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import team.rusty.bumpkinbatch.registry.BStructures;

import java.util.function.Supplier;

public class BiomeDefaults extends BiomeDefaultFeatures {

    public static final Supplier<ConfiguredStructureFeature<?, ?>> CARVING_STATION_CONFIGURED = Suppliers.memoize(() -> {
        return BStructures.CARVING_STATION.get().configured(NoneFeatureConfiguration.INSTANCE);
    });

}
