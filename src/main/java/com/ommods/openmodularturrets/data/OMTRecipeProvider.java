package com.ommods.openmodularturrets.data;

import com.ommods.openmodularturrets.OMTBlocks;
import com.ommods.openmodularturrets.OMTItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public final class OMTRecipeProvider extends RecipeProvider {
    public OMTRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        buildComponents(output);
        buildAmmo(output);
        buildUpgrades(output);
        buildAddons(output);
        buildTurretBases(output);
        buildTurrets(output);
        buildUtilityItems(output);
    }

    private void buildComponents(RecipeOutput output) {
        shaped(output, RecipeCategory.REDSTONE, OMTItems.ioBus.get(), 1, Items.IRON_INGOT)
            .pattern("IRI")
            .pattern("RCR")
            .pattern("IRI")
            .define('I', Items.IRON_INGOT)
            .define('R', Items.REDSTONE)
            .define('C', Items.COPPER_INGOT)
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTItems.sensorTierOne.get(), 1, OMTItems.ioBus.get())
            .pattern(" Q ")
            .pattern("RIR")
            .pattern(" B ")
            .define('Q', Items.QUARTZ)
            .define('R', Items.REDSTONE)
            .define('I', Items.IRON_INGOT)
            .define('B', OMTItems.ioBus.get())
            .save(output);
        tierUpgrade(output, OMTItems.sensorTierTwo.get(), OMTItems.sensorTierOne.get(), Items.GOLD_INGOT, Items.COMPARATOR);
        tierUpgrade(output, OMTItems.sensorTierThree.get(), OMTItems.sensorTierTwo.get(), Items.DIAMOND, Items.REPEATER);
        tierUpgrade(output, OMTItems.sensorTierFour.get(), OMTItems.sensorTierThree.get(), Items.ENDER_EYE, Items.CLOCK);
        tierUpgrade(output, OMTItems.sensorTierFive.get(), OMTItems.sensorTierFour.get(), Items.NETHERITE_INGOT, Items.RECOVERY_COMPASS);

        shaped(output, RecipeCategory.REDSTONE, OMTItems.chamberTierOne.get(), 1, OMTItems.sensorTierOne.get())
            .pattern("III")
            .pattern("SDS")
            .pattern("IBI")
            .define('I', Items.IRON_INGOT)
            .define('S', OMTItems.sensorTierOne.get())
            .define('D', Items.DISPENSER)
            .define('B', OMTItems.ioBus.get())
            .save(output);
        tierUpgrade(output, OMTItems.chamberTierTwo.get(), OMTItems.chamberTierOne.get(), Items.GOLD_INGOT, OMTItems.sensorTierTwo.get());
        tierUpgrade(output, OMTItems.chamberTierThree.get(), OMTItems.chamberTierTwo.get(), Items.DIAMOND, OMTItems.sensorTierThree.get());
        tierUpgrade(output, OMTItems.chamberTierFour.get(), OMTItems.chamberTierThree.get(), Items.ENDER_EYE, OMTItems.sensorTierFour.get());
        tierUpgrade(output, OMTItems.chamberTierFive.get(), OMTItems.chamberTierFour.get(), Items.NETHERITE_INGOT, OMTItems.sensorTierFive.get());

        shaped(output, RecipeCategory.REDSTONE, OMTItems.barrelTierOne.get(), 1, OMTItems.chamberTierOne.get())
            .pattern("II ")
            .pattern(" CB")
            .pattern(" II")
            .define('I', Items.IRON_INGOT)
            .define('C', OMTItems.chamberTierOne.get())
            .define('B', Items.FLINT)
            .save(output);
        tierUpgrade(output, OMTItems.barrelTierTwo.get(), OMTItems.barrelTierOne.get(), Items.GOLD_INGOT, OMTItems.chamberTierTwo.get());
        tierUpgrade(output, OMTItems.barrelTierThree.get(), OMTItems.barrelTierTwo.get(), Items.DIAMOND, OMTItems.chamberTierThree.get());
        tierUpgrade(output, OMTItems.barrelTierFour.get(), OMTItems.barrelTierThree.get(), Items.ENDER_EYE, OMTItems.chamberTierFour.get());
        tierUpgrade(output, OMTItems.barrelTierFive.get(), OMTItems.barrelTierFour.get(), Items.NETHERITE_INGOT, OMTItems.chamberTierFive.get());
    }

    private void buildAmmo(RecipeOutput output) {
        shaped(output, RecipeCategory.COMBAT, OMTItems.ammoItemBullet.get(), 16, Items.GUNPOWDER)
            .pattern(" N ")
            .pattern("NGN")
            .pattern(" N ")
            .define('N', Items.IRON_NUGGET)
            .define('G', Items.GUNPOWDER)
            .save(output);

        shaped(output, RecipeCategory.COMBAT, OMTItems.ammoItemGrenade.get(), 4, Items.TNT)
            .pattern(" I ")
            .pattern("TGT")
            .pattern(" P ")
            .define('I', Items.IRON_INGOT)
            .define('T', Items.TNT)
            .define('G', Items.GUNPOWDER)
            .define('P', Items.PAPER)
            .save(output);

        shaped(output, RecipeCategory.COMBAT, OMTItems.ammoItemRocketSmall.get(), 4, Items.FIREWORK_ROCKET)
            .pattern(" P ")
            .pattern("FGF")
            .pattern(" I ")
            .define('P', Items.PAPER)
            .define('F', Items.FIREWORK_ROCKET)
            .define('G', Items.GUNPOWDER)
            .define('I', Items.IRON_NUGGET)
            .save(output);

        shaped(output, RecipeCategory.COMBAT, OMTItems.ammoItemRocketLarge.get(), 2, OMTItems.ammoItemRocketSmall.get())
            .pattern(" I ")
            .pattern("RTR")
            .pattern(" T ")
            .define('I', Items.IRON_INGOT)
            .define('R', OMTItems.ammoItemRocketSmall.get())
            .define('T', Items.TNT)
            .save(output);

        shaped(output, RecipeCategory.COMBAT, OMTItems.ammoItemRailgunSlug.get(), 8, Items.LIGHTNING_ROD)
            .pattern(" I ")
            .pattern("ICI")
            .pattern(" I ")
            .define('I', Items.IRON_INGOT)
            .define('C', Items.COPPER_INGOT)
            .save(output);

        shaped(output, RecipeCategory.COMBAT, OMTItems.ammoFireCharge.get(), 8, Items.FIRE_CHARGE)
            .pattern("BCB")
            .pattern("CRC")
            .pattern("BCB")
            .define('B', Items.CLAY_BALL)
            .define('C', Items.BLAZE_POWDER)
            .define('R', Items.REDSTONE)
            .save(output);

        shaped(output, RecipeCategory.COMBAT, OMTItems.ammoItemArtilleryShell.get(), 2, OMTItems.ammoItemRocketLarge.get())
            .pattern(" I ")
            .pattern("RTR")
            .pattern(" I ")
            .define('I', Items.IRON_INGOT)
            .define('R', OMTItems.ammoItemRocketLarge.get())
            .define('T', Items.TNT)
            .save(output);

        shaped(output, RecipeCategory.COMBAT, OMTItems.ammoBlazingClay.get(), 16, Items.BLAZE_POWDER)
            .pattern("BCB")
            .pattern("CRC")
            .pattern("BCB")
            .define('B', Items.CLAY_BALL)
            .define('C', Items.BLAZE_POWDER)
            .define('R', Items.REDSTONE)
            .save(output, "openmodularturrets:ammo_blazing_clay");

        shaped(output, RecipeCategory.COMBAT, OMTItems.ammoItemCrossbowBolt.get(), 8, Items.ARROW)
            .pattern(" N ")
            .pattern(" A ")
            .pattern(" F ")
            .define('N', Items.IRON_NUGGET)
            .define('A', Items.ARROW)
            .define('F', Items.FEATHER)
            .save(output);
    }

    private void buildUpgrades(RecipeOutput output) {
        shaped(output, RecipeCategory.MISC, OMTItems.upgradeFireRate.get(), 1, Items.QUARTZ)
            .pattern(" Q ")
            .pattern("GBG")
            .pattern(" R ")
            .define('Q', Items.QUARTZ)
            .define('G', Items.GOLD_INGOT)
            .define('B', OMTItems.ioBus.get())
            .define('R', Items.REPEATER)
            .save(output);

        shaped(output, RecipeCategory.MISC, OMTItems.upgradeEfficiency.get(), 1, Items.REDSTONE_BLOCK)
            .pattern(" Q ")
            .pattern("GBG")
            .pattern(" E ")
            .define('Q', Items.QUARTZ)
            .define('G', Items.GOLD_INGOT)
            .define('B', OMTItems.ioBus.get())
            .define('E', Items.REDSTONE_BLOCK)
            .save(output);

        shaped(output, RecipeCategory.MISC, OMTItems.upgradeRange.get(), 1, Items.SPYGLASS)
            .pattern(" G ")
            .pattern("SBS")
            .pattern(" Q ")
            .define('G', Items.GOLD_INGOT)
            .define('S', Items.SPYGLASS)
            .define('B', OMTItems.ioBus.get())
            .define('Q', Items.QUARTZ)
            .save(output);

        shaped(output, RecipeCategory.MISC, OMTItems.upgradeAccuracy.get(), 1, Items.TARGET)
            .pattern(" Q ")
            .pattern("TBT")
            .pattern(" G ")
            .define('Q', Items.QUARTZ)
            .define('T', Items.TARGET)
            .define('B', OMTItems.ioBus.get())
            .define('G', Items.GOLD_INGOT)
            .save(output);

        shaped(output, RecipeCategory.MISC, OMTItems.upgradeScatterShot.get(), 1, OMTItems.upgradeFireRate.get())
            .pattern(" P ")
            .pattern("FHF")
            .pattern(" Q ")
            .define('P', Items.PISTON)
            .define('F', OMTItems.upgradeFireRate.get())
            .define('H', Items.TRIPWIRE_HOOK)
            .define('Q', Items.QUARTZ)
            .save(output);
    }

    private void buildAddons(RecipeOutput output) {
        shaped(output, RecipeCategory.MISC, OMTItems.addonRedstoneReactor.get(), 1, Items.REDSTONE_BLOCK)
            .pattern("IRI")
            .pattern("RBR")
            .pattern("ICI")
            .define('I', Items.IRON_INGOT)
            .define('R', Items.REDSTONE_BLOCK)
            .define('B', OMTItems.ioBus.get())
            .define('C', Items.COPPER_INGOT)
            .save(output);

        shaped(output, RecipeCategory.MISC, OMTItems.addonDamageAmp.get(), 1, Items.DIAMOND)
            .pattern(" D ")
            .pattern("QBQ")
            .pattern(" S ")
            .define('D', Items.DIAMOND)
            .define('Q', Items.QUARTZ)
            .define('B', OMTItems.ioBus.get())
            .define('S', Items.IRON_SWORD)
            .save(output);

        shaped(output, RecipeCategory.MISC, OMTItems.addonSolarPanel.get(), 1, Items.DAYLIGHT_DETECTOR)
            .pattern(" G ")
            .pattern("DBD")
            .pattern(" C ")
            .define('G', Items.GLASS)
            .define('D', Items.DAYLIGHT_DETECTOR)
            .define('B', OMTItems.ioBus.get())
            .define('C', Items.COPPER_INGOT)
            .save(output);

        shaped(output, RecipeCategory.MISC, OMTItems.addonPotentia.get(), 1, Items.BLAZE_ROD)
            .pattern(" A ")
            .pattern("RBR")
            .pattern(" O ")
            .define('A', Items.AMETHYST_SHARD)
            .define('R', Items.BLAZE_POWDER)
            .define('B', OMTItems.ioBus.get())
            .define('O', Items.BLAZE_ROD)
            .save(output);

        shaped(output, RecipeCategory.MISC, OMTItems.addonSerialPort.get(), 1, Items.COMPARATOR)
            .pattern(" O ")
            .pattern("CBC")
            .pattern(" R ")
            .define('O', Items.OBSERVER)
            .define('C', Items.COMPARATOR)
            .define('B', OMTItems.ioBus.get())
            .define('R', Items.REPEATER)
            .save(output);

        shaped(output, RecipeCategory.MISC, OMTItems.addonRecycler.get(), 1, Items.HOPPER)
            .pattern(" I ")
            .pattern("HBH")
            .pattern(" R ")
            .define('I', Items.IRON_INGOT)
            .define('H', Items.HOPPER)
            .define('B', OMTItems.ioBus.get())
            .define('R', Items.REDSTONE)
            .save(output);

        shaped(output, RecipeCategory.MISC, OMTItems.addonConcealer.get(), 1, Items.TINTED_GLASS)
            .pattern(" G ")
            .pattern("TBT")
            .pattern(" S ")
            .define('G', Items.GLASS_PANE)
            .define('T', Items.TINTED_GLASS)
            .define('B', OMTItems.ioBus.get())
            .define('S', Items.SCULK_SENSOR)
            .save(output);

        shaped(output, RecipeCategory.MISC, OMTItems.addonFakeDrops.get(), 1, Items.EMERALD)
            .pattern(" E ")
            .pattern("LBL")
            .pattern(" G ")
            .define('E', Items.EMERALD)
            .define('L', Items.LEATHER)
            .define('B', OMTItems.ioBus.get())
            .define('G', Items.GOLD_INGOT)
            .save(output);
    }

    private void buildTurretBases(RecipeOutput output) {
        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.turretBaseTier1.get(), 1, OMTItems.sensorTierOne.get())
            .pattern("III")
            .pattern("SCS")
            .pattern("IBI")
            .define('I', Items.IRON_INGOT)
            .define('S', OMTItems.sensorTierOne.get())
            .define('C', OMTItems.chamberTierOne.get())
            .define('B', OMTItems.ioBus.get())
            .save(output);
        baseUpgrade(output, OMTBlocks.turretBaseTier2.get(), OMTBlocks.turretBaseTier1.get(), OMTItems.sensorTierTwo.get(), OMTItems.chamberTierTwo.get(), Items.GOLD_INGOT);
        baseUpgrade(output, OMTBlocks.turretBaseTier3.get(), OMTBlocks.turretBaseTier2.get(), OMTItems.sensorTierThree.get(), OMTItems.chamberTierThree.get(), Items.DIAMOND);
        baseUpgrade(output, OMTBlocks.turretBaseTier4.get(), OMTBlocks.turretBaseTier3.get(), OMTItems.sensorTierFour.get(), OMTItems.chamberTierFour.get(), Items.ENDER_EYE);
        baseUpgrade(output, OMTBlocks.turretBaseTier5.get(), OMTBlocks.turretBaseTier4.get(), OMTItems.sensorTierFive.get(), OMTItems.chamberTierFive.get(), Items.NETHERITE_INGOT);
    }

    private void buildTurrets(RecipeOutput output) {
        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.disposableItemTurret.get(), 1, OMTItems.barrelTierOne.get())
            .pattern(" C ")
            .pattern("BBB")
            .pattern("RPR")
            .define('C', OMTItems.chamberTierOne.get())
            .define('B', OMTItems.barrelTierOne.get())
            .define('R', Items.REDSTONE)
            .define('P', Items.COBBLESTONE)
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.machineGunTurret.get(), 1, OMTItems.barrelTierTwo.get())
            .pattern(" B ")
            .pattern("ICI")
            .pattern("RCR")
            .define('B', OMTItems.barrelTierTwo.get())
            .define('I', Items.IRON_INGOT)
            .define('C', OMTItems.chamberTierTwo.get())
            .define('R', OMTItems.ioBus.get())
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.laserTurret.get(), 1, OMTItems.sensorTierThree.get())
            .pattern(" Q ")
            .pattern("SBS")
            .pattern("RCR")
            .define('Q', Items.QUARTZ)
            .define('S', OMTItems.sensorTierThree.get())
            .define('B', OMTItems.barrelTierThree.get())
            .define('R', Items.REDSTONE)
            .define('C', OMTItems.chamberTierThree.get())
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.rocketTurret.get(), 1, OMTItems.ammoItemRocketSmall.get())
            .pattern("RDR")
            .pattern("BCB")
            .pattern(" I ")
            .define('R', OMTItems.ammoItemRocketSmall.get())
            .define('D', Items.DIAMOND)
            .define('B', OMTItems.barrelTierThree.get())
            .define('C', OMTItems.chamberTierThree.get())
            .define('I', OMTItems.ioBus.get())
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.grenadeTurret.get(), 1, OMTItems.ammoItemGrenade.get())
            .pattern(" T ")
            .pattern("BCB")
            .pattern(" I ")
            .define('T', OMTItems.ammoItemGrenade.get())
            .define('B', OMTItems.barrelTierThree.get())
            .define('C', OMTItems.chamberTierThree.get())
            .define('I', OMTItems.ioBus.get())
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.railGunTurret.get(), 1, Items.LIGHTNING_ROD)
            .pattern(" L ")
            .pattern("BCB")
            .pattern("DID")
            .define('L', Items.LIGHTNING_ROD)
            .define('B', OMTItems.barrelTierFour.get())
            .define('C', OMTItems.chamberTierFour.get())
            .define('D', Items.DIAMOND)
            .define('I', OMTItems.ioBus.get())
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.plasmaTurret.get(), 1, Items.BLAZE_ROD)
            .pattern(" B ")
            .pattern("ACA")
            .pattern("RIR")
            .define('B', Items.BLAZE_ROD)
            .define('A', Items.AMETHYST_SHARD)
            .define('C', OMTItems.chamberTierFive.get())
            .define('R', OMTItems.barrelTierFive.get())
            .define('I', OMTItems.ioBus.get())
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.arcTurret.get(), 1, Items.LIGHTNING_ROD)
            .pattern(" L ")
            .pattern("SBS")
            .pattern("RCR")
            .define('L', Items.LIGHTNING_ROD)
            .define('S', OMTItems.sensorTierFour.get())
            .define('B', OMTItems.barrelTierFour.get())
            .define('R', Items.REDSTONE_BLOCK)
            .define('C', OMTItems.chamberTierFour.get())
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.teleporterTurret.get(), 1, Items.ENDER_EYE)
            .pattern(" E ")
            .pattern("SBS")
            .pattern("ICI")
            .define('E', Items.ENDER_EYE)
            .define('S', OMTItems.sensorTierFour.get())
            .define('B', OMTItems.barrelTierThree.get())
            .define('I', OMTItems.ioBus.get())
            .define('C', OMTItems.chamberTierFour.get())
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.relativisticTurret.get(), 1, Items.NETHER_STAR)
            .pattern(" N ")
            .pattern("SBS")
            .pattern("ICI")
            .define('N', Items.NETHER_STAR)
            .define('S', OMTItems.sensorTierFive.get())
            .define('B', OMTItems.barrelTierFive.get())
            .define('I', OMTItems.ioBus.get())
            .define('C', OMTItems.chamberTierFive.get())
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.crossbowTurret.get(), 1, Items.CROSSBOW)
            .pattern(" C ")
            .pattern("BBB")
            .pattern("RIR")
            .define('C', Items.CROSSBOW)
            .define('B', OMTItems.barrelTierTwo.get())
            .define('R', Items.STRING)
            .define('I', OMTItems.ioBus.get())
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.incendiaryTurret.get(), 1, Items.FIRE_CHARGE)
            .pattern(" F ")
            .pattern("BCB")
            .pattern("RIR")
            .define('F', Items.FIRE_CHARGE)
            .define('B', OMTItems.barrelTierTwo.get())
            .define('C', OMTItems.chamberTierTwo.get())
            .define('R', Items.BLAZE_ROD)
            .define('I', OMTItems.ioBus.get())
            .save(output);

        shaped(output, RecipeCategory.REDSTONE, OMTBlocks.potatoCannonTurret.get(), 1, Items.POTATO)
            .pattern(" P ")
            .pattern("BCB")
            .pattern("RIR")
            .define('P', Items.POTATO)
            .define('B', OMTItems.barrelTierOne.get())
            .define('C', OMTItems.chamberTierOne.get())
            .define('R', Items.REDSTONE)
            .define('I', OMTItems.ioBus.get())
            .save(output);
    }

    private void buildUtilityItems(RecipeOutput output) {
        shaped(output, RecipeCategory.TOOLS, OMTItems.memoryCard.get(), 1, Items.PAPER)
            .pattern("RLP")
            .pattern("PBP")
            .pattern("PRP")
            .define('R', Items.REDSTONE)
            .define('L', Items.LAPIS_LAZULI)
            .define('P', Items.PAPER)
            .define('B', OMTItems.ioBus.get())
            .save(output);

        shapeless(output, RecipeCategory.COMBAT, OMTItems.throwableBullet.get(), 4, OMTItems.ammoItemBullet.get())
            .requires(OMTItems.ammoItemBullet.get())
            .requires(Items.SLIME_BALL)
            .save(output);

        shapeless(output, RecipeCategory.COMBAT, OMTItems.throwableGrenade.get(), 2, OMTItems.ammoItemGrenade.get())
            .requires(OMTItems.ammoItemGrenade.get())
            .requires(Items.SLIME_BALL)
            .save(output);
    }

    private void tierUpgrade(RecipeOutput output, ItemLike result, ItemLike previousTier, ItemLike shellMaterial, ItemLike controlItem) {
        shaped(output, RecipeCategory.MISC, result, 1, previousTier)
            .pattern("MMM")
            .pattern("PCP")
            .pattern("MMM")
            .define('M', shellMaterial)
            .define('P', previousTier)
            .define('C', controlItem)
            .save(output);
    }

    private void baseUpgrade(RecipeOutput output, ItemLike result, ItemLike previousTier, ItemLike sensor, ItemLike chamber, ItemLike shellMaterial) {
        shaped(output, RecipeCategory.REDSTONE, result, 1, previousTier)
            .pattern("MSM")
            .pattern("PBP")
            .pattern("MCM")
            .define('M', shellMaterial)
            .define('S', sensor)
            .define('P', previousTier)
            .define('B', OMTItems.ioBus.get())
            .define('C', chamber)
            .save(output);
    }

    private ShapedRecipeBuilder shaped(RecipeOutput output, RecipeCategory category, ItemLike result, int count, ItemLike unlockItem) {
        return ShapedRecipeBuilder.shaped(category, result, count)
            .unlockedBy(getHasName(unlockItem), has(unlockItem));
    }

    private ShapelessRecipeBuilder shapeless(RecipeOutput output, RecipeCategory category, ItemLike result, int count, ItemLike unlockItem) {
        return ShapelessRecipeBuilder.shapeless(category, result, count)
            .unlockedBy(getHasName(unlockItem), has(unlockItem));
    }
}
