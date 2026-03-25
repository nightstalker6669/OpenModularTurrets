package com.ommods.openmodularturrets.content.block.turrets;

import com.ommods.openmodularturrets.OMTBlockEntities;
import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockEntity;
import com.ommods.openmodularturrets.content.item.ammo.AmmoType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class MachineGunTurretBlockEntity extends DirectedTurretBlockEntity {
    private static final SoundEvent MACHINE_GUN_FIRE_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "machine_gun"));
    private static final SoundEvent BULLET_HIT_SOUND = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "bullet_hit"));

    public MachineGunTurretBlockEntity(BlockPos pos, BlockState state) {
        super(OMTBlockEntities.MACHINE_GUN_TURRET.get(), pos, state);
    }

    @Override
    protected int getTargetRange(@Nullable TurretBaseBlockEntity base) {
        return base == null ? 12 : 8 + base.getTier() * 4;
    }

    @Override
    protected AmmoType getAmmoType() {
        return AmmoType.BULLET;
    }

    @Override
    protected int getEnergyPerShot() {
        return 6;
    }

    @Override
    protected int getShotCooldownTicks() {
        return 6;
    }

    @Override
    protected SoundEvent getFireSound() {
        return MACHINE_GUN_FIRE_SOUND;
    }

    @Override
    protected SoundEvent getHitSound() {
        return BULLET_HIT_SOUND;
    }

    @Override
    protected float getShotOriginForwardOffset() {
        // The machine gun model's visible muzzle sits on the opposite side of the root
        // from the generic directed-turret centerline, so invert the forward offset.
        return -0.7F;
    }

    @Override
    protected float getShotDamage() {
        return 3.0F;
    }
}
