package team.rusty.bumpkinbatch.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.rusty.bumpkinbatch.BumpkinBatch;

public class BItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BumpkinBatch.ID);

    public static final RegistryObject<Item> EAST_TWICKS = ITEMS.register("east_twicks", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(BFoods.EAST_TWICKS)));
    public static final RegistryObject<Item> WEST_TWICKS = ITEMS.register("west_twicks", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(BFoods.WEST_TWICKS)));
    public static final RegistryObject<Item> ORE_O = ITEMS.register("ore_o", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(BFoods.ORE_O)));
    public static final RegistryObject<Item> GUMMY_BOARS = ITEMS.register("gummy_boars", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(BFoods.GUMMY_BOARS)));
    public static final RegistryObject<Item> CREEPER_STICK = ITEMS.register("creeper_stick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(BFoods.CREEPER_STICK)));

    public static final RegistryObject<Item> REAPER_SPAWN_EGG = ITEMS.register("reaper_spawn_egg", () -> new ForgeSpawnEggItem(BEntities.REAPER, 0xd6d6d6, 0x8c5b3f, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // Swiftness - Ore - oes
    // Night Vision - Gummy Boars
    // confusion - east and west twicks
    // poison - Sizzlers
    // regeneration - chocolate smooches
    // Strength - Creeper cracks



}
