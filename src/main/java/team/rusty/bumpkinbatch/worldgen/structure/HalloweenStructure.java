package team.rusty.bumpkinbatch.worldgen.structure;

import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import team.rusty.util.structure.SimpleStructure;

public class HalloweenStructure extends SimpleStructure {

    //https://github.com/TelepathicGrunt/StructureTutorialMod/blob/1.18.x-Forge-Jigsaw/src/main/java/com/telepathicgrunt/structuretutorial/structures/RunDownHouseStructure.java

    public HalloweenStructure() {
        super(0, true, true, (m, element, p, gld, r, bb) -> {
            return new PoolElementStructurePiece(m, HalloweenStructureElement.create(element), p, gld, r, bb);
        }, SimpleStructure::canSpawn);
    }

    /*public static boolean canSpawn(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        BlockPos blockPos = context.chunkPos().getWorldPosition();

        // Grab height of land. Will stop at first non-air block.
        int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

        // gets column of blocks at position
        NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor());

        // Combine the column of blocks with land height and you get the top block itself
        BlockState topBlock = columnOfBlocks.getBlock(landHeight);

        //Makes sure not in water
        return topBlock.getFluidState().isEmpty();
    }*/
}
