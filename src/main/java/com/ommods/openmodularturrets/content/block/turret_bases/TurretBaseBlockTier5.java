package com.ommods.openmodularturrets.content.block.turret_bases;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.config.OMTConfig;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TurretBaseBlockTier5 extends TurretBaseBlock {
    public static final MapCodec<TurretBaseBlockTier5> CODEC = simpleCodec(TurretBaseBlockTier5::new);
    public static final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
            .destroyTime(OMTConfig.TurretBase.baseTier5DestroySpeed)
            .explosionResistance(OMTConfig.TurretBase.baseTier5BlastResistance);

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
