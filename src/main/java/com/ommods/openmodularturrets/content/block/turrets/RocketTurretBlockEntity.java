package com.ommods.openmodularturrets.content.block.turrets;

import com.ommods.openmodularturrets.OMTBlockEntities;
import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockEntity;
import com.ommods.openmodularturrets.content.item.ammo.AmmoType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class RocketTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent ROCKET_FIRE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "rocket"));

    public RocketTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.ROCKET_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 16 : 12 + base.getTier() * 4;
    }

    @Override
    protected float getIdleSweepDegrees() {
        return 20.0F;
    }

    @Override
    protected float getIdlePitchDegrees() {
        return 96.0F;
    }

    @Override
    protected float getYawStep() {
        return 3.0F;
    }

    @Override
    protected float getPitchStep() {
        return 2.25F;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.ROCKET_SMALL;
    }

    @Override
    protected int getEnergyPerShot() {
        return 32;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 30;
    }

    @Override
    protected float getShotDamage() {
        return 8.0F;
    }

    @Override
    protected float getSplashRadius() {
        return 2.5F;
    }

    @Override
    protected SoundEvent getFireSound() {
        return ROCKET_FIRE_SOUND;
    }

    @Override
    protected ParticleOptions getTracerParticle() {
        return ParticleTypes.SMOKE;
    }

    @Override
    protected int getTracerParticleCount(Vec3 start, Vec3 end) {
        return Math.max(5, (int) Math.ceil(start.distanceTo(end) * 1.4D));
    }

    @Override
    protected ParticleOptions getImpactParticle() {
        return ParticleTypes.EXPLOSION;
    }

    @Override
    protected int getImpactParticleCount() {
        return 2;
    }

    @Override
    protected void spawnShotFeedback(Level level, BlockPos pos, LivingEntity target) {
        super.spawnShotFeedback(level, pos, target);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 impact = this.getTargetAimPoint(target);
            serverLevel.sendParticles(ParticleTypes.POOF, impact.x, impact.y, impact.z, 8, 0.15D, 0.15D, 0.15D, 0.02D);
        }
    }
}
