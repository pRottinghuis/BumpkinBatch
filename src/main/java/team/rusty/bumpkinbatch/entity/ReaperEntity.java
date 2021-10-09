package team.rusty.bumpkinbatch.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ReaperEntity extends Monster {
    protected ReaperEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void registerGoals() {

    }


    public void addBehaviorGoals() {

    }

    public static AttributeSupplier.Builder createAttributes() {

    }

    @Override
    public void defineSynchedData() {

    }

    @Override
    public int getExperienceReward(Player player) {

    }

    @Override
    public void tick() {

    }

    @Override
    public void aiStep() {

    }
}
