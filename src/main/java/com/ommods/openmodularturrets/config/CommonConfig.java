package com.ommods.openmodularturrets.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig {
    public final ModConfigSpec.BooleanValue doDebugChat;
    public final ModConfigSpec.BooleanValue debugLogging;

    public CommonConfig(ModConfigSpec.Builder builder) {
        builder.push("DEBUG");
        this.doDebugChat = builder.comment("Should some blocks write debug messages on interaction?")
                .define("doDebugChat", false);
        this.debugLogging = builder.comment("Should OpenModular Mods print out debug messages in log? (Warning, spammy)")
                .define("debugLogging", false);
        builder.pop();
    }
}
