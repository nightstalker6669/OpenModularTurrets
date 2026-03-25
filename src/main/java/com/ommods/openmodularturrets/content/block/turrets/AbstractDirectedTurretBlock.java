package com.ommods.openmodularturrets.content.block.turrets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public abstract class AbstractDirectedTurretBlock<T extends DirectedTurretBlockEntity> extends MachineGunTurretBlock {
    private final Supplier<BlockEntityType<T>> typeSupplier;
    private final BiFunction<BlockPos, BlockState, T> factory;

    protected AbstractDirectedTurretBlock(Properties properties, Supplier<BlockEntityType<T>> typeSupplier, BiFunction<BlockPos, BlockState, T> factory) {
        super(properties);
        this.typeSupplier = typeSupplier;
        this.factory = factory;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return this.factory.apply(pos, state);
    }

    @Override
    public <E extends BlockEntity> BlockEntityTicker<E> getTicker(Level level, BlockState state, BlockEntityType<E> blockEntityType) {
        if (level.isClientSide) {
            return createTickerHelper(blockEntityType, this.typeSupplier.get(), DirectedTurretBlockEntity::clientTick);
        }
        return createTickerHelper(blockEntityType, this.typeSupplier.get(), DirectedTurretBlockEntity::serverTick);
    }
}
