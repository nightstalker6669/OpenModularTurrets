package com.ommods.openmodularturrets.content.block.turrets;

import com.ommods.openmodularturrets.OMTBlockEntities;
import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockEntity;
import com.ommods.openmodularturrets.content.item.ammo.AmmoType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class TeleporterTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent TELEPORT_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "teleport"));

    public TeleporterTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.TELEPORTER_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 16 : 12 + base.getTier() * 4;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.NONE;
    }

    @Override
    protected int getEnergyPerShot() {
        return 36;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 35;
    }

    @Override
    protected float getShotDamage() {
        return 0.0F;
    }

    @Override
    protected SoundEvent getFireSound() {
        return TELEPORT_SOUND;
    }

    @Override
    protected ParticleOptions getTracerParticle() {
        return ParticleTypes.PORTAL;
    }

    @Override
    protected void applyHitEffects(Level level, BlockPos pos, LivingEntity target, @Nullable TurretBaseBlockEntity base) {
        target.teleportTo(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
    }

    @Override
    protected void spawnShotFeedback(Level level, BlockPos pos, LivingEntity target) {
        super.spawnShotFeedback(level, pos, target);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 destination = new Vec3(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            serverLevel.sendParticles(ParticleTypes.PORTAL, destination.x, destination.y, destination.z, 18, 0.2D, 0.35D, 0.2D, 0.0D);
        }
    }
}
