package team.rusty.bumpkinbatch.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.registry.BItems;
import team.rusty.bumpkinbatch.registry.BWorldGen;
import team.rusty.util.worldgen.biome.AbstractBiomeRegistry;

public class BLanguageProvider extends LanguageProvider {
    public BLanguageProvider(DataGenerator gen) {
        super(gen, BumpkinBatch.ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        //Candies
        addItem(BItems.EAST_TWICKS, "East Twicks");
        addItem(BItems.WEST_TWICKS, "West Twicks");
        addItem(BItems.ORE_O, "Ore-O");
        addItem(BItems.GUMMY_BOARS, "Gummy Boars");
        addItem(BItems.CREEPER_CRACKS, "Creeper Cracks");

        //Biome
        add("biome.bumpkinbatch.pumpkin_patch", "Pumpkin Patch");
    }
}
