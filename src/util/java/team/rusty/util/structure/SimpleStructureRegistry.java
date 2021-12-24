package team.rusty.util.structure;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author TheDarkColour
 */
public final class SimpleStructureRegistry {
    /** Biome deferred register */
    private final DeferredRegister<StructureFeature<?>> deferredRegister;
    /** Map of structure information */
    private final Map<ResourceLocation, StructureInfo<StructureFeature<?>>> structureInfo;

    public SimpleStructureRegistry(String modId) {
        this.deferredRegister = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, modId);
        this.structureInfo = new HashMap<>();
    }

    /**
     * Registers this biome registry to the mod bus
     */
    public void register(IEventBus modBus) {
        deferredRegister.register(modBus);
        modBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::worldLoad);
    }

    @SuppressWarnings("rawtypes")
    public <T extends StructureFeature<?>> RegistryObject<T> register(String name, Function<T, ConfiguredStructureFeature<?, ?>> configured, Supplier<T> structure, boolean adjustGround, int spacing, int separation, int structureSeed) {
        var object = deferredRegister.register(name, structure);
        structureInfo.put(object.getId(), new StructureInfo(configured, adjustGround, new StructureFeatureConfiguration(spacing, separation, structureSeed)));
        return object;
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            for (var entry : deferredRegister.getEntries()) {
                var value = entry.get();
                var id = entry.getId();
                var info = structureInfo.get(id);

                // register to vanilla
                Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, id, info.configured().apply(value));
                // register to vanilla again
                StructureFeature.STRUCTURES_REGISTRY.put(id.toString(), value);

                // add bearding to the structure ground
                if (info.adjustGround()) {
                    StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder().addAll(StructureFeature.NOISE_AFFECTING_FEATURES).add(value).build();
                }
            }
        });
    }

    private void worldLoad(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel level) {
            var settings = level.getChunkSource().getGenerator().getSettings();
            var configurations = settings.structureConfig();

            // In case world type has immutable configurations (like flat world)
            if (!(configurations instanceof ImmutableMap)) {
                var associatedBiomes = new HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>>();

                for (var structure : deferredRegister.getEntries()) {
                    if (structure.get() instanceof SpawningInBiomeStructure biomeStructure) {
                        var biomes = biomeStructure.getSpawnBiomes();

                        for (var biome : biomes) {
                            associateBiomeToConfiguredStructure(associatedBiomes, biomeStructure.configuredStructure(), biome);
                            System.out.printf("Associated structure %s with Biome %s %n", structure.getId(), biome);
                        }
                    }

                    // Add structure placement settings to world instead of to immutable StructureSettings.DEFAULT
                    configurations.put(structure.get(), structureInfo.get(structure.getId()).config());
                }

                var newBiomeAssociationMap = ImmutableMap.<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>>builder();
                settings.configuredStructures.entrySet().forEach(newBiomeAssociationMap::put);

                // Add our structures to the structure map/multimap and set the world to use this combined map/multimap.
                associatedBiomes.forEach((key, value) -> newBiomeAssociationMap.put(key, ImmutableMultimap.copyOf(value)));

                // Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
                settings.configuredStructures = newBiomeAssociationMap.build();
            }
        }
    }

    /**
     * Helper method that handles setting up the map to multimap relationship to help prevent issues.
     */
    private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> associatedBiomes,
                                                            ConfiguredStructureFeature<?, ?> structure,
                                                            ResourceKey<Biome> biome) {
        associatedBiomes.putIfAbsent(structure.feature, HashMultimap.create());
        // Biomes for the current structure
        var structureAssociatedBiomes = associatedBiomes.get(structure.feature);
        if (structureAssociatedBiomes.containsValue(biome)) {
            System.out.printf("""
                                Detected 2 ConfiguredStructureFeatures that share the same base StructureFeature trying to be added to same biome. One will be prevented from spawning.
                                This issue happens with vanilla too and is why a Snowy Village and Plains Village cannot spawn in the same biome because they both use the Village base structure.
                                The two conflicting ConfiguredStructures are: %s, %s
                                The biome that is attempting to be shared: %s
                            %n""",
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(structure),
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(structureAssociatedBiomes.entries().stream().filter(e -> e.getValue() == biome).findFirst().get().getKey()),
                    biome
            );
        } else {
            structureAssociatedBiomes.put(structure, biome);
        }
    }

    private record StructureInfo<T extends StructureFeature<?>>(
            Function<T, ConfiguredStructureFeature<?, ?>> configured,
            boolean adjustGround,
            StructureFeatureConfiguration config
    ) {}
}
