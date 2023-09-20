package com.mof.fatcraft.handlers;

import java.io.File;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class Config {
    private static final String GENERIC_CATEGORY = "General Settings";

    public static int exampleInt = 512;
    public static boolean exampleBoolean = false;

    public static void load(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(new File(event.getModConfigurationDirectory(), "Fatcraft.cfg"), "1.0", true);
        config.load();

        config.addCustomCategoryComment(GENERIC_CATEGORY, "Be careful when changing these things or you suck.");

        exampleInt = config.getInt("ExampleInt", GENERIC_CATEGORY, 512, Short.MIN_VALUE, Short.MAX_VALUE,
                "Example int property");

        exampleBoolean = config.getBoolean("ExampleBoolean", GENERIC_CATEGORY, false,
                "Example boolean property");

        config.save();
    }

}
