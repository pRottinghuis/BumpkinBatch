package team.rusty.bumpkinbatch.worldgen.structure;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.registry.BWorldGen;

import java.util.Random;

public class HalloweenStructureElement extends SinglePoolElement {
    public static final ResourceLocation CANDY_CHEST = new ResourceLocation(BumpkinBatch.ID, "chests/candy_chest");

    public static final Codec<HalloweenStructureElement> CODEC = RecordCodecBuilder.create((p_210429_) -> {
        return p_210429_.group(templateCodec(), processorsCodec(), projectionCodec()).apply(p_210429_, HalloweenStructureElement::new);
    });

    public HalloweenStructureElement(Either<ResourceLocation, StructureTemplate> template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection) {
        super(template, processors, projection);
    }

    public static HalloweenStructureElement create(StructurePoolElement element) {
        if (element instanceof SinglePoolElement single) {
            return new HalloweenStructureElement(single.template, ProcessorLists.EMPTY, StructureTemplatePool.Projection.RIGID);
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
        //structureplacesettings.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK); // grrrr
        structureplacesettings.setFinalizeEntities(true);
        if (!p_69110_) {
            structureplacesettings.addProcessor(JigsawReplacementProcessor.INSTANCE);
        }

        // idk if using the value is bad practice
        this.processors.value().list().forEach(structureplacesettings::addProcessor);
        this.getProjection().getProcessors().forEach(structureplacesettings::addProcessor);
        return structureplacesettings;
    }

    @Override
    public void handleDataMarker(LevelAccessor level, StructureTemplate.StructureBlockInfo info, BlockPos pos, Rotation p_69161_, Random random, BoundingBox p_69163_) {
        if (StructureMode.valueOf(info.nbt.getString("mode")) == StructureMode.DATA) {
            var marker = info.nbt.getString("metadata");
            var markerPos = info.pos;

            if ("spider_spawner".equals(marker)) {
                level.setBlock(markerPos, Blocks.COBWEB.defaultBlockState(), 3);

                var te = level.getBlockEntity(markerPos.below());

                if (te instanceof SpawnerBlockEntity spawner) {
                    spawner.getSpawner().setEntityId(random.nextBoolean() ? EntityType.SPIDER : EntityType.CAVE_SPIDER);
                    spawner.setChanged();
                }
            }
        }
    }

    @Override
    public StructurePoolElementType<?> getType() {
        return BWorldGen.HALLOWEEN_ELEMENT.get();
    }

    @Override
    public String toString() {
        return "HalloweenElement[" + this.template + "]";
    }
}
