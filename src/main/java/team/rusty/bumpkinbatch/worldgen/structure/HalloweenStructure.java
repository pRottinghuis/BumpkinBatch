package team.rusty.bumpkinbatch.worldgen.structure;

import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.util.worldgen.structure.SimpleStructure;

import java.util.Random;

public class HalloweenStructure extends SimpleStructure {
    public static final ResourceLocation CANDY_CHEST = new ResourceLocation(BumpkinBatch.ID, "chests/candy_chest");

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return HalloweenStructure.FeatureStart::new;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom worldgenRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos2, NoFeatureConfig noneFeatureConfiguration) {
        Random random = new Random(seed + hash(chunkX, chunkZ));

        int x = chunkX << 4;
        int z = chunkZ << 4;

        // moves the center of the structure to a random location in the chunk.
        // random number is the same as long as the chunk (ie seed) is the same.
        BlockPos centerOfChunk = new BlockPos(x + random.nextInt(15), 0, z + random.nextInt(15));

        // Gets height of first land block.
        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);

        // Gets column of blocks at given position.
        IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());

        // Combine the column of blocks with land height and you get the top block itself
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));

        // Make sure not spawning on water or other fluids.
        return topBlock.getFluidState().isEmpty();
    }

    // From ChunkPos
    protected static int hash(int x, int z) {
        int i = 1664525 * x + 1013904223;
        int j = 1664525 * (z ^ -559038737) + 1013904223;
        return i ^ j;
    }


    public static class FeatureStart extends StructureStart<NoFeatureConfig> {
        // World seed
        private final long worldSeed;

        public FeatureStart(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox bounds, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, bounds, referenceIn, seedIn);
            this.worldSeed = seedIn;
        }

        public void generatePieces(DynamicRegistries dynamicRegistry, ChunkGenerator generator, TemplateManager manager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig noneConfig) {
            Random random = new Random(worldSeed + hash(chunkX, chunkZ));
            int x = chunkX << 4;
            int z = chunkZ << 4;

            BlockPos centerPos = new BlockPos(x + random.nextInt(15), 0, z + random.nextInt(15));

            JigsawManager.addPieces(
                    dynamicRegistry,
                    new VillageConfig(() -> {
                        MutableRegistry<JigsawPattern> templateRegistry = dynamicRegistry.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
                        return templateRegistry.get(new ResourceLocation(getFeature().getFeatureName() + "/pool"));
                    }, 10),
                    (m, element, p, gld, r, bb) -> new AbstractVillagePiece(m, HalloweenStructureElement.create(element), p, gld, r, bb),
                    generator,
                    manager,
                    centerPos,
                    pieces,
                    random,
                    false,
                    true);

            // Adjusts height of all pieces.
            //this.pieces.forEach(piece -> piece.move(0, -1, 0));
            // Adjusts height of bounding box
            //this.pieces.forEach(piece -> piece.getBoundingBox().move(0, 1, 0));

            // Centers structure on
            Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
            int xOffset = centerPos.getX() - structureCenter.getX();
            int zOffset = centerPos.getZ() - structureCenter.getZ();
            for (StructurePiece structurePiece : this.pieces) {
                if (getFeature().getFeatureName().equals("bumpkinbatch:haunted_house")) {
                    structurePiece.move(xOffset, -6, zOffset);
                    this.pieces.forEach(piece -> piece.getBoundingBox().move(0, 6, 0));
                } else {
                    structurePiece.move(xOffset, 0, zOffset);
                }
            }

            calculateBoundingBox();
        }
    }
}
