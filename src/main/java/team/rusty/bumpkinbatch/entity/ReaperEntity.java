package team.rusty.bumpkinbatch.entity;

import com.google.common.collect.ImmutableMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class ReaperEntity extends AbstractSkeletonEntity {
    public ReaperEntity(EntityType<? extends AbstractSkeletonEntity> entityType, World level) {
        super(entityType, level);

        xpReward = 15;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4)
                .add(Attributes.MAX_HEALTH, 50.0);
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        // Make the sword wood if you're on peaceful
        ItemStack stack = difficulty.getDifficulty() == Difficulty.EASY ? new ItemStack(Items.WOODEN_SWORD) : new ItemStack(Items.IRON_SWORD);

        // Sharpness 5 Unbreaking 3 Knockback 1
        // Sharpness 2-4 Unbreaking 1-3
        // Sharpness 1-2 Unbreaking 1-2
        switch (difficulty.getDifficulty()) {
            case HARD:
                EnchantmentHelper.setEnchantments(ImmutableMap.of(
                        Enchantments.SHARPNESS, 5,
                        Enchantments.UNBREAKING, 3,
                        Enchantments.KNOCKBACK, 1
                ), stack);
                break;
            case NORMAL:
                EnchantmentHelper.setEnchantments(ImmutableMap.of(
                        Enchantments.SHARPNESS, random.nextFloat() < difficulty.getSpecialMultiplier() ? 4 : 2,
                        Enchantments.UNBREAKING, 1 + random.nextInt(3)
                ), stack);
                break;
            default:
                EnchantmentHelper.setEnchantments(ImmutableMap.of(
                        Enchantments.SHARPNESS, random.nextFloat() < difficulty.getSpecialMultiplier() ? 2 : 1,
                        Enchantments.UNBREAKING, 1 + random.nextInt(2)
                ), stack);
                break;
        }

        setItemSlot(EquipmentSlotType.MAINHAND, stack);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!super.doHurtTarget(target)) {
            return false;
        } else {
            if (target instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) target;

                living.addEffect(new EffectInstance(Effects.BLINDNESS, 30));
            }

            return true;
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
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
