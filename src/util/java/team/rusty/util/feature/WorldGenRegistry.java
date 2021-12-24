package team.rusty.util.feature;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Registry to handle ConfiguredFeature, PlacedFeature, ConfiguredStructureFeature,
 * or any other world gen object type that lacks a Forge registry.
 *
 *
 *
 * @author TheDarkColour
 */
public final class WorldGenRegistry {
    private final HashMap<String, Lazy<? extends ConfiguredFeature<?, ?>>> configuredFeatures = new HashMap<>();
    private final HashMap<String, Lazy<? extends PlacedFeature>> placedFeatures = new HashMap<>();
    private final String modid;

    public WorldGenRegistry(String modid) {
        this.modid = modid;
    }

    public void register(IEventBus modBus) {
        modBus.addListener((FMLCommonSetupEvent e) -> e.enqueueWork(() -> {
            // Register configured features
            for (var entry : configuredFeatures.entrySet()) {
                Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(modid, entry.getKey()), entry.getValue().get());
            }

            // Register placed features
            for (var entry : placedFeatures.entrySet()) {
                Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(modid, entry.getKey()), entry.getValue().get());
            }
        }));
    }

    public <FC extends FeatureConfiguration, F extends Feature<FC>> Lazy<ConfiguredFeature<FC, F>> configuredFeature(String name, Supplier<ConfiguredFeature<FC, F>> supplier) {
        return lazyRegister(name, configuredFeatures, supplier);
    }

    public Lazy<PlacedFeature> placedFeature(String name, Supplier<PlacedFeature> supplier) {
        return lazyRegister(name, placedFeatures, supplier);
    }

    private static <T, V extends T> Lazy<V> lazyRegister(String name, Map<String, Lazy<? extends T>> registry, Supplier<V> supplier) {
        Lazy<V> lazy = Lazy.of(supplier);

        registry.put(name, lazy);

        return lazy;
    }
}
