package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import com.ommods.openmodularturrets.OMTBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MachineGunTurretBlock extends BaseEntityBlock {
    public static final MapCodec<MachineGunTurretBlock> CODEC = simpleCodec(MachineGunTurretBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;

    private static final VoxelShape FLOOR_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 14.0, 14.0);
    private static final VoxelShape CEILING_SHAPE = Block.box(2.0, 2.0, 2.0, 14.0, 16.0, 14.0);
    private static final VoxelShape WALL_NORTH_SHAPE = Block.box(2.0, 2.0, 0.0, 14.0, 14.0, 12.0);
    private static final VoxelShape WALL_SOUTH_SHAPE = Block.box(2.0, 2.0, 4.0, 14.0, 14.0, 16.0);
    private static final VoxelShape WALL_WEST_SHAPE = Block.box(0.0, 2.0, 2.0, 12.0, 14.0, 14.0);
    private static final VoxelShape WALL_EAST_SHAPE = Block.box(4.0, 2.0, 2.0, 16.0, 14.0, 14.0);

    public MachineGunTurretBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.FLOOR));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MachineGunTurretBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return createTickerHelper(blockEntityType, OMTBlockEntities.MACHINE_GUN_TURRET.get(), DirectedTurretBlockEntity::clientTick);
        }
        return createTickerHelper(blockEntityType, OMTBlockEntities.MACHINE_GUN_TURRET.get(), DirectedTurretBlockEntity::serverTick);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        BlockState clickedFaceState;
        if (clickedFace.getAxis() == Direction.Axis.Y) {
            clickedFaceState = this.defaultBlockState()
                .setValue(FACE, clickedFace == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING)
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
        } else {
            clickedFaceState = this.defaultBlockState()
                .setValue(FACE, AttachFace.WALL)
                .setValue(FACING, clickedFace.getOpposite());
        }

        if (clickedFaceState.canSurvive(context.getLevel(), context.getClickedPos())) {
            return clickedFaceState;
        }

        for (Direction direction : context.getNearestLookingDirections()) {
            BlockState fallbackState;
            if (direction.getAxis() == Direction.Axis.Y) {
                fallbackState = this.defaultBlockState()
                    .setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR)
                    .setValue(FACING, context.getHorizontalDirection().getOpposite());
            } else {
                fallbackState = this.defaultBlockState()
                    .setValue(FACE, AttachFace.WALL)
                    .setValue(FACING, direction.getOpposite());
            }

            if (fallbackState.canSurvive(context.getLevel(), context.getClickedPos())) {
                return fallbackState;
            }
        }
        return null;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canAttach(level, pos, getConnectedDirection(state).getOpposite());
    }

    public static boolean canAttach(LevelReader level, BlockPos pos, Direction direction) {
        BlockPos attachedPos = pos.relative(direction);
        return level.getBlockState(attachedPos).isFaceSturdy(level, attachedPos, direction.getOpposite());
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return getConnectedDirection(state).getOpposite() == direction && !state.canSurvive(level, pos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.getTurretShape(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.getTurretShape(state);
    }

    private VoxelShape getTurretShape(BlockState state) {
        AttachFace face = state.getValue(FACE);
        Direction facing = state.getValue(FACING);
        return switch (face) {
            case FLOOR -> FLOOR_SHAPE;
            case CEILING -> CEILING_SHAPE;
            case WALL -> switch (facing) {
                case NORTH -> WALL_NORTH_SHAPE;
                case SOUTH -> WALL_SOUTH_SHAPE;
                case EAST -> WALL_EAST_SHAPE;
                case WEST -> WALL_WEST_SHAPE;
                default -> FLOOR_SHAPE;
            };
        };
    }

    private static Direction getConnectedDirection(BlockState state) {
        return switch (state.getValue(FACE)) {
            case CEILING -> Direction.DOWN;
            case FLOOR -> Direction.UP;
            case WALL -> state.getValue(FACING);
        };
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FACE);
    }
}
