package com.ommods.openmodularturrets.content.block.turret_bases;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.config.OMTConfig;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TurretBaseBlockTier4 extends TurretBaseBlock {
    public static final MapCodec<TurretBaseBlockTier4> CODEC = simpleCodec(TurretBaseBlockTier4::new);
    public static final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
            .destroyTime(OMTConfig.TurretBase.baseTier4DestroySpeed)
            .explosionResistance(OMTConfig.TurretBase.baseTier4BlastResistance);

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
