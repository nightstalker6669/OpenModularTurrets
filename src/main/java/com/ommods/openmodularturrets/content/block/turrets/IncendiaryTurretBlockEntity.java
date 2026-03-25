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

public class IncendiaryTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent INCENDIARY_FIRE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "incendiary"));

    public IncendiaryTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.INCENDIARY_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 10 : 7 + base.getTier() * 3;
    }

    @Override
    protected float getIdleSweepDegrees() {
        return 28.0F;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.BLAZING_CLAY;
    }

    @Override
    protected int getEnergyPerShot() {
        return 16;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 12;
    }

    @Override
    protected float getShotDamage() {
        return 4.0F;
    }

    @Override
    protected int getFireTicks() {
        return 8;
    }

    @Override
    protected SoundEvent getFireSound() {
        return INCENDIARY_FIRE_SOUND;
    }

    @Override
    protected ParticleOptions getTracerParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    protected ParticleOptions getImpactParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    protected int getImpactParticleCount() {
        return 12;
    }

    @Override
    protected void spawnShotFeedback(Level level, BlockPos pos, LivingEntity target) {
        super.spawnShotFeedback(level, pos, target);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 impact = this.getTargetAimPoint(target);
            serverLevel.sendParticles(ParticleTypes.SMOKE, impact.x, impact.y, impact.z, 5, 0.06D, 0.06D, 0.06D, 0.0D);
        }
    }
}
