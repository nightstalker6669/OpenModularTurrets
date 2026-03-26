package com.ommods.openmodularturrets.content.block.turret_bases;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TurretBaseBlockTier3 extends TurretBaseBlock {
    public static final MapCodec<TurretBaseBlockTier3> CODEC = simpleCodec(TurretBaseBlockTier3::new);
    public static final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
            .destroyTime(30.0F)
            .explosionResistance(30.0F);

    public TurretBaseBlockTier3(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected int getTier() {
        return 3;
    }

    @Override
    protected MapCodec<? extends TurretBaseBlock> codec() {
        return CODEC;
    }
}
