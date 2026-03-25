package com.ommods.openmodularturrets.content.block.turrets;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SimpleTurretHeadBlock extends FaceAttachedHorizontalDirectionalBlock {
    public static final MapCodec<SimpleTurretHeadBlock> CODEC = simpleCodec(SimpleTurretHeadBlock::new);

    private static final VoxelShape FLOOR_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 14.0, 14.0);
    private static final VoxelShape CEILING_SHAPE = Block.box(2.0, 2.0, 2.0, 14.0, 16.0, 14.0);
    private static final VoxelShape WALL_NORTH_SHAPE = Block.box(2.0, 2.0, 0.0, 14.0, 14.0, 12.0);
    private static final VoxelShape WALL_SOUTH_SHAPE = Block.box(2.0, 2.0, 4.0, 14.0, 14.0, 16.0);
    private static final VoxelShape WALL_WEST_SHAPE = Block.box(0.0, 2.0, 2.0, 12.0, 14.0, 14.0);
    private static final VoxelShape WALL_EAST_SHAPE = Block.box(4.0, 2.0, 2.0, 16.0, 14.0, 14.0);

    public SimpleTurretHeadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.FLOOR));
    }

    @Override
    protected MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
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
