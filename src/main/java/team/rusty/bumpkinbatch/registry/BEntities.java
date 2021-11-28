package team.rusty.bumpkinbatch.registry;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.entity.ReaperEntity;

public class BEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, BumpkinBatch.ID);

    public static final RegistryObject<EntityType<ReaperEntity>> REAPER = ENTITIES.register("reaper", () -> EntityType.Builder.of(ReaperEntity::new, EntityClassification.MONSTER).sized(1, 3).build(BumpkinBatch.ID + ":reaper"));

    public static void addEntityAttribs(EntityAttributeCreationEvent event) {
        event.put(REAPER.get(), ReaperEntity.createAttributes().build());
    }
}
