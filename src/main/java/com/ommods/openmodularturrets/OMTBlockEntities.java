package com.ommods.openmodularturrets;

import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.MachineGunTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.LaserTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.RocketTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.DisposableItemTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.GrenadeTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.IncendiaryTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.PotatoCannonTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.RailGunTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.PlasmaTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.ArcTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.TeleporterTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.RelativisticTurretBlockEntity;
import com.ommods.openmodularturrets.content.block.turrets.CrossbowTurretBlockEntity;
import com.ommods.openmodularturrets.util.BlockEntityTypeFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public final class OMTBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TurretBaseBlockEntity>> TURRET_BASE = BLOCK_ENTITIES.register(
        "turret_base",
        () -> BlockEntityTypeFactory.create(
            TurretBaseBlockEntity::new,
            OMTBlocks.turretBaseTier1.get(),
            OMTBlocks.turretBaseTier2.get(),
            OMTBlocks.turretBaseTier3.get(),
            OMTBlocks.turretBaseTier4.get(),
            OMTBlocks.turretBaseTier5.get()
        )
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MachineGunTurretBlockEntity>> MACHINE_GUN_TURRET = BLOCK_ENTITIES.register(
        "machine_gun_turret",
        () -> BlockEntityTypeFactory.create(MachineGunTurretBlockEntity::new, OMTBlocks.machineGunTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LaserTurretBlockEntity>> LASER_TURRET = BLOCK_ENTITIES.register(
        "laser_turret",
        () -> BlockEntityTypeFactory.create(LaserTurretBlockEntity::new, OMTBlocks.laserTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RocketTurretBlockEntity>> ROCKET_TURRET = BLOCK_ENTITIES.register(
        "rocket_turret",
        () -> BlockEntityTypeFactory.create(RocketTurretBlockEntity::new, OMTBlocks.rocketTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DisposableItemTurretBlockEntity>> DISPOSABLE_ITEM_TURRET = BLOCK_ENTITIES.register(
        "disposable_item_turret",
        () -> BlockEntityTypeFactory.create(DisposableItemTurretBlockEntity::new, OMTBlocks.disposableItemTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrenadeTurretBlockEntity>> GRENADE_TURRET = BLOCK_ENTITIES.register(
        "grenade_turret",
        () -> BlockEntityTypeFactory.create(GrenadeTurretBlockEntity::new, OMTBlocks.grenadeTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IncendiaryTurretBlockEntity>> INCENDIARY_TURRET = BLOCK_ENTITIES.register(
        "incendiary_turret",
        () -> BlockEntityTypeFactory.create(IncendiaryTurretBlockEntity::new, OMTBlocks.incendiaryTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PotatoCannonTurretBlockEntity>> POTATO_CANNON_TURRET = BLOCK_ENTITIES.register(
        "potato_cannon_turret",
        () -> BlockEntityTypeFactory.create(PotatoCannonTurretBlockEntity::new, OMTBlocks.potatoCannonTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RailGunTurretBlockEntity>> RAIL_GUN_TURRET = BLOCK_ENTITIES.register(
        "rail_gun_turret",
        () -> BlockEntityTypeFactory.create(RailGunTurretBlockEntity::new, OMTBlocks.railGunTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PlasmaTurretBlockEntity>> PLASMA_TURRET = BLOCK_ENTITIES.register(
        "plasma_turret",
        () -> BlockEntityTypeFactory.create(PlasmaTurretBlockEntity::new, OMTBlocks.plasmaTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ArcTurretBlockEntity>> ARC_TURRET = BLOCK_ENTITIES.register(
        "arc_turret",
        () -> BlockEntityTypeFactory.create(ArcTurretBlockEntity::new, OMTBlocks.arcTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TeleporterTurretBlockEntity>> TELEPORTER_TURRET = BLOCK_ENTITIES.register(
        "teleporter_turret",
        () -> BlockEntityTypeFactory.create(TeleporterTurretBlockEntity::new, OMTBlocks.teleporterTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RelativisticTurretBlockEntity>> RELATIVISTIC_TURRET = BLOCK_ENTITIES.register(
        "relativistic_turret",
        () -> BlockEntityTypeFactory.create(RelativisticTurretBlockEntity::new, OMTBlocks.relativisticTurret.get())
    );
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrossbowTurretBlockEntity>> CROSSBOW_TURRET = BLOCK_ENTITIES.register(
        "crossbow_turret",
        () -> BlockEntityTypeFactory.create(CrossbowTurretBlockEntity::new, OMTBlocks.crossbowTurret.get())
    );

    private OMTBlockEntities() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
