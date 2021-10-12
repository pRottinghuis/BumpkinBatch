package team.rusty.bumpkinbatch.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.rusty.bumpkinbatch.BumpkinBatch;

import java.util.function.Supplier;

public class BStructures {
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, BumpkinBatch.ID);

    /**
     * Registers the ConfiguredStructure and configures spacing settings for your structure.
     *
     * @param structure registry object of your structure feature
     * @param configured memoized supplier that contains your configured structure feature
     * @param adjustGround whether ground is generated below this structure
     * @param spacing average number of chunks between structures
     * @param separation minimum number of chunks between structures
     * @param structureSeed <b>UNIQUE GENERATION SEED</b> that must be different from other structure seeds.
     */
    public static void configureStructure(RegistryObject<? extends StructureFeature<?>> structure, Supplier<ConfiguredStructureFeature<?, ?>> configured, boolean adjustGround, int spacing, int separation, int structureSeed) {
        StructureFeatureConfiguration config = new StructureFeatureConfiguration(spacing, separation, structureSeed);

        // Register configured feature
        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, structure.getId(), configured.get());
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getId().toString(), structure.get());
        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            settings.getValue().structureSettings().structureConfig().put(structure.get(), config);
        });

        if (adjustGround) {
            StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder().addAll(StructureFeature.NOISE_AFFECTING_FEATURES).add(structure.get()).build();
        }

        StructureSettings.DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder().putAll(StructureSettings.DEFAULTS).put(structure.get(), config).build();
    }
}
}
