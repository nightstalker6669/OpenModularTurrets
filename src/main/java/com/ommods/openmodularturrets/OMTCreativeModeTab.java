package com.ommods.openmodularturrets;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.ommods.openmodularturrets.reference.Reference.MOD_ID;

public final class OMTCreativeModeTab {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register(
        "openmodularturrets",
        () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .title(Component.translatable("itemGroup.openmodularturrets"))
            .icon(OMTCreativeModeTab::makeIcon)
            .displayItems((parameters, output) -> {
                output.accept(OMTBlocks.turretBaseTier1Item.get());
                output.accept(OMTBlocks.turretBaseTier2Item.get());
                output.accept(OMTBlocks.turretBaseTier3Item.get());
                output.accept(OMTBlocks.turretBaseTier4Item.get());
                output.accept(OMTBlocks.turretBaseTier5Item.get());
                output.accept(OMTBlocks.disposableItemTurretItem.get());
                output.accept(OMTBlocks.machineGunTurretItem.get());
                output.accept(OMTBlocks.laserTurretItem.get());
                output.accept(OMTBlocks.rocketTurretItem.get());
                output.accept(OMTBlocks.grenadeTurretItem.get());
                output.accept(OMTBlocks.railGunTurretItem.get());
                output.accept(OMTBlocks.plasmaTurretItem.get());
                output.accept(OMTBlocks.arcTurretItem.get());
                output.accept(OMTBlocks.teleporterTurretItem.get());
                output.accept(OMTBlocks.relativisticTurretItem.get());
                output.accept(OMTBlocks.crossbowTurretItem.get());
                output.accept(OMTBlocks.incendiaryTurretItem.get());
                output.accept(OMTBlocks.potatoCannonTurretItem.get());
                output.accept(OMTItems.upgradeFireRate.get());
                output.accept(OMTItems.upgradeEfficiency.get());
                output.accept(OMTItems.upgradeRange.get());
                output.accept(OMTItems.upgradeAccuracy.get());
                output.accept(OMTItems.upgradeScatterShot.get());
                output.accept(OMTItems.addonRedstoneReactor.get());
                output.accept(OMTItems.addonDamageAmp.get());
                output.accept(OMTItems.addonSolarPanel.get());
                output.accept(OMTItems.addonPotentia.get());
                output.accept(OMTItems.addonSerialPort.get());
                output.accept(OMTItems.addonRecycler.get());
                output.accept(OMTItems.addonConcealer.get());
                output.accept(OMTItems.addonFakeDrops.get());
                output.accept(OMTItems.sensorTierOne.get());
                output.accept(OMTItems.sensorTierTwo.get());
                output.accept(OMTItems.sensorTierThree.get());
                output.accept(OMTItems.sensorTierFour.get());
                output.accept(OMTItems.sensorTierFive.get());
                output.accept(OMTItems.chamberTierOne.get());
                output.accept(OMTItems.chamberTierTwo.get());
                output.accept(OMTItems.chamberTierThree.get());
                output.accept(OMTItems.chamberTierFour.get());
                output.accept(OMTItems.chamberTierFive.get());
                output.accept(OMTItems.barrelTierOne.get());
                output.accept(OMTItems.barrelTierTwo.get());
                output.accept(OMTItems.barrelTierThree.get());
                output.accept(OMTItems.barrelTierFour.get());
                output.accept(OMTItems.barrelTierFive.get());
                output.accept(OMTItems.ioBus.get());
                output.accept(OMTItems.memoryCard.get());
                output.accept(OMTItems.ammoItemBullet.get());
                output.accept(OMTItems.ammoBlazingClay.get());
                output.accept(OMTItems.ammoItemRocketSmall.get());
                output.accept(OMTItems.ammoItemRocketLarge.get());
                output.accept(OMTItems.ammoItemRailgunSlug.get());
                output.accept(OMTItems.ammoItemCrossbowBolt.get());
                output.accept(OMTItems.ammoFireCharge.get());
                output.accept(OMTItems.ammoItemGrenade.get());
                output.accept(OMTItems.ammoItemArtilleryShell.get());
                output.accept(OMTItems.throwableBullet.get());
                output.accept(OMTItems.throwableGrenade.get());
            })
            .build()
    );

    private OMTCreativeModeTab() {
    }

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }

    public static ItemStack makeIcon() {
        return new ItemStack(OMTItems.ammoItemBullet.get());
    }
}
