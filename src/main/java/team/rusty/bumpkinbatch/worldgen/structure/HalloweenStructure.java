package team.rusty.bumpkinbatch.worldgen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.registry.BEntities;
import team.rusty.bumpkinbatch.registry.BWorldGen;
import team.rusty.util.structure.SimpleStructure;

import java.util.List;
import java.util.Optional;

public class HalloweenStructure extends SimpleStructure {

    //https://github.com/TelepathicGrunt/StructureTutorialMod/blob/1.18.x-Forge-Jigsaw/src/main/java/com/telepathicgrunt/structuretutorial/structures/RunDownHouseStructure.java

    /*
    @Override
    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return HalloweenStructure.FeatureStart::new;
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
        BlockState topBlock = columnOfBlocks.getBlock(landHeight);

        // Make sure not spawning on water or other fluids.
        return topBlock.getFluidState().isEmpty();
    } */

    public HalloweenStructure(Codec<JigsawConfiguration> codec) {
        super(codec, (context) -> {
                    if (!HalloweenStructure.isFeatureChunk(context)) {
                        return Optional.empty();
                    }
                    // Create the pieces layout of the structure and give it to
                    else {
                        return HalloweenStructure.createPiecesGenerator(context);
                    }
                },
                PostPlacementProcessor.NONE);
    }

    private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_MONSTERS = ImmutableList.of(
            new MobSpawnSettings.SpawnerData(BEntities.REAPER.get(), 100, 4, 9)
    );

    private static void createPiecesGenerator(final StructureSpawnListGatherEvent event) {
        if (event.getStructure() == BWorldGen.HAUNTED_HOUSE.get()) {
            event.addEntitySpawns(MobCategory.MONSTER, STRUCTURE_MONSTERS);
        }
    }


    public static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        BlockPos blockPos = context.chunkPos().getWorldPosition();

        // Grab height of land. Will stop at first non-air block.
        int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

        // gets column of blocks at position
        NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor());

        // Combine the column of blocks with land height and you get the top block itself
        BlockState topBlock = columnOfBlocks.getBlock(landHeight);

        //Makes sure not in water
        return topBlock.getFluidState().isEmpty();
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);
        context.config().startPool =
                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                        .get(new ResourceLocation(BumpkinBatch.ID, "haunted_house/start_pool"));
        context.config().maxDepth = 10;
        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
                JigsawPlacement.addPieces(
                        context, // Used for JigsawPlacement to get all the proper behaviors done.
                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
                        false,  // Special boundary adjustments for villages. Keep this false and make your pieces not be partially intersecting.
                        // Either not intersecting or fully contained will make children pieces spawn just fine.
                        true // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
                        // keep false when placing structures in the nether to prevent heightmap placing the structure on the Bedrock roof.
                );
        return structurePiecesGenerator;
    }

    @Override
    public List<ResourceKey<Biome>> getSpawnBiomes() {
        return ResourceKey.create(Registry.BIOME_REGISTRY, BWorldGen.PUMPKIN_PATCH.getResourceKey());
    }
    /*
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
                            .get(new ResourceLocation(getFeature().getFeatureName() + "/pool")),
                            10), // how many pieces outward from center can structure pieces spawn. Irrelevant for this structure as it is a 1 piece anyways.
                    (m, element, p, gld, r, bb) -> new PoolElementStructurePiece(m, HalloweenStructureElement.create(element), p, gld, r, bb),
                    chunkGenerator,
                    manager,
                    centerPos,
                    this,
                    this.random,
                    false,
                    true, // true: jigsaw placement gets heightmap pos in middle of structure bounding box
                    heightAccessor);

            // Adjusts height of all pieces.
            //this.pieces.forEach(piece -> piece.move(0, -1, 0));
            // Adjusts height of bounding box
            //this.pieces.forEach(piece -> piece.getBoundingBox().move(0, 1, 0));

            // Centers structure on
            Vec3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
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
        }
    }*/
}
