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

import java.util.List;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class ArcTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent ARC_FIRE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "arc"));

    public ArcTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.ARC_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 14 : 10 + base.getTier() * 3;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.NONE;
    }

    @Override
    protected int getEnergyPerShot() {
        return 24;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 26;
    }

    @Override
    protected float getShotDamage() {
        return 6.0F;
    }

    @Override
    protected SoundEvent getFireSound() {
        return ARC_FIRE_SOUND;
    }

    @Override
    protected ParticleOptions getTracerParticle() {
        return ParticleTypes.ELECTRIC_SPARK;
    }

    @Override
    protected void applyHitEffects(Level level, BlockPos pos, LivingEntity target, @Nullable TurretBaseBlockEntity base) {
        super.applyHitEffects(level, pos, target, base);
        List<LivingEntity> chainedTargets = level.getEntitiesOfClass(
            LivingEntity.class,
            target.getBoundingBox().inflate(3.0D),
            entity -> entity.isAlive() && entity != target
        );
        int chains = 0;
        for (LivingEntity chainedTarget : chainedTargets) {
            chainedTarget.hurt(level.damageSources().generic(), 2.5F);
            chains++;
            if (chains >= 2) {
                break;
            }
        }
    }

    @Override
    protected void spawnShotFeedback(Level level, BlockPos pos, LivingEntity target) {
        super.spawnShotFeedback(level, pos, target);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 start = this.getShotStart(pos, target);
            Vec3 impact = this.getTargetAimPoint(target);
            List<LivingEntity> chainedTargets = level.getEntitiesOfClass(
                LivingEntity.class,
                target.getBoundingBox().inflate(3.0D),
                entity -> entity.isAlive() && entity != target
            );
            int chains = 0;
            for (LivingEntity chainedTarget : chainedTargets) {
                Vec3 chainedImpact = this.getTargetAimPoint(chainedTarget);
                this.spawnTracer(serverLevel, impact, chainedImpact, ParticleTypes.ELECTRIC_SPARK, 6);
                chains++;
                if (chains >= 2) {
                    break;
                }
            }
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, start.x, start.y, start.z, 4, 0.03D, 0.03D, 0.03D, 0.0D);
        }
    }
}
