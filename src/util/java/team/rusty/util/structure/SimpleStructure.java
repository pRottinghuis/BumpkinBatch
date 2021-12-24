package team.rusty.util.structure;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.NoiseAffectingStructureFeature;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import team.rusty.rpg.AdventureRpg;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Credits to <a href ="https://github.com/TelepathicGrunt/StructureTutorialMod/blob/1.18.x-Forge-Jigsaw/src/main/java/com/telepathicgrunt/structuretutorial/structures/RunDownHouseStructure.java">TelepathicGrunt</a>
 * for the 1.18 port
 *
 * @author TheDarkColour
 */
public abstract class SimpleStructure extends NoiseAffectingStructureFeature<JigsawConfiguration> implements SpawningInBiomeStructure {
    public SimpleStructure(String name) {
        this(name, SimpleStructure::shouldPlace);
    }

    public SimpleStructure(String name, Predicate<PieceGeneratorSupplier.Context<JigsawConfiguration>> shouldPlace) {
        super(JigsawConfiguration.CODEC, (context) -> {
            if (!shouldPlace.test(context)) {
                return Optional.empty();
            } else {
                return createPiecesGenerator(context, name);
            }
        }, PostPlacementProcessor.NONE);
    }

    @Override
    public ConfiguredStructureFeature<?, ?> configuredStructure() {
        // Dummy configuration
        return configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    }

    private static boolean shouldPlace(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        var pos = context.chunkPos().getWorldPosition();
        var level = context.heightAccessor();

        var landHeight = context.chunkGenerator().getFirstOccupiedHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, level);

        // Don't spawn on water
        var columnOfBlocks = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), level);
        var topBlock = columnOfBlocks.getBlock(landHeight);

        return topBlock.getFluidState().isEmpty();
    }

    private static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context, String name) {

        var pos = context.chunkPos().getWorldPosition();
        var registryAccess = context.registryAccess();

        var newContext = new PieceGeneratorSupplier.Context<>(
                context.chunkGenerator(),
                context.biomeSource(),
                context.seed(),
                context.chunkPos(),
                new JigsawConfiguration(() -> {
                    var templateRegistry = registryAccess.ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
                    return templateRegistry.get(new ResourceLocation(AdventureRpg.ID, name + "/pool"));
                }, 10),
                context.heightAccessor(),
                context.validBiome(),
                context.structureManager(),
                context.registryAccess()
        );

        return JigsawPlacement.addPieces(
                newContext,
                PoolElementStructurePiece::new,
                pos,
                false,
                true);
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
