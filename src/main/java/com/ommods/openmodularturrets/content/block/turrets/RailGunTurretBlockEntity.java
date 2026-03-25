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

public class RailGunTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent RAIL_GUN_FIRE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "rail_gun"));
    private static final SoundEvent RAIL_GUN_HIT_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "rail_gun_hit"));

    public RailGunTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.RAIL_GUN_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 18 : 14 + base.getTier() * 5;
    }

    @Override
    protected float getYawStep() {
        return 2.5F;
    }

    @Override
    protected float getPitchStep() {
        return 2.0F;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.RAILGUN_SLUG;
    }

    @Override
    protected int getEnergyPerShot() {
        return 40;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 18;
    }

    @Override
    protected float getShotDamage() {
        return 10.0F;
    }

    @Override
    protected SoundEvent getFireSound() {
        return RAIL_GUN_FIRE_SOUND;
    }

    @Override
    protected SoundEvent getHitSound() {
        return RAIL_GUN_HIT_SOUND;
    }

    @Override
    protected ParticleOptions getTracerParticle() {
        return ParticleTypes.ELECTRIC_SPARK;
    }

    @Override
    protected int getTracerParticleCount(Vec3 start, Vec3 end) {
        return Math.max(6, (int) Math.ceil(start.distanceTo(end) * 3.5D));
    }

    @Override
    protected int getImpactParticleCount() {
        return 14;
    }

    @Override
    protected void spawnShotFeedback(Level level, BlockPos pos, LivingEntity target) {
        super.spawnShotFeedback(level, pos, target);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 impact = this.getTargetAimPoint(target);
            serverLevel.sendParticles(ParticleTypes.CRIT, impact.x, impact.y, impact.z, 6, 0.06D, 0.06D, 0.06D, 0.0D);
        }
    }
}
