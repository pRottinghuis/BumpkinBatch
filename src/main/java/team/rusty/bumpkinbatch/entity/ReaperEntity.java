package team.rusty.bumpkinbatch.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Map;

public class ReaperEntity extends AbstractSkeleton {
    public ReaperEntity(EntityType<? extends AbstractSkeleton> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        // Make the sword wood if you're on peaceful
        ItemStack stack = difficulty.getDifficulty() == Difficulty.PEACEFUL ? new ItemStack(Items.WOODEN_SWORD) : new ItemStack(Items.IRON_SWORD);

        switch (difficulty.getDifficulty()) {
            // Sharpness 5 Unbreaking 3 Knockback 1
            case HARD -> EnchantmentHelper.setEnchantments(Map.of(
                    Enchantments.SHARPNESS, 5,
                    Enchantments.UNBREAKING, 3,
                    Enchantments.KNOCKBACK, 1
            ), stack);
            // Sharpness 2-4 Unbreaking 1-3
            case NORMAL -> EnchantmentHelper.setEnchantments(Map.of(
                    Enchantments.SHARPNESS, random.nextFloat() < difficulty.getSpecialMultiplier() ? 4 : 2,
                    Enchantments.UNBREAKING, 1 + random.nextInt(3)
            ), stack);
            // Sharpness 1-2 Unbreaking 1-2
            default -> EnchantmentHelper.setEnchantments(Map.of(
                    Enchantments.SHARPNESS, random.nextFloat() < difficulty.getSpecialMultiplier() ? 2 : 1,
                    Enchantments.UNBREAKING, 1 + random.nextInt(2)
            ), stack);
        }

        setItemSlot(EquipmentSlot.MAINHAND, stack);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!super.doHurtTarget(target)) {
            return false;
        } else {
            if (target instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30), this);
            }

            return true;
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 2.7f;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.STRAY_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33850_) {
        return SoundEvents.WITHER_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_SKELETON_DEATH;
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.STRAY_STEP;
    }
}
