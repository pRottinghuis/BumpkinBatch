package team.rusty.bumpkinbatch.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class BFoods {

    public static final FoodProperties CANDY = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).effect(new MobEffectInstance(MobEffects.CONFUSION, 100, 1), 1.0F).build();

}
