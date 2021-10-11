package team.rusty.util.worldgen.biome;

import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Base biome class for creating a biome.
 *
 * @author TheDarkColour
 */
public abstract class AbstractBiome {
    /** Default biome effects for new biomes */
    public static final BiomeSpecialEffects DEFAULT_EFFECTS = new BiomeSpecialEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.7F)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build();

    /** Vanilla biome category */
    protected Biome.BiomeCategory category = Biome.BiomeCategory.PLAINS;
    /** Negative depth counts as ocean */
    protected float depth = 0.1f;
    /** Vertical stretch */
    protected float scale = 0.1f;
    /** Biome water/sky/fog colors, particles, and music. Basically anything clientside */
    protected BiomeSpecialEffects effects = DEFAULT_EFFECTS;
    /** Which type of weather effects are displayed when it is raining. */
    protected Biome.Precipitation precipitation = Biome.Precipitation.RAIN;
    protected Biome.TemperatureModifier tempMod = Biome.TemperatureModifier.NONE;
    /** Affects melting of ice and snow */
    protected float temperature = 0.0f;
    /** Affects grass/foliage color. If greater than {@literal 0.85f}, the biome is considered humid. */
    protected float downfall = 0.0f;

    /** Copy + paste from VanillaBiomes */
    public static int calculateSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    /**
     * Configure biome properties, add features/structures/carvers, add mob spawns, etc.
     */
    public abstract void configure(BiomeGenerationSettingsBuilder generation, MobSpawnSettings.Builder spawns);

    /**
     * @return The spawn configuration of this biome, or empty if the biome should not spawn naturally.
     */
    public List<SpawnEntry> getSpawnEntries() {
        return Collections.emptyList();
    }

    public Biome.ClimateSettings getClimate() {
        return new Biome.ClimateSettings(precipitation, temperature, tempMod, downfall);
    }

    public Biome.BiomeCategory getCategory() {
        return category;
    }

    public float getDepth() {
        return depth;
    }

    public float getScale() {
        return scale;
    }

    public BiomeSpecialEffects getEffects() {
        return effects;
    }

    public record SpawnEntry(BiomeManager.BiomeType type, int weight) {
        public static SpawnEntry of(BiomeManager.BiomeType type, int weight) {
            return new SpawnEntry(type, weight);
        }
    }
}
