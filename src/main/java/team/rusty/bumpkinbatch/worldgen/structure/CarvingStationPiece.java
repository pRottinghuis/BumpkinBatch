package team.rusty.bumpkinbatch.worldgen.structure;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import team.rusty.bumpkinbatch.BumpkinBatch;

import java.util.Random;

public class CarvingStationPiece extends PoolElementStructurePiece {
    public static final ResourceLocation CANDY_CHEST = new ResourceLocation(BumpkinBatch.ID, "chests/candy_chest");

    public CarvingStationPiece(StructureManager p_72606_, StructurePoolElement p_72607_, BlockPos p_72608_, int p_72609_, Rotation p_72610_, BoundingBox p_72611_) {
        super(p_72606_, p_72607_, p_72608_, p_72609_, p_72610_, p_72611_);
    }
/*
    @Override
    public boolean postProcess(WorldGenLevel level, StructureFeatureManager manager, ChunkGenerator generator, Random random, BoundingBox bounds, ChunkPos chunkPos, BlockPos pos) {
        if (place(level, manager, generator, random, bounds, pos, false)) {
            for(StructureTemplate.StructureBlockInfo info : this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.STRUCTURE_BLOCK)) {
                if (info.nbt != null) {
                    StructureMode structuremode = StructureMode.valueOf(info.nbt.getString("mode"));
                    if (structuremode == StructureMode.DATA) {
                        handleDataMarker(info.nbt.getString("metadata"), info.pos, level, random, bounds);
                    }
                }
            }
        }

        return true;
    }
*/
    protected void handleDataMarker(String marker, BlockPos pos, ServerLevelAccessor level, Random random, BoundingBox p_73687_) {
        if ("candy_chest".equals(marker) || "chest".equals(marker)) {
            var te = level.getBlockEntity(pos.below());
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

            if (te instanceof RandomizableContainerBlockEntity container) {
                container.setLootTable(CANDY_CHEST, random.nextLong());
            }
        } else if ("spider_spawner".equals(marker)) {
            level.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 2);

            var te = level.getBlockEntity(pos);

            if (te instanceof SpawnerBlockEntity spawner) {
                spawner.getSpawner().setEntityId(random.nextBoolean() ? EntityType.SPIDER : EntityType.CAVE_SPIDER);
                spawner.setChanged();
            }
        }
    }
}
