package team.rusty.util.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Credits to <a href ="https://github.com/TelepathicGrunt/StructureTutorialMod/blob/1.18.x-Forge-Jigsaw/src/main/java/com/telepathicgrunt/structuretutorial/structures/RunDownHouseStructure.java">TelepathicGrunt</a>
 * for the 1.18 port
 *
 * @author TheDarkColour
 */
public abstract class SimpleStructure extends StructureFeature<JigsawConfiguration> {
    public SimpleStructure(int baseHeight, boolean split, boolean raiseToGround, JigsawPlacement.PieceFactory pieceFactory, Predicate<PieceGeneratorSupplier.Context<JigsawConfiguration>> canPlace) {
        // codec, base height, split, raise to ground
        super(JigsawConfiguration.CODEC, (context) -> {
            if (!canPlace.test(context)) {
                return Optional.empty();
            } else {
                BlockPos blockpos = new BlockPos(context.chunkPos().getMinBlockX(), baseHeight, context.chunkPos().getMinBlockZ());
                Pools.bootstrap();
                return JigsawPlacement.addPieces(context, pieceFactory, blockpos, split, raiseToGround);
            }
        });
    }

    protected static boolean canSpawn(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        var pos = context.chunkPos().getWorldPosition();
        var level = context.heightAccessor();

        var landHeight = context.chunkGenerator().getFirstOccupiedHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, level);

        // Don't spawn on water
        var columnOfBlocks = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), level);
        var topBlock = columnOfBlocks.getBlock(landHeight);

        return topBlock.getFluidState().isEmpty();
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }
}
