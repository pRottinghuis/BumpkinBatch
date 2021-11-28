package team.rusty.bumpkinbatch.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallHeight;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GraveStoneFeature extends Feature<NoFeatureConfig> {
    public GraveStoneFeature(Codec<NoFeatureConfig> context) {
        super(context);
    }

    private Block[] bricks = {Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS};
    private Block[] cobbleWalls = {Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL};
    private Block[] cobble = {Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE};
    private Random random = new Random();
    
    @Override
    public boolean place(ISeedReader level, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        // Cursor to place blocks
        BlockPos.Mutable mutable = pos.mutable();

        // chooses which gravestone type is chosen
        int type = random.nextInt(3);

        // Used to randomize if structure is rotated
        boolean rotated = random.nextBoolean();

        if (type == 0) {
            // Chooses either regular or rotated structure
            if (rotated) {
                // don't spawn in the water or any other non-solid (non occluding) block for any blocks under the feature
                if (canPlace(level, pos)) return false;
                type0(level, mutable, true);
            } else {
                if (canPlaceRotated(level, pos)) return false;
                type0(level, mutable, false);
            }
        } else if (type == 1) {
            if (rotated) {
                // don't spawn in the water or any other non-solid (non occluding) block for any blocks under the feature
                if (canPlace(level, pos)) return false;
                type1(level, mutable, true);
            } else {
                if (canPlaceRotated(level, pos)) return false;
                type1(level, mutable, false);
            }
        } else {
            if (rotated) {
                // don't spawn in the water or any other non-solid (non occluding) block for any blocks under the feature
                if (canPlace(level, pos)) return false;
                type2(level, mutable, true);
            } else {
                if (canPlaceRotated(level, pos)) return false;
                type2(level, mutable, false);
            }
        }
        return false;
    }

    private boolean canPlace(ISeedReader level, BlockPos pos) {
        return (!level.getBlockState(pos.below()).canOcclude()) || (!level.getBlockState(pos.offset(-1, -1, 0)).canOcclude()) || (!level.getBlockState(pos.offset(1, -1, 0)).canOcclude());
    }

    private boolean canPlaceRotated(ISeedReader level, BlockPos pos) {
        return (!level.getBlockState(pos.below()).canOcclude()) || (!level.getBlockState(pos.offset(0, -1, -1)).canOcclude()) || (!level.getBlockState(pos.offset(0, -1, 1)).canOcclude());
    }

    // helper to choose random block option for one type of block. ex. mossy cobble or regular cobble
    private Block randBlock(Block[] blocks) {
        return blocks[random.nextInt(blocks.length)];
    }



    private void type0(ISeedReader level, BlockPos.Mutable mutable, boolean rotated) {
        if (rotated) {
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
            setBlock(level, mutable, randBlock(cobbleWalls).defaultBlockState().setValue(BlockStateProperties.EAST_WALL, WallHeight.LOW));
            mutable.move(1, 0, 0);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());
            mutable.move(1, 0, 0);
            setBlock(level, mutable, randBlock(cobbleWalls).defaultBlockState().setValue(BlockStateProperties.WEST_WALL, WallHeight.LOW));

            //top layer
            mutable.move(-2, 1, 0);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
            mutable.move(1, 0, 0);
            setBlock(level, mutable, Blocks.STONE_BRICKS.defaultBlockState());
            mutable.move(1, 0, 0);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
        } else {
            // rotated feature

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
            setBlock(level, mutable, randBlock(cobbleWalls).defaultBlockState().setValue(BlockStateProperties.SOUTH_WALL, WallHeight.LOW));
            mutable.move(0, 0, 1);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());
            mutable.move(0, 0, 1);
            setBlock(level, mutable, randBlock(cobbleWalls).defaultBlockState().setValue(BlockStateProperties.NORTH_WALL, WallHeight.LOW));

            //top layer
            mutable.move(0, 1, -2);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
            mutable.move(0, 0, 1);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());
            mutable.move(0, 0, 1);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
        }
    }

    private void type1(ISeedReader level, BlockPos.Mutable mutable, boolean rotated) {
        if (rotated) {
            //draws from left to right from bottom to top
            //bottom layer
            mutable.move(-1, 0, 0);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());
            mutable.move(1, 0, 0);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());
            mutable.move(1, 0, 0);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());

            // middle layer
            mutable.move(-2, 1, 0);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_STAIRS.defaultBlockState().setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.STRAIGHT).setValue(BlockStateProperties.HALF, Half.TOP).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST));
            mutable.move(1, 0, 0);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());
            mutable.move(1, 0, 0);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_STAIRS.defaultBlockState().setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.STRAIGHT).setValue(BlockStateProperties.HALF, Half.TOP).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST));

            //top layer
            mutable.move(-2, 1, 0);
            setBlock(level, mutable, Blocks.STONE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
            mutable.move(1, 0, 0);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE.defaultBlockState());
            mutable.move(1, 0, 0);
            setBlock(level, mutable, Blocks.STONE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
        } else {
            // rotated feature

            //draws from left to right from bottom to top
            //bottom layer
            mutable.move(0, 0, -1);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());
            mutable.move(0, 0, 1);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());
            mutable.move(0, 0, 1);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());

            // middle layer
            mutable.move(0, 1, -2);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_STAIRS.defaultBlockState().setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.STRAIGHT).setValue(BlockStateProperties.HALF, Half.TOP).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH));
            mutable.move(0, 0, 1);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());
            mutable.move(0, 0, 1);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_STAIRS.defaultBlockState().setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.STRAIGHT).setValue(BlockStateProperties.HALF, Half.TOP).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));

            //top layer
            mutable.move(0, 1, -2);
            setBlock(level, mutable, Blocks.STONE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
            mutable.move(0, 0, 1);
            setBlock(level, mutable, Blocks.STONE_BRICKS.defaultBlockState());
            mutable.move(0, 0, 1);
            setBlock(level, mutable, Blocks.STONE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
        }
    }

    private void type2(ISeedReader level, BlockPos.Mutable mutable, boolean rotated) {
        if (rotated) {
            //draws from left to right from bottom to top
            //bottom layer
            mutable.move(-1, 0, 0);
            setBlock(level, mutable, Blocks.ANDESITE.defaultBlockState());
            mutable.move(1, 0, 0);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());
            mutable.move(1, 0, 0);
            setBlock(level, mutable, Blocks.ANDESITE.defaultBlockState());

            // middle layer
            mutable.move(-2, 1, 0);
            setBlock(level, mutable, Blocks.STONE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
            mutable.move(1, 0, 0);
            setBlock(level, mutable, randBlock(cobble).defaultBlockState());
            mutable.move(1, 0, 0);
            setBlock(level, mutable, Blocks.STONE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));

            //second middle layer
            mutable.move(-2, 1, 0);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_STAIRS.defaultBlockState().setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.STRAIGHT).setValue(BlockStateProperties.HALF, Half.TOP).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST));
            mutable.move(1, 0, 0);
            setBlock(level, mutable, randBlock(cobbleWalls).defaultBlockState().setValue(BlockStateProperties.EAST_WALL, WallHeight.LOW).setValue(BlockStateProperties.WEST_WALL, WallHeight.LOW));
            mutable.move(1, 0, 0);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_STAIRS.defaultBlockState().setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.STRAIGHT).setValue(BlockStateProperties.HALF, Half.TOP).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST));

            //top layer
            mutable.move(-1, 1, 0);
            setBlock(level, mutable, randBlock(cobbleWalls).defaultBlockState());
        } else {
            // rotated feature

            //draws from left to right from bottom to top
            //bottom layer
            mutable.move(0, 0, -1);
            setBlock(level, mutable, Blocks.ANDESITE.defaultBlockState());
            mutable.move(0, 0, 1);
            setBlock(level, mutable, randBlock(bricks).defaultBlockState());
            mutable.move(0, 0, 1);
            setBlock(level, mutable, Blocks.ANDESITE.defaultBlockState());

            // middle layer
            mutable.move(0, 1, -2);
            setBlock(level, mutable, Blocks.STONE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
            mutable.move(0, 0, 1);
            setBlock(level, mutable, randBlock(cobble).defaultBlockState());
            mutable.move(0, 0, 1);
            setBlock(level, mutable, Blocks.STONE_SLAB.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));

            //second middle layer
            mutable.move(0, 1, -2);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_STAIRS.defaultBlockState().setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.STRAIGHT).setValue(BlockStateProperties.HALF, Half.TOP).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH));
            mutable.move(0, 0, 1);
            setBlock(level, mutable, randBlock(cobbleWalls).defaultBlockState().setValue(BlockStateProperties.NORTH_WALL, WallHeight.LOW).setValue(BlockStateProperties.SOUTH_WALL, WallHeight.LOW));
            mutable.move(0, 0, 1);
            setBlock(level, mutable, Blocks.POLISHED_ANDESITE_STAIRS.defaultBlockState().setValue(BlockStateProperties.STAIRS_SHAPE, StairsShape.STRAIGHT).setValue(BlockStateProperties.HALF, Half.TOP).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));

            //top layer
            mutable.move(0, 1, -1);
            setBlock(level, mutable, randBlock(cobbleWalls).defaultBlockState());
        }
    }
}