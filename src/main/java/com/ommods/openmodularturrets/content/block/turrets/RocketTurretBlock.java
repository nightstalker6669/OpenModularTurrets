package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class RocketTurretBlock extends AbstractDirectedTurretBlock<RocketTurretBlockEntity> {
    public static final MapCodec<RocketTurretBlock> CODEC = simpleCodec(RocketTurretBlock::new);

    public RocketTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.ROCKET_TURRET::get, RocketTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
