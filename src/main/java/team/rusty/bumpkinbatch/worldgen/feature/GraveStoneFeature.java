package team.rusty.bumpkinbatch.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class GraveStoneFeature extends Feature<NoneFeatureConfiguration> {
    public GraveStoneFeature(Codec<NoneFeatureConfiguration> context) {
        super(context);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        var level = context.level();
        // with SQUARE and HEIGHTMAP feature decorators,
        // this position is the block above ground level.
        var pos = context.origin();

        // don't spawn in the water or any other non-solid (non occluding) block for any blocks under the feature
        if ((!level.getBlockState(pos.below()).canOcclude()) || (!level.getBlockState(pos.offset(-1, -1, 0)).canOcclude()) || (!level.getBlockState(pos.offset(1, -1, 0)).canOcclude())) return false;

        // Cursor to place blocks
        var mutable = pos.mutable();

        Random random = new Random();

        Block[] bricks = {Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS};
        Block[] cobbleWalls= {Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL};


        //draws from left to right from bottom to top
        //bottom layer
        mutable.move(-1, 0, 0);
        setBlock(level, mutable, Blocks.POLISHED_ANDESITE.defaultBlockState());
        mutable.move(1, 0, 0);
        setBlock(level, mutable, Blocks.ANDESITE.defaultBlockState());
        mutable.move(1, 0, 0);
        setBlock(level, mutable, Blocks.POLISHED_ANDESITE.defaultBlockState());

        // middle layer
        mutable.move(-2, 1, 0);
        setBlock(level, mutable, cobbleWalls[random.nextInt(cobbleWalls.length)].defaultBlockState().setValue(BlockStateProperties.EAST_WALL, WallSide.LOW));
        mutable.move(1, 0, 0);
        setBlock(level, mutable, bricks[random.nextInt(bricks.length)].defaultBlockState());
        mutable.move(1, 0, 0);
        setBlock(level, mutable, cobbleWalls[random.nextInt(cobbleWalls.length)].defaultBlockState().setValue(BlockStateProperties.WEST_WALL, WallSide.LOW));

        //top layer
        mutable.move(-2, 1, 0);
        setBlock(level, mutable, Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
        mutable.move(1, 0, 0);
        setBlock(level, mutable, Blocks.STONE_BRICKS.defaultBlockState());
        mutable.move(1, 0, 0);
        setBlock(level, mutable, Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));



        return false;
    }


}
