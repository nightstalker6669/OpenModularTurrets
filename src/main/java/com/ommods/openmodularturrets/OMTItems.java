package com.ommods.openmodularturrets;

import com.ommods.openmodularturrets.content.item.ammo.*;
import com.ommods.openmodularturrets.content.item.upgrades.GenericUpgradeItem;
import com.ommods.openmodularturrets.content.item.upgrades.UpgradeEffect;
import com.ommods.openmodularturrets.content.item.upgrades.UpgradeSpeedItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public class OMTItems {
    static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<BulletItem> ammoItemBullet = ITEMS.register(BulletItem.NAME, () -> new BulletItem(new Item.Properties()));
    public static final DeferredItem<RocketSmallItem> ammoItemRocketSmall = ITEMS.register(RocketSmallItem.NAME, () -> new RocketSmallItem(new Item.Properties()));
    public static final DeferredItem<RocketLargeItem> ammoItemRocketLarge = ITEMS.register(RocketLargeItem.NAME, () -> new RocketLargeItem(new Item.Properties()));
    public static final DeferredItem<RailgunSlugItem> ammoItemRailgunSlug = ITEMS.register(RailgunSlugItem.NAME, () -> new RailgunSlugItem(new Item.Properties()));
    public static final DeferredItem<CrossbowBoltItem> ammoItemCrossbowBolt = ITEMS.register(CrossbowBoltItem.NAME, () -> new CrossbowBoltItem(new Item.Properties()));
    public static final DeferredItem<FireChargeItem> ammoFireCharge = ITEMS.register(FireChargeItem.NAME, () -> new FireChargeItem(new Item.Properties()));
    public static final DeferredItem<GrenadeItem> ammoItemGrenade = ITEMS.register(GrenadeItem.NAME, () -> new GrenadeItem(new Item.Properties()));
    public static final DeferredItem<ArtilleryShellItem> ammoItemArtilleryShell = ITEMS.register(ArtilleryShellItem.NAME, () -> new ArtilleryShellItem(new Item.Properties()));
    public static final DeferredItem<Item> ammoBlazingClay = ITEMS.register("ammo_blazing_clay", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> throwableBullet = ITEMS.register("throwable_bullet", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> throwableGrenade = ITEMS.register("throwable_grenade", () -> new Item(new Item.Properties()));
    public static final DeferredItem<UpgradeSpeedItem> upgradeFireRate = ITEMS.register("upgrade_fire_rate", () -> new UpgradeSpeedItem(new Item.Properties()));
    public static final DeferredItem<GenericUpgradeItem> upgradeEfficiency = ITEMS.register("upgrade_efficiency", () -> new GenericUpgradeItem(new Item.Properties(), UpgradeEffect.AMMO_CAPACITY));
    public static final DeferredItem<GenericUpgradeItem> upgradeRange = ITEMS.register("upgrade_range", () -> new GenericUpgradeItem(new Item.Properties(), UpgradeEffect.RANGE));
    public static final DeferredItem<GenericUpgradeItem> upgradeAccuracy = ITEMS.register("upgrade_accuracy", () -> new GenericUpgradeItem(new Item.Properties(), UpgradeEffect.ACCURACY));
    public static final DeferredItem<GenericUpgradeItem> upgradeScatterShot = ITEMS.register("upgrade_scatter_shot", () -> new GenericUpgradeItem(new Item.Properties(), UpgradeEffect.SCATTER_SHOT));
    public static final DeferredItem<Item> addonRedstoneReactor = ITEMS.register("addon_redstone_reactor", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> addonDamageAmp = ITEMS.register("addon_damage_amp", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> addonSolarPanel = ITEMS.register("addon_solar_panel", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> addonPotentia = ITEMS.register("addon_potentia", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> addonSerialPort = ITEMS.register("addon_serial_port", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> addonRecycler = ITEMS.register("addon_recycler", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> addonConcealer = ITEMS.register("addon_concealer", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> addonFakeDrops = ITEMS.register("addon_fake_drops", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> sensorTierOne = ITEMS.register("sensor_tier_one", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> sensorTierTwo = ITEMS.register("sensor_tier_two", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> sensorTierThree = ITEMS.register("sensor_tier_three", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> sensorTierFour = ITEMS.register("sensor_tier_four", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> sensorTierFive = ITEMS.register("sensor_tier_five", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> chamberTierOne = ITEMS.register("chamber_tier_one", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> chamberTierTwo = ITEMS.register("chamber_tier_two", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> chamberTierThree = ITEMS.register("chamber_tier_three", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> chamberTierFour = ITEMS.register("chamber_tier_four", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> chamberTierFive = ITEMS.register("chamber_tier_five", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> barrelTierOne = ITEMS.register("barrel_tier_one", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> barrelTierTwo = ITEMS.register("barrel_tier_two", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> barrelTierThree = ITEMS.register("barrel_tier_three", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> barrelTierFour = ITEMS.register("barrel_tier_four", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> barrelTierFive = ITEMS.register("barrel_tier_five", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ioBus = ITEMS.register("io_bus", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> memoryCard = ITEMS.register("memory_card", () -> new Item(new Item.Properties()));

    static DeferredItem<BlockItem> registerSimpleBlockItem(DeferredBlock<?> block, Item.Properties properties) {
        return ITEMS.registerSimpleBlockItem(block, properties);
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
