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

public class PlasmaTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent PLASMA_FIRE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "plasma_launch"));

    public PlasmaTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.PLASMA_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 18 : 14 + base.getTier() * 4;
    }

    @Override
    protected float getIdleSweepDegrees() {
        return 18.0F;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.NONE;
    }

    @Override
    protected int getEnergyPerShot() {
        return 28;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 20;
    }

    @Override
    protected float getShotDamage() {
        return 7.0F;
    }

    @Override
    protected float getSplashRadius() {
        return 1.5F;
    }

    @Override
    protected SoundEvent getFireSound() {
        return PLASMA_FIRE_SOUND;
    }

    @Override
    protected ParticleOptions getTracerParticle() {
        return ParticleTypes.END_ROD;
    }

    @Override
    protected int getTracerParticleCount(Vec3 start, Vec3 end) {
        return Math.max(5, (int) Math.ceil(start.distanceTo(end) * 3.0D));
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
            serverLevel.sendParticles(ParticleTypes.END_ROD, impact.x, impact.y, impact.z, 10, 0.1D, 0.1D, 0.1D, 0.01D);
        }
    }
}
