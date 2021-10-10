package team.rusty.bumpkinbatch.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class CrossFeature extends Feature<BlockStateConfiguration> {
    public CrossFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        var level = context.level();
        var config = context.config();
        var state = config.state;
        var pos = context.origin();

        // don't spawn in the water
        if (!level.getBlockState(pos.below()).canOcclude()) return false;

        var mutable = pos.mutable();

        for (var i = 0; i < 5; ++i) {
            setBlock(level, mutable, state);
            mutable.move(0, 1, 0);
        }

        mutable.move(0, -2, 0);

        setBlock(level, mutable.move(-1, 0, 0), state);
        setBlock(level, mutable.move( 2, 0, 0), state);

        return false;
    }
}
