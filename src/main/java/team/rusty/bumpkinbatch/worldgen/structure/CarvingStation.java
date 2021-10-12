package team.rusty.bumpkinbatch.worldgen.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import team.rusty.bumpkinbatch.BumpkinBatch;

import java.util.Random;

public class CarvingStation extends StructureFeature<NoneFeatureConfiguration> {

    public CarvingStation() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return CarvingStation.FeatureStart::new;
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed, WorldgenRandom worldgenRandom, ChunkPos pos, Biome biome, ChunkPos chunkPos2, NoneFeatureConfiguration noneFeatureConfiguration, LevelHeightAccessor levelHeightAccessor) {
        var random = new Random(seed + pos.hashCode());

        // moves the center of the structure to a random location in the chunk.
        // random number is the same as long as the chunk (ie seed) is the same.
        BlockPos centerOfChunk = new BlockPos(pos.getMinBlockX() + random.nextInt(15), 0, pos.getMinBlockZ() + random.nextInt(15));

        // Gets height of first land block.
        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor);

        // Gets column of blocks at given position.
        NoiseColumn columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ(), levelHeightAccessor);

        // Combine the column of blocks with land height and you get the top block itself
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));

        // Make sure not spawning on water or other fluids.
        return topBlock.getFluidState().isEmpty();
    }


    public static class FeatureStart extends StructureStart<NoneFeatureConfiguration> {
        // World seed
        private final long worldSeed;

        public FeatureStart(StructureFeature<NoneFeatureConfiguration> structureIn, ChunkPos pos, int referenceIn, long seedIn) {
            super(structureIn, pos, referenceIn, seedIn);
            this.worldSeed = seedIn;
        }

        public void generatePieces(RegistryAccess registryAccess, ChunkGenerator chunkGenerator, StructureManager manager, ChunkPos pos, Biome biome, NoneFeatureConfiguration noneConfig, LevelHeightAccessor heightAccessor) {
            var random = new Random(worldSeed + pos.hashCode());


            BlockPos centerPos = new BlockPos(pos.getMinBlockX() + random.nextInt(15), 0, pos.getMinBlockZ() + random.nextInt(15));

            JigsawPlacement.addPieces(
                    registryAccess,
                    // Creates JigsawConfiguration from specified resourcelocation.
                    new JigsawConfiguration(() -> registryAccess.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                            .get(new ResourceLocation(BumpkinBatch.ID, "grave_yard/pool")),
                            10), // how many pieces outward from center can structure pieces spawn. Irrelevant for this structure as it is a 1 piece anyways.
                    PoolElementStructurePiece::new,
                    chunkGenerator,
                    manager,
                    centerPos,
                    this,
                    this.random,
                    false,
                    true, // true: jigsaw placement gets heightmap pos in middle of structure bounding box
                    heightAccessor);

            // Adjusts hight of all pieces.
            this.pieces.forEach(piece -> piece.move(0, 0, 0));
            // Adjusts height of bounding box
            this.pieces.forEach(piece -> piece.getBoundingBox().move(0, 1, 0));

            // Centers structure on
            Vec3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
            int xOffset = centerPos.getX() - structureCenter.getX();
            int zOffset = centerPos.getZ() - structureCenter.getZ();

            for (StructurePiece structurePiece : this.pieces){
                structurePiece.move(xOffset, 0, zOffset);
            }
        }
    }
}
