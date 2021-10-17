package team.rusty.bumpkinbatch.worldgen.structure;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.structures.SinglePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import team.rusty.bumpkinbatch.BumpkinBatch;

import java.util.Random;
import java.util.function.Supplier;

public class HalloweenStructureElement extends SinglePoolElement {
    public static final ResourceLocation CANDY_CHEST = new ResourceLocation(BumpkinBatch.ID, "chests/candy_chest");

    protected HalloweenStructureElement(Either<ResourceLocation, StructureTemplate> template, Supplier<StructureProcessorList> processors, StructureTemplatePool.Projection projection) {
        super(template, processors, projection);
    }

    public static HalloweenStructureElement create(StructurePoolElement element) {
        if (element instanceof SinglePoolElement single) {
            return new HalloweenStructureElement(single.template, () -> ProcessorLists.EMPTY, StructureTemplatePool.Projection.RIGID);
        } else {
            throw new RuntimeException("broken");
        }
    }

    @Override
    protected StructurePlaceSettings getSettings(Rotation p_69108_, BoundingBox bounds, boolean p_69110_) {
        StructurePlaceSettings structureplacesettings = new StructurePlaceSettings();
        structureplacesettings.setBoundingBox(bounds);
        structureplacesettings.setRotation(p_69108_);
        structureplacesettings.setKnownShape(true);
        structureplacesettings.setIgnoreEntities(false);
        //structureplacesettings.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK); // fuck you minecraft
        structureplacesettings.setFinalizeEntities(true);
        if (!p_69110_) {
            structureplacesettings.addProcessor(JigsawReplacementProcessor.INSTANCE);
        }

        this.processors.get().list().forEach(structureplacesettings::addProcessor);
        this.getProjection().getProcessors().forEach(structureplacesettings::addProcessor);
        return structureplacesettings;
    }

    @Override
    public void handleDataMarker(LevelAccessor level, StructureTemplate.StructureBlockInfo info, BlockPos pos, Rotation p_69161_, Random random, BoundingBox p_69163_) {
        if (info.nbt != null) {
            if (StructureMode.valueOf(info.nbt.getString("mode")) == StructureMode.DATA) {
                var marker = info.nbt.getString("metadata");
                var markerPos = info.pos;

                if ("candy_chest".equals(marker) || "chest".equals(marker)) {
                    var te = level.getBlockEntity(markerPos.below());
                    level.setBlock(markerPos, Blocks.AIR.defaultBlockState(), 3);

                    if (te instanceof ChestBlockEntity container) {
                        container.setLootTable(CANDY_CHEST, random.nextLong());
                    }
                } else if ("spider_spawner".equals(marker)) {
                    level.setBlock(markerPos, Blocks.COBWEB.defaultBlockState(), 3);

                    var te = level.getBlockEntity(markerPos.below());

                    if (te instanceof SpawnerBlockEntity spawner) {
                        spawner.getSpawner().setEntityId(random.nextBoolean() ? EntityType.SPIDER : EntityType.CAVE_SPIDER);
                        spawner.setChanged();
                    }
                }
            }
        }
    }
}
