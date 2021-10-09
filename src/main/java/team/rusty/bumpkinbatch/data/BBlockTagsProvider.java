package team.rusty.bumpkinbatch.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import team.rusty.bumpkinbatch.BumpkinBatch;

public class BBlockTagsProvider extends BlockTagsProvider{
    public BBlockTagsProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen, BumpkinBatch.ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

    }
}
