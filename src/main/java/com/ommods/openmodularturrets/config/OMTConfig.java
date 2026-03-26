package com.ommods.openmodularturrets.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class OMTConfig {
    public static final ModConfigSpec SERVER_SPEC;
    public static final ModConfigSpec COMMON_SPEC;
    public static final ServerConfig SERVER;
    public static final CommonConfig COMMON;

    private OMTConfig() {
    }

    public static class Permission {
        public static boolean canOPAccessOwnedBlocks() {
            return SERVER.canOPAccessOwnedBlocks.get();
        }

        public static boolean offlineModeSupport() {
            return SERVER.offlineModeSupport.get();
        }
    }

    public static class Debug {
        public static boolean doDebugChat() {
            return COMMON.doDebugChat.get();
        }

        public static boolean debugLogging() {
            return COMMON.debugLogging.get();
        }
    }
    
    public static class TurretBase {
        public static int baseTier1MaxCharge() { return SERVER.baseTier1MaxCharge.get(); }
        public static int baseTier1MaxTransferRate() { return SERVER.baseTier1MaxTransferRate.get(); }
        public static int baseTier1BlastResistance() { return SERVER.baseTier1BlastResistance.get(); }
        public static int baseTier1DestroySpeed() { return SERVER.baseTier1DestroySpeed.get(); }
        public static int baseTier2MaxCharge() { return SERVER.baseTier2MaxCharge.get(); }
        public static int baseTier2MaxTransferRate() { return SERVER.baseTier2MaxTransferRate.get(); }
        public static int baseTier2BlastResistance() { return SERVER.baseTier2BlastResistance.get(); }
        public static int baseTier2DestroySpeed() { return SERVER.baseTier2DestroySpeed.get(); }
        public static int baseTier3MaxCharge() { return SERVER.baseTier3MaxCharge.get(); }
        public static int baseTier3MaxTransferRate() { return SERVER.baseTier3MaxTransferRate.get(); }
        public static int baseTier3BlastResistance() { return SERVER.baseTier3BlastResistance.get(); }
        public static int baseTier3DestroySpeed() { return SERVER.baseTier3DestroySpeed.get(); }
        public static int baseTier4MaxCharge() { return SERVER.baseTier4MaxCharge.get(); }
        public static int baseTier4MaxTransferRate() { return SERVER.baseTier4MaxTransferRate.get(); }
        public static int baseTier4BlastResistance() { return SERVER.baseTier4BlastResistance.get(); }
        public static int baseTier4DestroySpeed() { return SERVER.baseTier4DestroySpeed.get(); }
        public static int baseTier5MaxCharge() { return SERVER.baseTier5MaxCharge.get(); }
        public static int baseTier5MaxTransferRate() { return SERVER.baseTier5MaxTransferRate.get(); }
        public static int baseTier5BlastResistance() { return SERVER.baseTier5BlastResistance.get(); }
        public static int baseTier5DestroySpeed() { return SERVER.baseTier5DestroySpeed.get(); }
    }


    static {
        Pair<ServerConfig, ModConfigSpec> serverPair = new ModConfigSpec.Builder()
                .configure(ServerConfig::new);
        Pair<CommonConfig, ModConfigSpec> commonPair = new ModConfigSpec.Builder()
                .configure(CommonConfig::new);

        SERVER = serverPair.getLeft();
        COMMON = commonPair.getLeft();
        SERVER_SPEC = serverPair.getRight();
        COMMON_SPEC = commonPair.getRight();
    }
}
