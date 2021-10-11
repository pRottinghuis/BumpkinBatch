package team.rusty.bumpkinbatch.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class GraveStoneFeature extends Feature<NoneFeatureConfiguration> {
    public GraveStoneFeature(Codec<NoneFeatureConfiguration> context) {
        super(context);
    }

    private Block[] bricks = {Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS};
    private Block[] cobbleWalls= {Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL};
    private Random random = new Random();

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        var level = context.level();
        // with SQUARE and HEIGHTMAP feature decorators,
        // this position is the block above ground level.
        var pos = context.origin();

        // Cursor to place blocks
        var mutable = pos.mutable();

        // Used to randomize if structure is rotated
        int rotation = random.nextInt(2);

        // Chooses either regular or rotated structure
        if (rotation == 0) {
            // don't spawn in the water or any other non-solid (non occluding) block for any blocks under the feature
            if (canPlace(level, pos)) return false;
            type1(level, mutable, bricks[random.nextInt(bricks.length)], cobbleWalls[random.nextInt(cobbleWalls.length)]);
        } else {
            if (canPlaceRotated(level, pos)) return false;
            type1Rotated(level, mutable, bricks[random.nextInt(bricks.length)], cobbleWalls[random.nextInt(cobbleWalls.length)]);
        }
        return false;
    }

    private boolean canPlace(WorldGenLevel level, BlockPos pos) {
        return (!level.getBlockState(pos.below()).canOcclude()) || (!level.getBlockState(pos.offset(-1, -1, 0)).canOcclude()) || (!level.getBlockState(pos.offset(1, -1, 0)).canOcclude());
    }

    private boolean canPlaceRotated(WorldGenLevel level, BlockPos pos) {
        return (!level.getBlockState(pos.below()).canOcclude()) || (!level.getBlockState(pos.offset(0, -1, -1)).canOcclude()) || (!level.getBlockState(pos.offset(0, -1, 1)).canOcclude());
    }

    // helper to choose random block option for one type of block. ex. mossy cobble or regular cobble
    private Block randBlock(Block[] blocks) {
        return blocks[random.nextInt(blocks.length)];
    }

    private void type1(WorldGenLevel level, BlockPos.MutableBlockPos mutable, Block brick, Block cobbleWall) {
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
        setBlock(level, mutable, randBlock(cobbleWalls).defaultBlockState().setValue(BlockStateProperties.EAST_WALL, WallSide.LOW));
        mutable.move(1, 0, 0);
        setBlock(level, mutable, randBlock(bricks).defaultBlockState());
        mutable.move(1, 0, 0);
        setBlock(level, mutable, randBlock(cobbleWalls).defaultBlockState().setValue(BlockStateProperties.WEST_WALL, WallSide.LOW));

        //top layer
        mutable.move(-2, 1, 0);
        setBlock(level, mutable, Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
        mutable.move(1, 0, 0);
        setBlock(level, mutable, Blocks.STONE_BRICKS.defaultBlockState());
        mutable.move(1, 0, 0);
        setBlock(level, mutable, Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
    }

    private void type1Rotated(WorldGenLevel level, BlockPos.MutableBlockPos mutable, Block brick, Block cobbleWall) {
        //draws from left to right from bottom to top
        //bottom layer
        mutable.move(0, 0, -1);
        setBlock(level, mutable, Blocks.POLISHED_ANDESITE.defaultBlockState());
        mutable.move(0, 0, 1);
        setBlock(level, mutable, Blocks.ANDESITE.defaultBlockState());
        mutable.move(0, 0, 1);
        setBlock(level, mutable, Blocks.POLISHED_ANDESITE.defaultBlockState());

        // middle layer
        mutable.move(0, 1, -2);
        setBlock(level, mutable, cobbleWall.defaultBlockState().setValue(BlockStateProperties.SOUTH_WALL, WallSide.LOW));
        mutable.move(0, 0, 1);
        setBlock(level, mutable, brick.defaultBlockState());
        mutable.move(0, 0, 1);
        setBlock(level, mutable, cobbleWall.defaultBlockState().setValue(BlockStateProperties.NORTH_WALL, WallSide.LOW));

        //top layer
        mutable.move(0, 1, -2);
        setBlock(level, mutable, Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
        mutable.move(0, 0, 1);
        setBlock(level, mutable, Blocks.STONE_BRICKS.defaultBlockState());
        mutable.move(0, 0, 1);
        setBlock(level, mutable, Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
    }
}
