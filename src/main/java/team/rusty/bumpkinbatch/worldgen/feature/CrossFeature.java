package team.rusty.bumpkinbatch.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;

/**
 * @author TheDarkColour
 */
public class CrossFeature extends Feature<BlockStateFeatureConfig> {
    public CrossFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    /**
     * @param level World access
     * @param generator unused
     * @param random unused
     * @param pos with {@link Placement#SQUARE} and {@link Placement#HEIGHTMAP} feature decorators, this position is the block above ground level.
     * @param config get the block type from the config, used for the entire structure
     * @return I don't know if it really matters what you return here
     */
    @Override
    public boolean place(ISeedReader level, ChunkGenerator generator, Random random, BlockPos pos, BlockStateFeatureConfig config) {
        BlockState state = config.state;

        // don't spawn in the water or any other non-solid (non occluding) block
        if (!level.getBlockState(pos.below()).canOcclude()) return false;

        // Cursor to place blocks
        BlockPos.Mutable mutable = pos.mutable();

        // Place five blocks...
        for (int i = 0; i < 5; ++i) {
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
