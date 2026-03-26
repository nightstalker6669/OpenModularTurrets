package com.ommods.openmodularturrets.content.block.turret_bases;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TurretBaseBlockTier1 extends TurretBaseBlock {
    public static final MapCodec<TurretBaseBlockTier1> CODEC = simpleCodec(TurretBaseBlockTier1::new);
    public static final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
            .destroyTime(10.0F)
            .explosionResistance(10.0F);

    public TurretBaseBlockTier1(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected int getTier() {
        return 1;
    }

    @Override
    protected MapCodec<? extends TurretBaseBlock> codec() {
        return CODEC;
    }
}
