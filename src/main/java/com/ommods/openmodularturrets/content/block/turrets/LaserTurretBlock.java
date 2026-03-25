package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class LaserTurretBlock extends AbstractDirectedTurretBlock<LaserTurretBlockEntity> {
    public static final MapCodec<LaserTurretBlock> CODEC = simpleCodec(LaserTurretBlock::new);

    public LaserTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.LASER_TURRET::get, LaserTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
