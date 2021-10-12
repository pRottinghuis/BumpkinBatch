package team.rusty.bumpkinbatch.worldgen.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Random;
import java.util.function.Function;

public class CarvingStationPiece extends PoolElementStructurePiece {


    public CarvingStationPiece(StructureManager p_72606_, StructurePoolElement p_72607_, BlockPos p_72608_, int p_72609_, Rotation p_72610_, BoundingBox p_72611_) {
        super(p_72606_, p_72607_, p_72608_, p_72609_, p_72610_, p_72611_);
    }

    public CarvingStationPiece(ServerLevel p_163118_, CompoundTag p_163119_) {
        super(p_163118_, p_163119_);
    }

    @Override
    public boolean postProcess(WorldGenLevel p_72620_, StructureFeatureManager p_72621_, ChunkGenerator p_72622_, Random p_72623_, BoundingBox p_72624_, ChunkPos p_72625_, BlockPos p_72626_) {
        return super.postProcess(p_72620_, p_72621_, p_72622_, p_72623_, p_72624_, p_72625_, p_72626_);
    }

    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, Random rand, BoundingBox boundingBox) {
        if ("candy_chest".equals(name)) {
            RandomizableContainerBlockEntity.setLootTable(level, rand, pos.below(), new ResourceLocation("bumpkinbatch", "chest/candy_chest"));
        }
    }
}
