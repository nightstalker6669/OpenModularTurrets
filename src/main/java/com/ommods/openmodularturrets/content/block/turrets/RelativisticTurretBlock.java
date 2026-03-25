package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class RelativisticTurretBlock extends AbstractDirectedTurretBlock<RelativisticTurretBlockEntity> {
    public static final MapCodec<RelativisticTurretBlock> CODEC = simpleCodec(RelativisticTurretBlock::new);

    public RelativisticTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.RELATIVISTIC_TURRET::get, RelativisticTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
