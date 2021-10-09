package team.rusty.bumpkinbatch.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class BFoods {

    public static final FoodProperties EAST_TWICKS = makeCandy(MobEffects.CONFUSION);
    public static final FoodProperties WEST_TWICKS = makeCandy(MobEffects.CONFUSION);

    public static final FoodProperties ORESOES = makeCandy(MobEffects.MOVEMENT_SPEED);
    public static final FoodProperties GUMMY_BOARS = makeCandy(MobEffects.NIGHT_VISION);
    public static final FoodProperties CREEPER_CRACKS = makeCandy(MobEffects.CONFUSION);



    public static FoodProperties makeCandy(MobEffect effect) {
        return (new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).effect(new MobEffectInstance(effect, 100, 1), 1.0F).build();
    }
}
