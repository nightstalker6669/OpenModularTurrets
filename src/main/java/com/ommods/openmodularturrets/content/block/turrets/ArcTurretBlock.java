package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class ArcTurretBlock extends AbstractDirectedTurretBlock<ArcTurretBlockEntity> {
    public static final MapCodec<ArcTurretBlock> CODEC = simpleCodec(ArcTurretBlock::new);

    public ArcTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.ARC_TURRET::get, ArcTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
