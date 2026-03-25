package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class IncendiaryTurretBlock extends AbstractDirectedTurretBlock<IncendiaryTurretBlockEntity> {
    public static final MapCodec<IncendiaryTurretBlock> CODEC = simpleCodec(IncendiaryTurretBlock::new);

    public IncendiaryTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.INCENDIARY_TURRET::get, IncendiaryTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
