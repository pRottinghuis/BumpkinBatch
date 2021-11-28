package team.rusty.bumpkinbatch.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import team.rusty.bumpkinbatch.BumpkinBatch;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = BumpkinBatch.ID)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeClient()) {
            // Models, assets, etc.
            dataGen.addProvider(new BItemModelProvider(dataGen, helper));
            dataGen.addProvider(new BLanguageProvider(dataGen));
        }
        if (event.includeServer()) {
            // Loot tables, recipes, tags, etc.
            dataGen.addProvider(new BLootTableProvider(dataGen));
        }
    }
}
