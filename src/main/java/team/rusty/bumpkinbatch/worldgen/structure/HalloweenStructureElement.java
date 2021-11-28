package team.rusty.bumpkinbatch.worldgen.structure;

import com.mojang.datafixers.util.Either;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.JigsawReplacementStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;
import java.util.function.Supplier;

public class HalloweenStructureElement extends SingleJigsawPiece {

    protected HalloweenStructureElement(Either<ResourceLocation, Template> template, Supplier<StructureProcessorList> processors, JigsawPattern.PlacementBehaviour projection) {
        super(template, processors, projection);
    }

    public static HalloweenStructureElement create(JigsawPiece element) {
        if (element instanceof SingleJigsawPiece) {
            SingleJigsawPiece single = (SingleJigsawPiece) element;
            return new HalloweenStructureElement(single.template, () -> ProcessorLists.EMPTY, JigsawPattern.PlacementBehaviour.RIGID);
        } else {
            throw new RuntimeException("broken");
        }
    }

    @Override
    protected PlacementSettings getSettings(Rotation p_69108_, MutableBoundingBox bounds, boolean p_69110_) {
        PlacementSettings settings = new PlacementSettings();
        settings.setBoundingBox(bounds);
        settings.setRotation(p_69108_);
        settings.setKnownShape(true);
        settings.setIgnoreEntities(false);
        //settings.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK); // still want to handle data markers
        settings.setFinalizeEntities(true);
        if (!p_69110_) {
            settings.addProcessor(JigsawReplacementStructureProcessor.INSTANCE);
        }

        this.processors.get().list().forEach(settings::addProcessor);
        this.getProjection().getProcessors().forEach(settings::addProcessor);
        return settings;
    }

    @Override
    public void handleDataMarker(IWorld level, Template.BlockInfo info, BlockPos pos, Rotation p_69161_, Random random, MutableBoundingBox p_69163_) {
        if (info.nbt != null) {
            if (StructureMode.valueOf(info.nbt.getString("mode")) == StructureMode.DATA) {
                String marker = info.nbt.getString("metadata");
                BlockPos markerPos = info.pos;

                if ("candy_chest".equals(marker) || "chest".equals(marker)) {
                    TileEntity te = level.getBlockEntity(markerPos.below());
                    level.setBlock(markerPos, Blocks.AIR.defaultBlockState(), 3);

                    if (te instanceof ChestTileEntity) {
                        ((ChestTileEntity) te).setLootTable(HalloweenStructure.CANDY_CHEST, random.nextLong());
                    }
                } else if ("spider_spawner".equals(marker)) {
                    level.setBlock(markerPos, Blocks.COBWEB.defaultBlockState(), 3);

                    TileEntity te = level.getBlockEntity(markerPos.below());

                    if (te instanceof MobSpawnerTileEntity) {
                        MobSpawnerTileEntity spawner = (MobSpawnerTileEntity) te;
                        spawner.getSpawner().setEntityId(random.nextBoolean() ? EntityType.SPIDER : EntityType.CAVE_SPIDER);
                        spawner.setChanged();
                    }
                }
            }
        }
    }
}
