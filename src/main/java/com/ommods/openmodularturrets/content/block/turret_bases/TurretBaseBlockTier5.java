package com.ommods.openmodularturrets.content.block.turret_bases;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TurretBaseBlockTier5 extends TurretBaseBlock {
    public static final MapCodec<TurretBaseBlockTier5> CODEC = simpleCodec(TurretBaseBlockTier5::new);
    public static final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
            .destroyTime(50.0F)
            .explosionResistance(50.0F);

    public TurretBaseBlockTier5(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected int getTier() {
        return 5;
    }

    @Override
    protected MapCodec<? extends TurretBaseBlock> codec() {
        return CODEC;
    }
}
