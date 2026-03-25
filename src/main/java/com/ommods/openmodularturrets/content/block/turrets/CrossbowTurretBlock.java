package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class CrossbowTurretBlock extends AbstractDirectedTurretBlock<CrossbowTurretBlockEntity> {
    public static final MapCodec<CrossbowTurretBlock> CODEC = simpleCodec(CrossbowTurretBlock::new);

    public CrossbowTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.CROSSBOW_TURRET::get, CrossbowTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
