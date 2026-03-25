package com.ommods.openmodularturrets.content.block.turrets;

import com.ommods.openmodularturrets.OMTBlockEntities;
import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockEntity;
import com.ommods.openmodularturrets.content.item.ammo.AmmoType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class DisposableItemTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent DISPOSABLE_FIRE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "disposable"));
    private ItemStack lastFiredItem = ItemStack.EMPTY;

    public DisposableItemTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.DISPOSABLE_ITEM_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 10 : 8 + base.getTier() * 3;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.DISPOSABLE_ITEM;
    }

    @Override
    protected int getEnergyPerShot() {
        return 8;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 14;
    }

    @Override
    protected float getShotDamage() {
        return 4.0F;
    }

    @Override
    protected SoundEvent getFireSound() {
        return DISPOSABLE_FIRE_SOUND;
    }

    @Override
    protected boolean canConsumeShotResources(TurretBaseBlockEntity base) {
        return base.canConsumeAnyAmmoItem(this.getAmmoPerShot(), this.getEnergyPerShot());
    }

    @Override
    protected boolean consumeShotResources(TurretBaseBlockEntity base) {
        this.lastFiredItem = base.getFirstMatchingAmmoStack(stack -> !stack.isEmpty());
        return base.consumeAnyAmmoItem(this.getAmmoPerShot(), this.getEnergyPerShot());
    }

    @Override
    protected void spawnShotFeedback(Level level, BlockPos pos, LivingEntity target) {
        super.spawnShotFeedback(level, pos, target);
        if (level instanceof ServerLevel serverLevel && !this.lastFiredItem.isEmpty()) {
            Vec3 start = this.getShotStart(pos, target);
            Vec3 impact = this.getTargetAimPoint(target);
            ItemParticleOption itemParticle = new ItemParticleOption(ParticleTypes.ITEM, this.lastFiredItem.copy());
            serverLevel.sendParticles(itemParticle, start.x, start.y, start.z, 5, 0.04D, 0.04D, 0.04D, 0.0D);
            serverLevel.sendParticles(itemParticle, impact.x, impact.y, impact.z, 6, 0.08D, 0.08D, 0.08D, 0.0D);
        }
    }
}
