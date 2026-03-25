package com.ommods.openmodularturrets.util;

import com.mojang.datafixers.types.Type;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.Set;

public final class BlockEntityTypeFactory {
    private BlockEntityTypeFactory() {
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends BlockEntity> BlockEntityType<T> create(Factory<T> factory, Block... validBlocks) {
        try {
            Class<?> supplierClass = Class.forName("net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier");
            Object supplier = Proxy.newProxyInstance(
                supplierClass.getClassLoader(),
                new Class<?>[]{supplierClass},
                (proxy, method, args) -> factory.create((BlockPos) args[0], (BlockState) args[1])
            );
            Constructor<BlockEntityType> constructor = (Constructor<BlockEntityType>) BlockEntityType.class.getDeclaredConstructor(
                supplierClass,
                Set.class,
                Type.class
            );
            constructor.setAccessible(true);
            return (BlockEntityType<T>) constructor.newInstance(supplier, Set.of(validBlocks), null);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to create block entity type", exception);
        }
    }

    @FunctionalInterface
    public interface Factory<T extends BlockEntity> {
        T create(BlockPos pos, BlockState state);
    }
}
