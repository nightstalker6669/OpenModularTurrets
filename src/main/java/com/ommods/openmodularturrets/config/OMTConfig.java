package com.ommods.openmodularturrets.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class OMTConfig {
    public static ForgeConfigSpec serverConfigSpec;
    public static ForgeConfigSpec commonConfigSpec;
    public static ServerConfig serverConfig;
    public static CommonConfig commonConfig;



    public static class General {
        public static String recipes;
    }

    public static class Permission {
        public static Boolean canOPAccessOwnedBlocks;
        public static Boolean offlineModeSupport;
    }

    public static class Debug {

        public static Boolean doDebugChat;
        public static Boolean debugLogging;

    }
    
    public static class TurretBase {
        public static int baseTier1MaxCharge;
        public static int baseTier1MaxTransferRate;
        public static int baseTier1BlastResistance;
        public static int baseTier1DestroySpeed;
        public static int baseTier2MaxCharge;
        public static int baseTier2MaxTransferRate;
        public static int baseTier2BlastResistance;
        public static int baseTier2DestroySpeed;
        public static int baseTier3MaxCharge;
        public static int baseTier3MaxTransferRate;
        public static int baseTier3BlastResistance;
        public static int baseTier3DestroySpeed;
        public static int baseTier4MaxCharge;
        public static int baseTier4MaxTransferRate;
        public static int baseTier4BlastResistance;
        public static int baseTier4DestroySpeed;
        public static int baseTier5MaxCharge;
        public static int baseTier5MaxTransferRate;
        public static int baseTier5BlastResistance;
        public static int baseTier5DestroySpeed;
    }


    static {
        Pair<ServerConfig, ForgeConfigSpec> serverPair = new ForgeConfigSpec.Builder()
                .configure(ServerConfig::new);
        Pair<CommonConfig, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder()
                .configure(CommonConfig::new);


        serverConfig = serverPair.getLeft();
        commonConfig = commonPair.getLeft();
        serverConfigSpec = serverPair.getValue();
        commonConfigSpec = commonPair.getValue();

        General.recipes = serverConfig.recipes.get();
        Permission.canOPAccessOwnedBlocks = serverConfig.canOPAccessOwnedBlocks.get();
        Permission.offlineModeSupport = serverConfig.offlineModeSupport.get();

        Debug.doDebugChat = commonConfig.doDebugChat.get();
        Debug.debugLogging = commonConfig.debugLogging.get();

        TurretBase.baseTier1MaxCharge = serverConfig.baseTier1MaxCharge.get();
        TurretBase.baseTier1MaxTransferRate = serverConfig.baseTier1MaxTransferRate.get();
        TurretBase.baseTier1BlastResistance = serverConfig.baseTier1BlastResistance.get();
        TurretBase.baseTier1DestroySpeed = serverConfig.baseTier1DestroySpeed.get();

        TurretBase.baseTier2MaxCharge = serverConfig.baseTier2MaxCharge.get();
        TurretBase.baseTier2MaxTransferRate = serverConfig.baseTier2MaxTransferRate.get();
        TurretBase.baseTier2BlastResistance = serverConfig.baseTier2BlastResistance.get();
        TurretBase.baseTier2DestroySpeed = serverConfig.baseTier2DestroySpeed.get();

        TurretBase.baseTier3MaxCharge = serverConfig.baseTier3MaxCharge.get();
        TurretBase.baseTier3MaxTransferRate = serverConfig.baseTier3MaxTransferRate.get();
        TurretBase.baseTier3BlastResistance = serverConfig.baseTier3BlastResistance.get();
        TurretBase.baseTier3DestroySpeed = serverConfig.baseTier3DestroySpeed.get();

        TurretBase.baseTier4MaxCharge = serverConfig.baseTier4MaxCharge.get();
        TurretBase.baseTier4MaxTransferRate = serverConfig.baseTier4MaxTransferRate.get();
        TurretBase.baseTier4BlastResistance = serverConfig.baseTier4BlastResistance.get();
        TurretBase.baseTier4DestroySpeed = serverConfig.baseTier4DestroySpeed.get();

        TurretBase.baseTier5MaxCharge = serverConfig.baseTier5MaxCharge.get();
        TurretBase.baseTier5MaxTransferRate = serverConfig.baseTier5MaxTransferRate.get();
        TurretBase.baseTier5BlastResistance = serverConfig.baseTier5BlastResistance.get();
        TurretBase.baseTier5DestroySpeed = serverConfig.baseTier5DestroySpeed.get();
    }
}
