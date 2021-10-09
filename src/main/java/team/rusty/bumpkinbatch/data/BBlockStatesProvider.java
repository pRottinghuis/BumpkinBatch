package team.rusty.bumpkinbatch.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import team.rusty.bumpkinbatch.BumpkinBatch;

public class BBlockStatesProvider extends BlockStateProvider {
    public BBlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BumpkinBatch.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
