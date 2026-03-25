package com.ommods.openmodularturrets.content.block.turrets;

import com.ommods.openmodularturrets.OMTBlockEntities;
import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockEntity;
import com.ommods.openmodularturrets.content.item.ammo.AmmoType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class PotatoCannonTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent POTATO_FIRE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "potato"));

    public PotatoCannonTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.POTATO_CANNON_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 11 : 8 + base.getTier() * 3;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.POTATO;
    }

    @Override
    protected int getEnergyPerShot() {
        return 10;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 16;
    }

    @Override
    protected float getShotDamage() {
        return 3.0F;
    }

    @Override
    protected SoundEvent getFireSound() {
        return POTATO_FIRE_SOUND;
    }

    @Override
    protected boolean canConsumeShotResources(TurretBaseBlockEntity base) {
        return base.canConsumeSpecificItem(Items.POTATO, this.getAmmoPerShot(), this.getEnergyPerShot());
    }

    @Override
    protected boolean consumeShotResources(TurretBaseBlockEntity base) {
        return base.consumeSpecificItem(Items.POTATO, this.getAmmoPerShot(), this.getEnergyPerShot());
    }

    @Override
    protected void spawnShotFeedback(Level level, BlockPos pos, LivingEntity target) {
        super.spawnShotFeedback(level, pos, target);
        if (level instanceof ServerLevel serverLevel) {
            ItemParticleOption potatoParticle = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.POTATO));
            Vec3 start = this.getShotStart(pos, target);
            Vec3 impact = this.getTargetAimPoint(target);
            serverLevel.sendParticles(potatoParticle, start.x, start.y, start.z, 4, 0.04D, 0.04D, 0.04D, 0.0D);
            serverLevel.sendParticles(potatoParticle, impact.x, impact.y, impact.z, 6, 0.07D, 0.07D, 0.07D, 0.0D);
        }
    }
}
