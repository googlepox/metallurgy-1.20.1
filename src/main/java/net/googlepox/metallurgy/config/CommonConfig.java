package net.googlepox.metallurgy.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Example Mod Client Configs");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}