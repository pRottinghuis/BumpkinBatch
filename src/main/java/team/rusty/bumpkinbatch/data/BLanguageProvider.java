package team.rusty.bumpkinbatch.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import team.rusty.bumpkinbatch.BumpkinBatch;

public class BLanguageProvider extends LanguageProvider {
    public BLanguageProvider(DataGenerator gen) {
        super(gen, BumpkinBatch.ID, "en_us");
    }

    @Override
    protected void addTranslations() {

    }
}
