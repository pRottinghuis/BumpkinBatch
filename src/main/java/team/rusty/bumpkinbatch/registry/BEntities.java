package team.rusty.bumpkinbatch.registry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.rusty.bumpkinbatch.BumpkinBatch;
import team.rusty.bumpkinbatch.entity.ReaperEntity;

public class BEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, BumpkinBatch.ID);

    public static final RegistryObject<EntityType<ReaperEntity>> REAPER = ENTITIES.register("reaper", () -> EntityType.Builder.of(ReaperEntity::new, MobCategory.MONSTER).sized(1, 1).build(BumpkinBatch.ID + ":reaper"));
    //
}
