package team.rusty.bumpkinbatch.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class BFoods {

    public static final FoodProperties EAST_TWICKS = makeCandy(MobEffects.MOVEMENT_SPEED);
    public static final FoodProperties WEST_TWICKS = makeCandy(MobEffects.MOVEMENT_SLOWDOWN);
    public static final FoodProperties ORE_O = makeCandy(MobEffects.DIG_SPEED);
    public static final FoodProperties GUMMY_BOARS = makeCandy(MobEffects.DAMAGE_BOOST);
    public static final FoodProperties CREEPER_CRACKS = makeCandy(MobEffects.NIGHT_VISION);



    public static FoodProperties makeCandy(MobEffect effect) {
        return (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).effect(new MobEffectInstance(effect, 400, 1), 1.0F).build();
    }
}
