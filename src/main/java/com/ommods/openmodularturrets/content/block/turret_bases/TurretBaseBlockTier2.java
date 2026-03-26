package com.ommods.openmodularturrets.content.block.turret_bases;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TurretBaseBlockTier2 extends TurretBaseBlock {
    public static final MapCodec<TurretBaseBlockTier2> CODEC = simpleCodec(TurretBaseBlockTier2::new);
    public static final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
            .destroyTime(20.0F)
            .explosionResistance(20.0F);

    public TurretBaseBlockTier2(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected int getTier() {
        return 2;
    }

    @Override
    protected MapCodec<? extends TurretBaseBlock> codec() {
        return CODEC;
    }
}
