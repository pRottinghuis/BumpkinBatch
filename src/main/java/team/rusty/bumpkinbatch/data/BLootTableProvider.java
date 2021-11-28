package team.rusty.bumpkinbatch.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.registry.BItems;
import team.rusty.bumpkinbatch.worldgen.structure.HalloweenStructure;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BLootTableProvider extends LootTableProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator generator;
    private final Map<Block, LootTable.Builder> blockTables = new HashMap<>();

    public BLootTableProvider(DataGenerator generator) {
        super(generator);
        this.generator = generator;
    }

    @Override
    public void run(DirectoryCache cache) {
        // add tables

        HashMap<ResourceLocation, LootTable> namespacedTables = new HashMap<>(blockTables.size());

        for (Map.Entry<Block, LootTable.Builder> entry : blockTables.entrySet()) {
            // Add tables to the block loot parameter set automatically
            namespacedTables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootParameterSets.BLOCK).build());
        }

        // Candy chest loot
        namespacedTables.put(HalloweenStructure.CANDY_CHEST, new LootTable.Builder()
                .withPool(LootPool.lootPool()
                        .setRolls(RandomValueRange.between(2.0f, 8.0f))
                        .add(ItemLootEntry.lootTableItem(Items.COOKIE).setWeight(15)
                                .apply(SetCount.setCount(RandomValueRange.between(1.0f, 3.0f))))
                        .add(ItemLootEntry.lootTableItem(BItems.GUMMY_BOARS.get()).setWeight(15))
                        .add(ItemLootEntry.lootTableItem(BItems.CREEPER_STICK.get()).setWeight(10)
                                .apply(SetCount.setCount(RandomValueRange.between(2.0f, 3.0f))))
                        .add(ItemLootEntry.lootTableItem(BItems.ORE_O.get()).setWeight(10)
                                .apply(SetCount.setCount(RandomValueRange.between(1.0f, 3.0f))))
                        .add(ItemLootEntry.lootTableItem(BItems.EAST_TWICKS.get()).setWeight(16))
                        .add(ItemLootEntry.lootTableItem(BItems.WEST_TWICKS.get()).setWeight(14))
                        .add(ItemLootEntry.lootTableItem(Items.GOLD_INGOT).setWeight(5))
                        .add(ItemLootEntry.lootTableItem(Items.COAL).setWeight(20)
                                .apply(SetCount.setCount(RandomValueRange.between(1.0f, 3.0f))))
                        .add(ItemLootEntry.lootTableItem(Items.EMERALD).setWeight(5)))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.DIAMOND)))
                .setParamSet(LootParameterSets.CHEST).build());

        // Reaper loot
        namespacedTables.put(new ResourceLocation(BumpkinBatch.ID, "entities/reaper"), new LootTable.Builder()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.LEATHER)
                                .apply(SetCount.setCount(RandomValueRange.between(0.0f, 2.0f)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.BONE)
                                .apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(RandomValueRange.between(0.0F, 1.0f))
                        .add(ItemLootEntry.lootTableItem(BItems.EAST_TWICKS.get()).setWeight(10))
                        .add(ItemLootEntry.lootTableItem(BItems.WEST_TWICKS.get()).setWeight(10))
                        .add(ItemLootEntry.lootTableItem(BItems.CREEPER_STICK.get()).setWeight(10))
                        .add(ItemLootEntry.lootTableItem(BItems.ORE_O.get()).setWeight(10))
                        .add(ItemLootEntry.lootTableItem(BItems.GUMMY_BOARS.get()).setWeight(10))
                        .add(ItemLootEntry.lootTableItem(Items.GOLDEN_APPLE).setWeight(5)))
                .setParamSet(LootParameterSets.ENTITY).build());

        LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.ARROW)
                                .apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.BONE)
                                .apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))));

        // Igloo exampe loot table
        //LootTable.lootTable()
        //        // This pool runs between 2 to 8 times
        //        // It picks an item each time, higher weight has a higher probability of being picked
        //        // An item can be in multiple slots by using SetCount
        //        // I have them grouped in the image on discord
        //        .withPool(LootPool.lootPool()
        //                .setRolls(RandomValueRange.between(2.0F, 8.0F))
        //                .add(ItemLootEntry.lootTableItem(Items.APPLE).setWeight(15)
        //                        .apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F))))
        //                .add(ItemLootEntry.lootTableItem(Items.COAL).setWeight(15)
        //                        .apply(SetCount.setCount(RandomValueRange.between(1.0F, 4.0F))))
        //                .add(ItemLootEntry.lootTableItem(Items.GOLD_NUGGET).setWeight(10)
        //                        .apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F))))
        //                .add(ItemLootEntry.lootTableItem(Items.STONE_AXE).setWeight(2))
        //                .add(ItemLootEntry.lootTableItem(Items.ROTTEN_FLESH).setWeight(10))
        //                .add(ItemLootEntry.lootTableItem(Items.EMERALD))
        //                .add(ItemLootEntry.lootTableItem(Items.WHEAT).setWeight(10)
        //                        .apply(SetCount.setCount(RandomValueRange.between(2.0F, 3.0F)))))
        //        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
        //                .add(ItemLootEntry.lootTableItem(Items.GOLDEN_APPLE)));

        writeLootTables(namespacedTables, cache);
    }

    private void writeLootTables(Map<ResourceLocation, LootTable> tables, DirectoryCache cache) {
        Path outputFolder = generator.getOutputFolder();

        for (Map.Entry<ResourceLocation, LootTable> entry : tables.entrySet()) {
            Path path = outputFolder.resolve("data/" + entry.getKey().getNamespace() + "/loot_tables/" + entry.getKey().getPath() + ".json");

            try {
                IDataProvider.save(GSON, cache, LootTableManager.serialize(entry.getValue()), path);
            } catch (Exception e) {
                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        }
    }
}
