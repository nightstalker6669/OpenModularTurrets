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

public class GrenadeTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent GRENADE_FIRE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "grenade"));

    public GrenadeTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.GRENADE_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 12 : 9 + base.getTier() * 4;
    }

    @Override
    protected float getIdlePitchDegrees() {
        return 105.0F;
    }

    @Override
    protected float getPitchStep() {
        return 2.75F;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.GRENADE;
    }

    @Override
    protected int getEnergyPerShot() {
        return 20;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 24;
    }

    @Override
    protected float getShotDamage() {
        return 6.0F;
    }

    @Override
    protected float getSplashRadius() {
        return 2.0F;
    }

    @Override
    protected SoundEvent getFireSound() {
        return GRENADE_FIRE_SOUND;
    }

    @Override
    protected ParticleOptions getTracerParticle() {
        return ParticleTypes.SMOKE;
    }

    @Override
    protected ParticleOptions getImpactParticle() {
        return ParticleTypes.EXPLOSION;
    }

    @Override
    protected int getImpactParticleCount() {
        return 1;
    }

    @Override
    protected void spawnShotFeedback(Level level, BlockPos pos, LivingEntity target) {
        super.spawnShotFeedback(level, pos, target);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 impact = this.getTargetAimPoint(target);
            serverLevel.sendParticles(ParticleTypes.POOF, impact.x, impact.y, impact.z, 10, 0.12D, 0.12D, 0.12D, 0.01D);
        }
    }
}
