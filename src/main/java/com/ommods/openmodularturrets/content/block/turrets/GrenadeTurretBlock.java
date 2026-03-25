package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class GrenadeTurretBlock extends AbstractDirectedTurretBlock<GrenadeTurretBlockEntity> {
    public static final MapCodec<GrenadeTurretBlock> CODEC = simpleCodec(GrenadeTurretBlock::new);

    public GrenadeTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.GRENADE_TURRET::get, GrenadeTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
