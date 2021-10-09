package team.rusty.bumpkinbatch.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.registry.BItems;

public class BLanguageProvider extends LanguageProvider {
    public BLanguageProvider(DataGenerator gen) {
        super(gen, BumpkinBatch.ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        //Candies
        addItem(BItems.EAST_TWICKS, "East Twicks");
        addItem(BItems.WEST_TWICKS, "East Twicks");
        addItem(BItems.ORESEOES, "Ores-e-oes");
        addItem(BItems.GUMMY_BOARS, "Gummy Boars");
        addItem(BItems.CREEPER_CRACKS, "Creeper Cracks");
    }
}
