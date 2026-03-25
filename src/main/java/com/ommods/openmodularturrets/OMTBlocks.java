package com.ommods.openmodularturrets;

import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockTier1;
import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockTier2;
import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockTier3;
import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockTier4;
import com.ommods.openmodularturrets.content.block.turret_bases.TurretBaseBlockTier5;
import com.ommods.openmodularturrets.content.block.turrets.MachineGunTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.LaserTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.RocketTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.DisposableItemTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.GrenadeTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.IncendiaryTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.PotatoCannonTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.RailGunTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.PlasmaTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.ArcTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.TeleporterTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.RelativisticTurretBlock;
import com.ommods.openmodularturrets.content.block.turrets.CrossbowTurretBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.ommods.openmodularturrets.reference.Constants.Blocks.TURRET_BASE_TIER_1;
import static com.ommods.openmodularturrets.reference.Constants.Blocks.TURRET_BASE_TIER_2;
import static com.ommods.openmodularturrets.reference.Constants.Blocks.TURRET_BASE_TIER_3;
import static com.ommods.openmodularturrets.reference.Constants.Blocks.TURRET_BASE_TIER_4;
import static com.ommods.openmodularturrets.reference.Constants.Blocks.TURRET_BASE_TIER_5;
import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public final class OMTBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    private static final BlockBehaviour.Properties TURRET_HEAD_PROPERTIES = BlockBehaviour.Properties.of()
        .strength(3.0F, 6.0F)
        .sound(SoundType.METAL)
        .noOcclusion();

    public static final DeferredBlock<TurretBaseBlockTier1> turretBaseTier1 = BLOCKS.registerBlock(TURRET_BASE_TIER_1, TurretBaseBlockTier1::new, TurretBaseBlockTier1.properties);
    public static final DeferredBlock<TurretBaseBlockTier2> turretBaseTier2 = BLOCKS.registerBlock(TURRET_BASE_TIER_2, TurretBaseBlockTier2::new, TurretBaseBlockTier2.properties);
    public static final DeferredBlock<TurretBaseBlockTier3> turretBaseTier3 = BLOCKS.registerBlock(TURRET_BASE_TIER_3, TurretBaseBlockTier3::new, TurretBaseBlockTier3.properties);
    public static final DeferredBlock<TurretBaseBlockTier4> turretBaseTier4 = BLOCKS.registerBlock(TURRET_BASE_TIER_4, TurretBaseBlockTier4::new, TurretBaseBlockTier4.properties);
    public static final DeferredBlock<TurretBaseBlockTier5> turretBaseTier5 = BLOCKS.registerBlock(TURRET_BASE_TIER_5, TurretBaseBlockTier5::new, TurretBaseBlockTier5.properties);
    public static final DeferredBlock<DisposableItemTurretBlock> disposableItemTurret = BLOCKS.registerBlock("disposable_item_turret", DisposableItemTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<MachineGunTurretBlock> machineGunTurret = BLOCKS.registerBlock("machine_gun_turret", MachineGunTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<LaserTurretBlock> laserTurret = BLOCKS.registerBlock("laser_turret", LaserTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<RocketTurretBlock> rocketTurret = BLOCKS.registerBlock("rocket_turret", RocketTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<GrenadeTurretBlock> grenadeTurret = BLOCKS.registerBlock("grenade_turret", GrenadeTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<RailGunTurretBlock> railGunTurret = BLOCKS.registerBlock("rail_gun_turret", RailGunTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<PlasmaTurretBlock> plasmaTurret = BLOCKS.registerBlock("plasma_turret", PlasmaTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<ArcTurretBlock> arcTurret = BLOCKS.registerBlock("arc_turret", ArcTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<TeleporterTurretBlock> teleporterTurret = BLOCKS.registerBlock("teleporter_turret", TeleporterTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<RelativisticTurretBlock> relativisticTurret = BLOCKS.registerBlock("relativistic_turret", RelativisticTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<CrossbowTurretBlock> crossbowTurret = BLOCKS.registerBlock("crossbow_turret", CrossbowTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<IncendiaryTurretBlock> incendiaryTurret = BLOCKS.registerBlock("incendiary_turret", IncendiaryTurretBlock::new, TURRET_HEAD_PROPERTIES);
    public static final DeferredBlock<PotatoCannonTurretBlock> potatoCannonTurret = BLOCKS.registerBlock("potato_cannon_turret", PotatoCannonTurretBlock::new, TURRET_HEAD_PROPERTIES);

    public static final DeferredItem<BlockItem> turretBaseTier1Item = OMTItems.registerSimpleBlockItem(turretBaseTier1, new Item.Properties());
    public static final DeferredItem<BlockItem> turretBaseTier2Item = OMTItems.registerSimpleBlockItem(turretBaseTier2, new Item.Properties());
    public static final DeferredItem<BlockItem> turretBaseTier3Item = OMTItems.registerSimpleBlockItem(turretBaseTier3, new Item.Properties());
    public static final DeferredItem<BlockItem> turretBaseTier4Item = OMTItems.registerSimpleBlockItem(turretBaseTier4, new Item.Properties());
    public static final DeferredItem<BlockItem> turretBaseTier5Item = OMTItems.registerSimpleBlockItem(turretBaseTier5, new Item.Properties());
    public static final DeferredItem<BlockItem> disposableItemTurretItem = OMTItems.registerSimpleBlockItem(disposableItemTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> machineGunTurretItem = OMTItems.registerSimpleBlockItem(machineGunTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> laserTurretItem = OMTItems.registerSimpleBlockItem(laserTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> rocketTurretItem = OMTItems.registerSimpleBlockItem(rocketTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> grenadeTurretItem = OMTItems.registerSimpleBlockItem(grenadeTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> railGunTurretItem = OMTItems.registerSimpleBlockItem(railGunTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> plasmaTurretItem = OMTItems.registerSimpleBlockItem(plasmaTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> arcTurretItem = OMTItems.registerSimpleBlockItem(arcTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> teleporterTurretItem = OMTItems.registerSimpleBlockItem(teleporterTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> relativisticTurretItem = OMTItems.registerSimpleBlockItem(relativisticTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> crossbowTurretItem = OMTItems.registerSimpleBlockItem(crossbowTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> incendiaryTurretItem = OMTItems.registerSimpleBlockItem(incendiaryTurret, new Item.Properties());
    public static final DeferredItem<BlockItem> potatoCannonTurretItem = OMTItems.registerSimpleBlockItem(potatoCannonTurret, new Item.Properties());

    private OMTBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
