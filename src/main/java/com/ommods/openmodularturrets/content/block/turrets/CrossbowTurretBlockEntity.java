package com.ommods.openmodularturrets.content.block.turrets;

import com.ommods.openmodularturrets.OMTBlockEntities;
import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockEntity;
import com.ommods.openmodularturrets.content.item.ammo.AmmoType;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrossbowTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent CROSSBOW_FIRE_SOUND = SoundEvents.CROSSBOW_SHOOT;

    public CrossbowTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.CROSSBOW_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 14 : 10 + base.getTier() * 4;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.CROSSBOW_BOLT;
    }

    @Override
    protected int getEnergyPerShot() {
        return 10;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 18;
    }

    @Override
    protected float getShotDamage() {
        return 5.0F;
    }

    @Override
    protected float getShotOriginForwardOffset() {
        return -0.95F;
    }

    @Override
    protected SoundEvent getFireSound() {
        return CROSSBOW_FIRE_SOUND;
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ARROW_HIT;
    }

    @Override
    protected ParticleOptions getTracerParticle() {
        return ParticleTypes.CRIT;
    }
}
