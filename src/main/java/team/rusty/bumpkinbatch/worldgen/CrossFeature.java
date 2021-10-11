package team.rusty.bumpkinbatch.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class CrossFeature extends Feature<BlockStateConfiguration> {
    public CrossFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {

        var level = context.level();
        var config = context.config();
        // get the block type from the config
        // you can use your own config if you want more states to specify,
        // but the cross uses a single state for the entire structure
        var state = config.state;
        // with SQUARE and HEIGHTMAP feature decorators,
        // this position is the block above ground level.
        var pos = context.origin();

        // don't spawn in the water or any other non-solid (non occluding) block
        if (!level.getBlockState(pos.below()).canOcclude()) return false;

        // Cursor to place blocks
        var mutable = pos.mutable();

        // Place five blocks...
        for (var i = 0; i < 5; ++i) {
            // Place a block first...
            setBlock(level, mutable, state);
            // ...then move up the cursor
            mutable.move(0, 1, 0);
        }

        // Because the for loop above moves the cursor AFTER placing,
        // it will be located in the air block above the last block placed.
        // So to move it down a block from the tip, the cursor needs to move
        // down by TWO blocks instead of one.
        mutable.move(0, -2, 0);

        // Move the cursor to the left and place one side
        setBlock(level, mutable.move(-1, 0, 0), state);
        // Move the two blocks over to the other side and place a block
        setBlock(level, mutable.move( 2, 0, 0), state);

        return false;
    }
}
