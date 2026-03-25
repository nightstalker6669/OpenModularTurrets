package com.ommods.openmodularturrets.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.properties.AttachFace;
import org.joml.Vector3f;

public final class TurretAimHelper {
    private static final float DEG_TO_RAD = 0.017453292F;

    private TurretAimHelper() {
    }

    public static float getAimYawDegrees(BlockPos turretPos, LivingEntity target) {
        double dX = target.getX() - (turretPos.getX() + 0.5D);
        double dZ = target.getZ() - (turretPos.getZ() + 0.5D);
        float yaw = (float) Math.toDegrees(Math.atan2(dX, dZ));
        return yaw < 0.0F ? yaw + 360.0F : yaw;
    }

    public static float getAimPitchDegrees(BlockPos turretPos, LivingEntity target) {
        double dX = (target.getX() - 0.5D) - (turretPos.getX() + 0.5D);
        double dY = (target.getY() + target.getEyeHeight()) - (turretPos.getY() + 0.5D);
        double dZ = (target.getZ() - 0.5D) - (turretPos.getZ() + 0.5D);
        double distance = Math.sqrt(dZ * dZ + dX * dX);
        float pitch = (float) Math.toDegrees(Math.atan2(dY, distance));
        return pitch < 0.0F ? pitch + 360.0F : pitch;
    }

    public static float getAimYawDegrees(Vector3f from, Vector3f to) {
        double dX = to.x - from.x;
        double dZ = to.z - from.z;
        float yaw = (float) Math.toDegrees(Math.atan2(dX, dZ));
        return yaw < 0.0F ? yaw + 360.0F : yaw;
    }

    public static float getAimPitchDegrees(Vector3f from, Vector3f to) {
        double dX = to.x - from.x;
        double dY = to.y - from.y;
        double dZ = to.z - from.z;
        double distance = Math.sqrt(dZ * dZ + dX * dX);
        float pitch = (float) Math.toDegrees(Math.atan2(dY, distance));
        return pitch < 0.0F ? pitch + 360.0F : pitch;
    }

    public static float getRotationXY(float yaw, float pitch, Direction baseFacing) {
        return switch (baseFacing) {
            case UP, DOWN -> (-pitch) * DEG_TO_RAD;
            case EAST -> yaw > 270.0F ? yaw * DEG_TO_RAD : (-yaw - 180.0F) * DEG_TO_RAD;
            case WEST -> yaw < 90.0F ? (-yaw) * DEG_TO_RAD : (yaw + 180.0F) * DEG_TO_RAD;
            case NORTH -> yaw > 180.0F ? (-yaw - 90.0F) * DEG_TO_RAD : (yaw - 90.0F) * DEG_TO_RAD;
            case SOUTH -> yaw < 180.0F ? (-yaw + 90.0F) * DEG_TO_RAD : (yaw + 90.0F) * DEG_TO_RAD;
        };
    }

    public static float getRotationXZ(float yaw, float pitch, Direction baseFacing) {
        return switch (baseFacing) {
            case UP -> (yaw + 180.0F) * DEG_TO_RAD;
            case DOWN -> (-yaw) * DEG_TO_RAD;
            case EAST -> {
                if (yaw > 270.0F) {
                    yield (-pitch - 90.0F) * DEG_TO_RAD;
                }
                if (yaw < 90.0F) {
                    yield (pitch - 90.0F) * DEG_TO_RAD;
                }
                yield (pitch + 90.0F) * DEG_TO_RAD;
            }
            case WEST -> yaw < 90.0F ? (pitch + 90.0F) * DEG_TO_RAD : (-pitch - 90.0F) * DEG_TO_RAD;
            case NORTH -> yaw < 180.0F ? (-pitch - 90.0F) * DEG_TO_RAD : (pitch + 90.0F) * DEG_TO_RAD;
            case SOUTH -> yaw > 180.0F ? (-pitch - 90.0F) * DEG_TO_RAD : (pitch + 90.0F) * DEG_TO_RAD;
        };
    }

    public static Direction getBaseFacing(AttachFace face, Direction facing) {
        return switch (face) {
            case FLOOR -> Direction.DOWN;
            case CEILING -> Direction.UP;
            case WALL -> facing.getOpposite();
        };
    }
}
