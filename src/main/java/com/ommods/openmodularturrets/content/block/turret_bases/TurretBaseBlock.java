package com.ommods.openmodularturrets.content.block.turret_bases;

import com.ommods.omlib.block.AbstractOMLibBlockWithEntity;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class TurretBaseBlock extends AbstractOMLibBlockWithEntity {

    public TurretBaseBlock(Properties pProperties) {
        super(pProperties);
    }

    protected abstract int getTier();

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TurretBaseBlockEntity(OMTBlockEntities.TURRET_BASE.get(), pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return !pLevel.isClientSide() ? createTickerHelper(pBlockEntityType, OMTBlockEntities.TURRET_BASE.get(), TurretBaseBlockEntity::tick) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel pLevel, T pBlockEntity) {
        return null;
    }

    @Override
    public float defaultDestroyTime() {
        return Float.MAX_VALUE;
    }

    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        return false;
    }

    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return Float.MAX_VALUE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof TurretBaseBlockEntity turretBaseBlockEntity) {
            return turretBaseBlockEntity.getSerialPortSignal();
        }
        return 0;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull InteractionResult OM_use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!(pLevel.getBlockEntity(pPos) instanceof TurretBaseBlockEntity turretBaseBlockEntity)) {
            return InteractionResult.PASS;
        }
        if (!pLevel.isClientSide() && pPlayer instanceof ServerPlayer serverPlayer) {
            openTurretBaseMenu(serverPlayer, turretBaseBlockEntity, pPos);
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    private static void openTurretBaseMenu(ServerPlayer serverPlayer, TurretBaseBlockEntity turretBaseBlockEntity, BlockPos pos) {
        try {
            Class<?> extensionClass = Class.forName("net.neoforged.neoforge.common.extensions.IPlayerExtension");
            Method openMenuMethod = extensionClass.getMethod("openMenu", net.minecraft.world.MenuProvider.class, java.util.function.Consumer.class);
            openMenuMethod.invoke(serverPlayer, turretBaseBlockEntity, (java.util.function.Consumer<Object>) buffer -> {
                try {
                    Method writeBlockPos = buffer.getClass().getMethod("writeBlockPos", BlockPos.class);
                    writeBlockPos.invoke(buffer, pos);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
                    throw new IllegalStateException("Failed to write turret base menu position", exception);
                }
            });
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            throw new IllegalStateException("Failed to open turret base menu", exception);
        }
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);

        TurretBaseBlockEntity b = (TurretBaseBlockEntity) pLevel.getBlockEntity(pPos);
        if (b != null) {
            b.updateTurrets();
        }
    }
}
