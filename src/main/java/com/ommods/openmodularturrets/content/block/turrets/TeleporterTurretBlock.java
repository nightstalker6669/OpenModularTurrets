package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class TeleporterTurretBlock extends AbstractDirectedTurretBlock<TeleporterTurretBlockEntity> {
    public static final MapCodec<TeleporterTurretBlock> CODEC = simpleCodec(TeleporterTurretBlock::new);

    public TeleporterTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.TELEPORTER_TURRET::get, TeleporterTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
