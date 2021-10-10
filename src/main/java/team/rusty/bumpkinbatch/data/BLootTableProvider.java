package team.rusty.bumpkinbatch.data;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BLootTableProvider extends LootTableProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator generator;
    private final Map<Block, LootTable.Builder> blockTables = Maps.newHashMap();

    public BLootTableProvider(DataGenerator generator) {
        super(generator);
        this.generator = generator;
    }

    @Override
    public void run(HashCache cache) {
        // add tables

        HashMap<ResourceLocation, LootTable> namespacedTables = new HashMap<>(blockTables.size());

        for (Map.Entry<Block, LootTable.Builder> entry : blockTables.entrySet()) {
            // Add tables to the block loot parameter set automatically
            namespacedTables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootContextParamSets.BLOCK).build());
        }

        writeLootTables(namespacedTables, cache);
    }

    private void writeLootTables(Map<ResourceLocation, LootTable> tables, HashCache cache) {
        Path outputFolder = generator.getOutputFolder();

        for (Map.Entry<ResourceLocation, LootTable> entry : tables.entrySet()) {
            Path path = outputFolder.resolve("data/" + entry.getKey().getNamespace() + "/loot_tables/" + entry.getKey().getPath() + ".json");

            try {
                DataProvider.save(GSON, cache, LootTables.serialize(entry.getValue()), path);
            } catch (Exception e) {
                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        }
    }
}
