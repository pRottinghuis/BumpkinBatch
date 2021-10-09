package team.rusty.bumpkinbatch.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import team.rusty.bumpkinbatch.BumpkinBatch;

public class BItemModelProvider extends ItemModelProvider {
    public BItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BumpkinBatch.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
