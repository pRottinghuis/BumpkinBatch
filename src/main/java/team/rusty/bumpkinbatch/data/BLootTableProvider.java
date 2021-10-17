package team.rusty.bumpkinbatch.data;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.rusty.bumpkinbatch.registry.BItems;
import team.rusty.bumpkinbatch.worldgen.structure.HalloweenStructureElement;

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

        // Rottinghuis I need you to finish this
        namespacedTables.put(HalloweenStructureElement.CANDY_CHEST, new LootTable.Builder()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2.0f, 8.0f))
                        .add(LootItem.lootTableItem(Items.COOKIE).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                        .add(LootItem.lootTableItem(BItems.GUMMY_BOARS.get()).setWeight(15))
                        .add(LootItem.lootTableItem(BItems.ORE_O.get()).setWeight(10))

                ).setParamSet(LootContextParamSets.CHEST).build());

        // Igloo exampe loot table
        //LootTable.lootTable()
        //        // This pool runs between 2 to 8 times
        //        // It picks an item each time, higher weight has a higher probability of being picked
        //        // An item can be in multiple slots by using SetItemCountFunction
        //        // I have them grouped in the image on discord
        //        .withPool(LootPool.lootPool()
        //                .setRolls(UniformGenerator.between(2.0F, 8.0F))
        //                .add(LootItem.lootTableItem(Items.APPLE).setWeight(15)
        //                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
        //                .add(LootItem.lootTableItem(Items.COAL).setWeight(15)
        //                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
        //                .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(10)
        //                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
        //                .add(LootItem.lootTableItem(Items.STONE_AXE).setWeight(2))
        //                .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10))
        //                .add(LootItem.lootTableItem(Items.EMERALD))
        //                .add(LootItem.lootTableItem(Items.WHEAT).setWeight(10)
        //                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))))
        //        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
        //                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)));

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
