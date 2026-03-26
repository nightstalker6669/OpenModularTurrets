package com.ommods.openmodularturrets.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public final ModConfigSpec.BooleanValue canTurretsAttackMobs;
    public final ModConfigSpec.BooleanValue canTurretsAttackPlayers;
    public final ModConfigSpec.BooleanValue canTurretsAttackNeutralMobs;
    public final ModConfigSpec.BooleanValue canOPAccessOwnedBlocks;
    public final ModConfigSpec.BooleanValue offlineModeSupport;

    public final ModConfigSpec.IntValue baseTier1MaxCharge;
    public final ModConfigSpec.IntValue baseTier1MaxTransferRate;
    public final ModConfigSpec.IntValue baseTier1BlastResistance;
    public final ModConfigSpec.IntValue baseTier1DestroySpeed;
    public final ModConfigSpec.IntValue baseTier2MaxCharge;
    public final ModConfigSpec.IntValue baseTier2MaxTransferRate;
    public final ModConfigSpec.IntValue baseTier2BlastResistance;
    public final ModConfigSpec.IntValue baseTier2DestroySpeed;
    public final ModConfigSpec.IntValue baseTier3MaxCharge;
    public final ModConfigSpec.IntValue baseTier3MaxTransferRate;
    public final ModConfigSpec.IntValue baseTier3BlastResistance;
    public final ModConfigSpec.IntValue baseTier3DestroySpeed;
    public final ModConfigSpec.IntValue baseTier4MaxCharge;
    public final ModConfigSpec.IntValue baseTier4MaxTransferRate;
    public final ModConfigSpec.IntValue baseTier4BlastResistance;
    public final ModConfigSpec.IntValue baseTier4DestroySpeed;
    public final ModConfigSpec.IntValue baseTier5MaxCharge;
    public final ModConfigSpec.IntValue baseTier5MaxTransferRate;
    public final ModConfigSpec.IntValue baseTier5BlastResistance;
    public final ModConfigSpec.IntValue baseTier5DestroySpeed;

    public final TurretSettings gunTurretSettings;
    public final TurretSettings laserTurretSettings;
    public final TurretSettings rocketSmallTurretSettings;
    public final TurretSettings rocketLargeTurretSettings;
    public final TurretSettings flameTurretSettings;
    public final TurretSettings grenadeLauncherTurretSettings;
    public final TurretSettings railgunTurretSettings;
    public final TurretSettings disposableTurretSettings;
    public final TurretSettings meleeTurretSettings;
    public final TurretSettings crossbowTurretSettings;
    public final TurretSettings artilleryTurretSettings;
    public final TurretSettings plasmaLauncherTurretSettings;
    public final TurretSettings teleporterTurretSettings;
    public final TurretSettings relativisticTurretSettings;

    public ServerConfig(ModConfigSpec.Builder builder) {
        builder.push("GLOBAL_TARGETING");
        this.canTurretsAttackMobs = builder.comment("Can turrets attack mobs?")
                .define("canTurretsAttackMobs", true);
        this.canTurretsAttackPlayers = builder.comment("Can turrets attack players?")
                .define("canTurretsAttackPlayers", true);
        this.canTurretsAttackNeutralMobs = builder.comment("Can turrets attack neutral passives?")
                .define("canTurretsAttackPassives", true);
        builder.pop();

        builder.push("GENERAL");
        this.canOPAccessOwnedBlocks = builder.comment("Can operators access owned turret bases?")
                .define("canOPAccessOwnedBlocks", true);
        this.offlineModeSupport = builder.comment("Enable offline-mode ownership support?")
                .define("offlineModeSupport", false);
        builder.pop();

        builder.push("BASE_SETTINGS");
        this.baseTier1MaxCharge = builder.comment("Base max charge for tier 1 turrets")
                .defineInRange("baseTier1MaxCharge", 500, 0, Integer.MAX_VALUE);
        this.baseTier1MaxTransferRate = builder.comment("Base max transfer rate for tier 1 turrets")
                .defineInRange("baseTier1MaxTransferRate", 500, 0, Integer.MAX_VALUE);
        this.baseTier1BlastResistance = builder.comment("Base blast resistance for tier 1 turrets")
                .defineInRange("baseTier1BlastResistance", 10, 0, Integer.MAX_VALUE);
        this.baseTier1DestroySpeed = builder.comment("Base destroy speed for tier 1 turrets")
                .defineInRange("baseTier1DestroySpeed", 10, 0, Integer.MAX_VALUE);

        this.baseTier2MaxCharge = builder.comment("Base max charge for tier 2 turrets")
                .defineInRange("baseTier2MaxCharge", 50_000, 0, Integer.MAX_VALUE);
        this.baseTier2MaxTransferRate = builder.comment("Base max transfer rate for tier 2 turrets")
                .defineInRange("baseTier2MaxTransferRate", 5_000, 0, Integer.MAX_VALUE);
        this.baseTier2BlastResistance = builder.comment("Base blast resistance for tier 2 turrets")
                .defineInRange("baseTier2BlastResistance", 20, 0, Integer.MAX_VALUE);
        this.baseTier2DestroySpeed = builder.comment("Base destroy speed for tier 2 turrets")
                .defineInRange("baseTier2DestroySpeed", 20, 0, Integer.MAX_VALUE);

        this.baseTier3MaxCharge = builder.comment("Base max charge for tier 3 turrets")
                .defineInRange("baseTier3MaxCharge", 150_000, 0, Integer.MAX_VALUE);
        this.baseTier3MaxTransferRate = builder.comment("Base max transfer rate for tier 3 turrets")
                .defineInRange("baseTier3MaxTransferRate", 15_000, 0, Integer.MAX_VALUE);
        this.baseTier3BlastResistance = builder.comment("Base blast resistance for tier 3 turrets")
                .defineInRange("baseTier3BlastResistance", 30, 0, Integer.MAX_VALUE);
        this.baseTier3DestroySpeed = builder.comment("Base destroy speed for tier 3 turrets")
                .defineInRange("baseTier3DestroySpeed", 30, 0, Integer.MAX_VALUE);

        this.baseTier4MaxCharge = builder.comment("Base max charge for tier 4 turrets")
                .defineInRange("baseTier4MaxCharge", 500_000, 0, Integer.MAX_VALUE);
        this.baseTier4MaxTransferRate = builder.comment("Base max transfer rate for tier 4 turrets")
                .defineInRange("baseTier4MaxTransferRate", 50_000, 0, Integer.MAX_VALUE);
        this.baseTier4BlastResistance = builder.comment("Base blast resistance for tier 4 turrets")
                .defineInRange("baseTier4BlastResistance", 40, 0, Integer.MAX_VALUE);
        this.baseTier4DestroySpeed = builder.comment("Base destroy speed for tier 4 turrets")
                .defineInRange("baseTier4DestroySpeed", 40, 0, Integer.MAX_VALUE);

        this.baseTier5MaxCharge = builder.comment("Base max charge for tier 5 turrets")
                .defineInRange("baseTier5MaxCharge", 1_000_000, 0, Integer.MAX_VALUE);
        this.baseTier5MaxTransferRate = builder.comment("Base max transfer rate for tier 5 turrets")
                .defineInRange("baseTier5MaxTransferRate", 100_000, 0, Integer.MAX_VALUE);
        this.baseTier5BlastResistance = builder.comment("Base blast resistance for tier 5 turrets")
                .defineInRange("baseTier5BlastResistance", 50, 0, Integer.MAX_VALUE);
        this.baseTier5DestroySpeed = builder.comment("Base destroy speed for tier 5 turrets")
                .defineInRange("baseTier5DestroySpeed", 50, 0, Integer.MAX_VALUE);
        builder.pop();

        builder.push("TURRET_SETTINGS");
        this.gunTurretSettings = new TurretSettings(builder, "gunTurret", 12, 16, 5.0D, 4.0D, 0.04D);
        this.laserTurretSettings = new TurretSettings(builder, "laserTurret", 40, 20, 2.0D, 8.0D, 0.08D);
        this.rocketSmallTurretSettings = new TurretSettings(builder, "rocketTurret", 80, 24, 0.8D, 12.0D, 0.10D);
        this.rocketLargeTurretSettings = new TurretSettings(builder, "rocketLargeTurret", 120, 28, 0.5D, 16.0D, 0.12D);
        this.flameTurretSettings = new TurretSettings(builder, "flameTurret", 24, 10, 6.0D, 3.0D, 0.03D);
        this.grenadeLauncherTurretSettings = new TurretSettings(builder, "grenadeTurret", 70, 20, 1.0D, 10.0D, 0.08D);
        this.railgunTurretSettings = new TurretSettings(builder, "railgunTurret", 140, 28, 0.5D, 18.0D, 0.14D);
        this.disposableTurretSettings = new TurretSettings(builder, "disposableTurret", 8, 12, 3.0D, 2.0D, 0.02D);
        this.meleeTurretSettings = new TurretSettings(builder, "meleeTurret", 10, 4, 2.0D, 6.0D, 0.02D);
        this.crossbowTurretSettings = new TurretSettings(builder, "crossbowTurret", 18, 16, 2.5D, 5.0D, 0.05D);
        this.artilleryTurretSettings = new TurretSettings(builder, "artilleryTurret", 160, 32, 0.3D, 24.0D, 0.16D);
        this.plasmaLauncherTurretSettings = new TurretSettings(builder, "plasmaTurret", 120, 24, 1.0D, 14.0D, 0.10D);
        this.teleporterTurretSettings = new TurretSettings(builder, "teleporterTurret", 120, 18, 0.75D);
        this.relativisticTurretSettings = new TurretSettings(builder, "relativisticTurret", 220, 32, 0.2D, 30.0D, 0.20D);
        builder.pop();
    }
}

