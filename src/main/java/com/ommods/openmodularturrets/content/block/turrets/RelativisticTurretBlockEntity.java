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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class RelativisticTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent RELATIVISTIC_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "relativistic"));

    public RelativisticTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.RELATIVISTIC_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 18 : 14 + base.getTier() * 4;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.NONE;
    }

    @Override
    protected int getEnergyPerShot() {
        return 40;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 45;
    }

    @Override
    protected float getShotDamage() {
        return 0.0F;
    }

    @Override
    protected SoundEvent getFireSound() {
        return RELATIVISTIC_SOUND;
    }

    @Override
    protected ParticleOptions getTracerParticle() {
        return ParticleTypes.WITCH;
    }

    @Override
    protected int getTracerParticleCount(Vec3 start, Vec3 end) {
        return Math.max(6, (int) Math.ceil(start.distanceTo(end) * 2.5D));
    }

    @Override
    protected void applyHitEffects(Level level, BlockPos pos, LivingEntity target, @Nullable TurretBaseBlockEntity base) {
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 3, false, true));
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1, false, true));
    }

    @Override
    protected void spawnShotFeedback(Level level, BlockPos pos, LivingEntity target) {
        super.spawnShotFeedback(level, pos, target);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 impact = this.getTargetAimPoint(target);
            serverLevel.sendParticles(ParticleTypes.ENCHANT, impact.x, impact.y, impact.z, 12, 0.12D, 0.12D, 0.12D, 0.0D);
        }
    }
}
