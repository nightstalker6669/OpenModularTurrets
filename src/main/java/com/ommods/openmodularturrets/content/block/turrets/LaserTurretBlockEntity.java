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

public class LaserTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent LASER_FIRE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "laser"));
    private static final SoundEvent LASER_HIT_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "laser_hit"));

    public LaserTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.LASER_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 14 : 10 + base.getTier() * 4;
    }

    @Override
    protected float getIdleSweepDegrees() {
        return 25.0F;
    }

    @Override
    protected float getIdlePitchDegrees() {
        return 92.0F;
    }

    @Override
    protected float getYawStep() {
        return 4.5F;
    }

    @Override
    protected float getPitchStep() {
        return 3.0F;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.NONE;
    }

    @Override
    protected int getEnergyPerShot() {
        return 18;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 10;
    }

    @Override
    protected float getShotDamage() {
        return 5.0F;
    }

    @Override
    protected SoundEvent getFireSound() {
        return LASER_FIRE_SOUND;
    }

    @Override
    protected SoundEvent getHitSound() {
        return LASER_HIT_SOUND;
    }

    @Override
    protected ParticleOptions getTracerParticle() {
        return ParticleTypes.END_ROD;
    }

    @Override
    protected int getTracerParticleCount(Vec3 start, Vec3 end) {
        return Math.max(10, (int) Math.ceil(start.distanceTo(end) * 5.0D));
    }

    @Override
    protected int getImpactParticleCount() {
        return 12;
    }

    @Override
    protected double getImpactParticleSpread() {
        return 0.12D;
    }

    @Override
    protected void spawnShotFeedback(Level level, BlockPos pos, LivingEntity target) {
        super.spawnShotFeedback(level, pos, target);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 impact = this.getTargetAimPoint(target);
            serverLevel.sendParticles(ParticleTypes.FLASH, impact.x, impact.y, impact.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }
}
