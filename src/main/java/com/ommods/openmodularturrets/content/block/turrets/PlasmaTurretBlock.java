package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class PlasmaTurretBlock extends AbstractDirectedTurretBlock<PlasmaTurretBlockEntity> {
    public static final MapCodec<PlasmaTurretBlock> CODEC = simpleCodec(PlasmaTurretBlock::new);

    public PlasmaTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.PLASMA_TURRET::get, PlasmaTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
