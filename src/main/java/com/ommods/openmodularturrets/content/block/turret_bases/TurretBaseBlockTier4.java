package com.ommods.openmodularturrets.content.block.turret_bases;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TurretBaseBlockTier4 extends TurretBaseBlock {
    public static final MapCodec<TurretBaseBlockTier4> CODEC = simpleCodec(TurretBaseBlockTier4::new);
    public static final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
            .destroyTime(40.0F)
            .explosionResistance(40.0F);

    public TurretBaseBlockTier4(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected int getTier() {
        return 4;
    }

    @Override
    protected MapCodec<? extends TurretBaseBlock> codec() {
        return CODEC;
    }
}
