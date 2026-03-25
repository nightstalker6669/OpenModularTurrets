package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.world.level.block.BaseEntityBlock;

public class PotatoCannonTurretBlock extends AbstractDirectedTurretBlock<PotatoCannonTurretBlockEntity> {
    public static final MapCodec<PotatoCannonTurretBlock> CODEC = simpleCodec(PotatoCannonTurretBlock::new);

    public PotatoCannonTurretBlock(Properties properties) {
        super(properties, OMTBlockEntities.POTATO_CANNON_TURRET::get, PotatoCannonTurretBlockEntity::new);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
