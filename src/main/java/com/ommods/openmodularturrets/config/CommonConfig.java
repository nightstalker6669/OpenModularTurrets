package com.ommods.openmodularturrets.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public final ForgeConfigSpec.ConfigValue<Boolean> doDebugChat;
    public final ForgeConfigSpec.ConfigValue<Boolean> debugLogging;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("DEBUG");
        this.doDebugChat = builder.comment("Should some blocks write debug messages on interaction?")
                .define("doDebugChat", false);
        this.debugLogging = builder.comment("Should OpenModular Mods print out debug messages in log? (Warning, spammy)")
                .define("debugLogging", false);
        builder.pop();
    }
}
