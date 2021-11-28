package team.rusty.bumpkinbatch.registry;

import net.minecraft.item.Food;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class BFoods {

    public static final Food EAST_TWICKS = makeCandy(Effects.MOVEMENT_SPEED);
    public static final Food WEST_TWICKS = makeCandy(Effects.MOVEMENT_SLOWDOWN);
    public static final Food ORE_O = makeCandy(Effects.DIG_SPEED);
    public static final Food GUMMY_BOARS = makeCandy(Effects.DAMAGE_BOOST);
    public static final Food CREEPER_STICK = makeCandy(Effects.NIGHT_VISION, Effects.CONFUSION);



    public static Food makeCandy(Effect effect) {
        return (new Food.Builder()).nutrition(2).saturationMod(0.1F).effect(() -> new EffectInstance(effect, 400, 1), 1.0F).build();
    }

    public static Food makeCandy(Effect effect1, Effect effect2) {
        return (new Food.Builder()).nutrition(2).saturationMod(0.1F).effect(() -> new EffectInstance(effect1, 400, 1), 1.0F).effect(() -> new EffectInstance(effect2, 400, 1), 1.0F).build();
    }
}
