package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class DisposableItemTurretBlock extends AbstractDirectedTurretBlock<DisposableItemTurretBlockEntity> {
    public static final MapCodec<DisposableItemTurretBlock> CODEC = simpleCodec(DisposableItemTurretBlock::new);

    public DisposableItemTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.DISPOSABLE_ITEM_TURRET::get, DisposableItemTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
