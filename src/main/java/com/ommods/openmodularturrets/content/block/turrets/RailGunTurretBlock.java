package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class RailGunTurretBlock extends AbstractDirectedTurretBlock<RailGunTurretBlockEntity> {
    public static final MapCodec<RailGunTurretBlock> CODEC = simpleCodec(RailGunTurretBlock::new);

    public RailGunTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.RAIL_GUN_TURRET::get, RailGunTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
