package com.ommods.openmodularturrets.content.block.turrets;

import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockEntity;
import com.ommods.openmodularturrets.content.item.ammo.AmmoType;
import com.ommods.openmodularturrets.content.item.upgrades.UpgradeEffect;
import com.ommods.openmodularturrets.util.TurretAimHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class DirectedTurretBlockEntity extends BlockEntity {
    protected float yaw = 180.0F;
    protected float pitch = 90.0F;
    protected float renderYaw = 180.0F;
    protected float renderPitch = 90.0F;
    protected float targetYaw = 180.0F;
    protected float targetPitch = 90.0F;
    protected float idlePhase;
    protected int syncCooldown;
    protected int shotCooldown;
    protected int activityTicks;

    protected DirectedTurretBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public float getRenderYaw() {
        return this.renderYaw;
    }

    public float getRenderPitch() {
        return this.renderPitch;
    }

    public Direction getBaseFacing() {
        if (this.level != null) {
            for (Direction direction : Direction.values()) {
                if (this.level.getBlockEntity(this.worldPosition.relative(direction)) instanceof TurretBaseBlockEntity) {
                    return direction;
                }
            }
        }
        AttachFace face = this.getBlockState().getValue(MachineGunTurretBlock.FACE);
        Direction facing = this.getBlockState().getValue(MachineGunTurretBlock.FACING);
        return TurretAimHelper.getBaseFacing(face, facing);
    }

    protected abstract int getTargetRange(@Nullable TurretBaseBlockEntity base);

    protected AmmoType getAmmoType() {
        return AmmoType.BULLET;
    }

    protected int getAmmoPerShot() {
        return this.getAmmoType() == AmmoType.NONE ? 0 : 1;
    }

    protected int getEnergyPerShot() {
        return 12;
    }

    protected int getShotCooldownTicks() {
        return 10;
    }

    protected float getShotOriginForwardOffset() {
        return 0.6F;
    }

    protected float getAimToleranceDegrees() {
        return 7.5F;
    }

    protected float getShotDamage() {
        return 4.0F;
    }

    protected float getSplashRadius() {
        return 0.0F;
    }

    protected int getFireTicks() {
        return 0;
    }

    protected SoundEvent getFireSound() {
        return SoundEvents.CROSSBOW_SHOOT;
    }

    @Nullable
    protected SoundEvent getHitSound() {
        return null;
    }

    protected SoundSource getFireSoundSource() {
        return SoundSource.BLOCKS;
    }

    protected ParticleOptions getTracerParticle() {
        return ParticleTypes.CRIT;
    }

    protected ParticleOptions getImpactParticle() {
        return this.getTracerParticle();
    }

    protected int getTracerParticleCount(Vec3 start, Vec3 end) {
        return Math.max(3, (int) Math.ceil(start.distanceTo(end) * 2.0D));
    }

    protected int getImpactParticleCount() {
        return 8;
    }

    protected double getImpactParticleSpread() {
        return 0.08D;
    }

    protected ItemStack getShotParticleItem() {
        return ItemStack.EMPTY;
    }

    protected float getIdleSweepDegrees() {
        return 35.0F;
    }

    protected float getIdlePitchDegrees() {
        return 100.0F;
    }

    protected float getYawStep() {
        return 6.0F;
    }

    protected float getPitchStep() {
        return 4.0F;
    }

    public static <T extends DirectedTurretBlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T turret) {
        turret.tickServer(level, pos);
    }

    public static <T extends DirectedTurretBlockEntity> void clientTick(Level level, BlockPos pos, BlockState state, T turret) {
        turret.renderYaw = Mth.approachDegrees(turret.renderYaw, turret.yaw, turret.getYawStep());
        turret.renderPitch = Mth.approachDegrees(turret.renderPitch, turret.pitch, turret.getPitchStep());
    }

    protected void tickServer(Level level, BlockPos pos) {
        this.idlePhase += 0.045F;
        if (this.shotCooldown > 0) {
            this.shotCooldown--;
        }
        if (this.activityTicks > 0) {
            this.activityTicks--;
        }

        TurretBaseBlockEntity base = this.findAttachedBase(level, pos);
        LivingEntity target = base == null ? null : this.findTarget(level, pos, base);

        if (target == null) {
            this.targetYaw = 180.0F + Mth.sin(this.idlePhase) * this.getIdleSweepDegrees();
            this.targetPitch = this.getIdlePitchDegrees() + Mth.cos(this.idlePhase * 0.6F) * 5.0F;
        } else {
            this.activityTicks = 10;
            this.targetYaw = TurretAimHelper.getAimYawDegrees(pos, target);
            this.targetPitch = TurretAimHelper.getAimPitchDegrees(pos, target);
        }

        float oldYaw = this.yaw;
        float oldPitch = this.pitch;
        this.yaw = Mth.approachDegrees(this.yaw, this.targetYaw, this.getYawStep());
        this.pitch = Mth.approachDegrees(this.pitch, this.targetPitch, this.getPitchStep());
        this.renderYaw = this.yaw;
        this.renderPitch = this.pitch;

        if (target != null && base != null && this.shotCooldown <= 0 && this.isAimedAtTarget(base)) {
            this.tryFire(level, pos, base, target);
        }

        if (Math.abs(Mth.degreesDifference(oldYaw, this.yaw)) > 0.01F || Math.abs(oldPitch - this.pitch) > 0.01F) {
            this.setChanged();
            this.syncCooldown++;
            if (this.syncCooldown >= 3) {
                this.syncCooldown = 0;
                level.sendBlockUpdated(pos, this.getBlockState(), this.getBlockState(), 3);
            }
        }
    }

    protected Vector3f getAimOrigin(BlockPos pos) {
        return new Vector3f((float) pos.getX() + 0.5F, (float) pos.getY() + 0.75F, (float) pos.getZ() + 0.5F);
    }

    private boolean isAimedAtTarget(@Nullable TurretBaseBlockEntity base) {
        float tolerance = this.getEffectiveAimTolerance(base);
        return Math.abs(Mth.degreesDifference(this.yaw, this.targetYaw)) <= tolerance
            && Math.abs(this.pitch - this.targetPitch) <= tolerance;
    }

    private void tryFire(Level level, BlockPos pos, TurretBaseBlockEntity base, LivingEntity target) {
        if (!this.consumeShotResources(base)) {
            return;
        }

        this.shotCooldown = this.getEffectiveShotCooldownTicks(base);
        this.applyHitEffects(level, pos, target, base);
        this.spawnShotFeedback(level, pos, target);
        this.setChanged();
        level.sendBlockUpdated(pos, this.getBlockState(), this.getBlockState(), 3);
    }

    protected boolean canConsumeShotResources(TurretBaseBlockEntity base) {
        return base.canConsumeShotResources(this.getAmmoType(), this.getAmmoPerShot(), this.getEnergyPerShot());
    }

    protected boolean consumeShotResources(TurretBaseBlockEntity base) {
        return base.consumeShotResources(this.getAmmoType(), this.getAmmoPerShot(), this.getEnergyPerShot());
    }

    protected void applyHitEffects(Level level, BlockPos pos, LivingEntity target, @Nullable TurretBaseBlockEntity base) {
        float damage = this.getEffectiveShotDamage(base);
        if (damage > 0.0F) {
            if (base != null && base.hasFakeDropsAddon() && level instanceof ServerLevel serverLevel) {
                target.hurt(level.damageSources().playerAttack(FakePlayerFactory.getMinecraft(serverLevel)), damage);
            } else {
                target.hurt(level.damageSources().generic(), damage);
            }
        }
        if (this.getFireTicks() > 0) {
            target.igniteForSeconds(this.getFireTicks());
        }
        float splashRadius = this.getEffectiveSplashRadius(base);
        if (splashRadius > 0.0F) {
            AABB splashBox = target.getBoundingBox().inflate(splashRadius);
            List<LivingEntity> impacted = level.getEntitiesOfClass(LivingEntity.class, splashBox, entity -> entity.isAlive() && entity != target);
            for (LivingEntity livingEntity : impacted) {
                livingEntity.hurt(level.damageSources().generic(), Math.max(1.0F, damage * 0.6F));
                if (this.getFireTicks() > 0) {
                    livingEntity.igniteForSeconds(Math.max(1, this.getFireTicks() / 2));
                }
            }
        }
        int scatterTargets = this.getEffectiveScatterShotCount(base);
        if (scatterTargets > 0) {
            AABB scatterBox = target.getBoundingBox().inflate(2.0D + scatterTargets * 0.5D);
            List<LivingEntity> nearby = level.getEntitiesOfClass(
                LivingEntity.class,
                scatterBox,
                entity -> entity.isAlive() && entity != target && entity.distanceToSqr(target) <= 16.0D
            );
            nearby.sort(Comparator.comparingDouble(entity -> entity.distanceToSqr(target)));
            float scatterDamage = Math.max(1.0F, damage * 0.45F);
            for (int i = 0; i < Math.min(scatterTargets, nearby.size()); i++) {
                nearby.get(i).hurt(level.damageSources().generic(), scatterDamage);
            }
        }
        SoundEvent hitSound = this.getHitSound();
        if (hitSound != null) {
            level.playSound(null, target.blockPosition(), hitSound, SoundSource.AMBIENT, 0.8F, 0.85F + level.random.nextFloat() * 0.3F);
        }
    }

    protected void spawnShotFeedback(Level level, BlockPos pos, LivingEntity target) {
        level.playSound(null, pos, this.getFireSound(), this.getFireSoundSource(), 0.8F, 0.95F + level.random.nextFloat() * 0.1F);
        this.activityTicks = 10;
        if (level instanceof ServerLevel serverLevel) {
            Vec3 start = this.getShotStart(pos, target);
            Vec3 end = this.getTargetAimPoint(target);
            this.spawnTracer(serverLevel, start, end, this.getTracerParticle(), this.getTracerParticleCount(start, end));
            this.spawnImpactParticles(serverLevel, end, this.getImpactParticle(), this.getImpactParticleCount(), this.getImpactParticleSpread());
        }
    }

    @Nullable
    private TurretBaseBlockEntity findAttachedBase(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (level.getBlockEntity(pos.relative(direction)) instanceof TurretBaseBlockEntity base) {
                return base;
            }
        }
        return null;
    }

    @Nullable
    private LivingEntity findTarget(Level level, BlockPos pos, @Nullable TurretBaseBlockEntity base) {
        if (base == null || (!base.isAttacksMobs() && !base.isAttacksNeutrals() && !base.isAttacksPlayers())) {
            return null;
        }

        int range = this.getEffectiveTargetRange(base);
        int verticalAllowance = this.getVerticalTargetAllowance(base);
        AABB searchBox = new AABB(pos).inflate(range, verticalAllowance, range);
        List<LivingEntity> candidates = new ArrayList<>();

        if (base.isAttacksMobs()) {
            candidates.addAll(level.getEntitiesOfClass(Mob.class, searchBox, entity -> entity.isAlive() && entity instanceof Enemy));
        }
        if (base.isAttacksNeutrals()) {
            candidates.addAll(level.getEntitiesOfClass(Mob.class, searchBox, entity -> entity.isAlive() && !(entity instanceof Enemy)));
        }
        if (base.isAttacksPlayers()) {
            candidates.addAll(level.getEntitiesOfClass(Player.class, searchBox, player -> player.isAlive() && !player.isSpectator() && !player.isCreative()));
        }

        return candidates.stream()
            .filter(entity -> this.isWithinVerticalAllowance(pos, entity, verticalAllowance))
            .filter(entity -> this.canSee(level, pos, entity))
            .min(Comparator.comparingDouble(entity -> entity.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D)))
            .orElse(null);
    }

    private int getVerticalTargetAllowance(@Nullable TurretBaseBlockEntity base) {
        if (base == null) {
            return this.getEffectiveTargetRange(null);
        }
        return Math.max(1, base.getYAxisDetect() + 1);
    }

    protected int getEffectiveTargetRange(@Nullable TurretBaseBlockEntity base) {
        int baseRange = this.getTargetRange(base);
        if (base == null) {
            return baseRange;
        }
        return Math.max(1, Math.round(baseRange * base.getRangeMultiplier()));
    }

    protected int getEffectiveShotCooldownTicks(@Nullable TurretBaseBlockEntity base) {
        int baseCooldown = this.getShotCooldownTicks();
        if (base == null) {
            return baseCooldown;
        }
        return Math.max(1, Math.round(baseCooldown / base.getFireRateMultiplier()));
    }

    protected float getEffectiveAimTolerance(@Nullable TurretBaseBlockEntity base) {
        float baseTolerance = this.getAimToleranceDegrees();
        if (base == null) {
            return baseTolerance;
        }
        return Math.max(1.5F, baseTolerance / base.getAccuracyMultiplier());
    }

    protected float getEffectiveShotDamage(@Nullable TurretBaseBlockEntity base) {
        float baseDamage = this.getShotDamage();
        if (base == null) {
            return baseDamage;
        }
        return baseDamage * base.getDamageMultiplier();
    }

    protected float getEffectiveSplashRadius(@Nullable TurretBaseBlockEntity base) {
        float splash = this.getSplashRadius();
        if (base == null || splash <= 0.0F) {
            return splash;
        }
        return splash + (base.getUpgradeCount(UpgradeEffect.ACCURACY) * 0.1F);
    }

    protected int getEffectiveScatterShotCount(@Nullable TurretBaseBlockEntity base) {
        if (base == null) {
            return 0;
        }
        return base.getScatterShotCount();
    }

    private boolean isWithinVerticalAllowance(BlockPos pos, LivingEntity target, int verticalAllowance) {
        double aimY = pos.getY() + 0.75D;
        double targetY = target.getY() + target.getEyeHeight() * 0.85D;
        return Math.abs(targetY - aimY) <= verticalAllowance;
    }

    private boolean canSee(Level level, BlockPos pos, LivingEntity target) {
        BlockPos attachedBasePos = null;
        TurretBaseBlockEntity attachedBase = this.findAttachedBase(level, pos);
        if (attachedBase != null) {
            attachedBasePos = attachedBase.getBlockPos();
        }
        Vector3f aimOrigin = this.getShotOrigin(pos, target);

        HitResult result = level.clip(
            new ClipContext(
                new Vec3(aimOrigin.x, aimOrigin.y, aimOrigin.z),
                new Vec3(target.getX(), target.getY() + target.getEyeHeight() * 0.85D, target.getZ()),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                target
            )
        );
        if (result.getType() == HitResult.Type.MISS || result.getLocation().distanceToSqr(target.position()) < 1.0D) {
            return true;
        }
        if (result instanceof BlockHitResult blockHitResult) {
            BlockPos hitPos = blockHitResult.getBlockPos();
            if (hitPos.equals(pos) || (attachedBasePos != null && hitPos.equals(attachedBasePos))) {
                return true;
            }
        }
        return false;
    }

    private Vector3f getShotOrigin(BlockPos pos, LivingEntity target) {
        Vector3f origin = this.getAimOrigin(pos);
        Vector3f targetPoint = new Vector3f((float) target.getX(), (float) (target.getY() + target.getEyeHeight() * 0.85F), (float) target.getZ());
        Vector3f delta = targetPoint.sub(origin, new Vector3f());
        if (delta.lengthSquared() > 0.0001F) {
            delta.normalize().mul(this.getShotOriginForwardOffset());
            origin.add(delta);
        }
        return origin;
    }

    protected Vec3 getShotStart(BlockPos pos, LivingEntity target) {
        Vector3f origin = this.getShotOrigin(pos, target);
        return new Vec3(origin.x, origin.y, origin.z);
    }

    protected Vec3 getTargetAimPoint(LivingEntity target) {
        return new Vec3(target.getX(), target.getY() + target.getEyeHeight() * 0.85D, target.getZ());
    }

    protected void spawnTracer(ServerLevel serverLevel, Vec3 start, Vec3 end, ParticleOptions particle, int particles) {
        Vec3 delta = end.subtract(start);
        for (int i = 0; i <= particles; i++) {
            Vec3 point = start.add(delta.scale(i / (double) particles));
            serverLevel.sendParticles(particle, point.x, point.y, point.z, 1, 0.01D, 0.01D, 0.01D, 0.0D);
        }
    }

    protected void spawnImpactParticles(ServerLevel serverLevel, Vec3 center, ParticleOptions particle, int count, double spread) {
        serverLevel.sendParticles(particle, center.x, center.y, center.z, count, spread, spread, spread, 0.0D);
    }

    public boolean isConcealed() {
        if (this.level == null || this.level.isClientSide()) {
            return false;
        }
        TurretBaseBlockEntity base = this.findAttachedBase(this.level, this.worldPosition);
        return base != null && base.hasConcealerAddon() && this.activityTicks <= 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putFloat("yaw", this.yaw);
        tag.putFloat("pitch", this.pitch);
        tag.putFloat("render_yaw", this.renderYaw);
        tag.putFloat("render_pitch", this.renderPitch);
        tag.putFloat("target_yaw", this.targetYaw);
        tag.putFloat("target_pitch", this.targetPitch);
        tag.putFloat("idle_phase", this.idlePhase);
        tag.putInt("shot_cooldown", this.shotCooldown);
        tag.putInt("activity_ticks", this.activityTicks);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.yaw = tag.getFloat("yaw");
        this.pitch = tag.getFloat("pitch");
        this.renderYaw = tag.contains("render_yaw") ? tag.getFloat("render_yaw") : this.yaw;
        this.renderPitch = tag.contains("render_pitch") ? tag.getFloat("render_pitch") : this.pitch;
        this.targetYaw = tag.getFloat("target_yaw");
        this.targetPitch = tag.getFloat("target_pitch");
        this.idlePhase = tag.getFloat("idle_phase");
        this.shotCooldown = tag.getInt("shot_cooldown");
        this.activityTicks = tag.getInt("activity_ticks");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
