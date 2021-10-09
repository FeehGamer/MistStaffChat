package me.feehgamer.msc.shared;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;


public class Settings {
    private static Json messages = new Json("messages", "plugins/MistStaffChat");

    private static Yaml conf = new Yaml("config", "plugins/MistStaffChat");

    public static void setupConfig(PlatformEnum ee){
        messages.setPathPrefix("staffchat");
        messages.setDefault("on", "&8[&e&lSTAFF&8] &7Staffchat successfully turned &aon&7!");
        messages.setDefault("off", "&8[&e&lSTAFF&8] &7Staffchat successfully turned &coff&7!");
        messages.setDefault("symbol", "#");
        if(ee == PlatformEnum.BUNGEE){
            messages.setDefault("format", "&8[&e&lSTAFF&8] &e{USERNAME} &8[&e{SERVER}&8] &7&l>> &7{MESSAGE}");
        }
        if(ee == PlatformEnum.SPIGOT){
            messages.setDefault("format", "&8[&e&lSTAFF&8] &e{USERNAME} &7&l>> &7{MESSAGE}");
        }
        messages.setPathPrefix("errors");
        messages.setDefault("console", "&cOnly player can use this command.");
        messages.setDefault("permission", "&cYou don't have permission to do that.");
        messages.setDefault("empty", "&cThe message can't be empty!");
        messages.setPathPrefix("success");
        messages.setDefault("format", "&aSuccessfully set new format!");
        messages.setDefault("symbol", "&aSuccessfully set symbol to {SYMBOL}!");
        if(ee == PlatformEnum.SPIGOT){
            Json gui = new Json("gui", "plugins/MistStaffChat");
            gui.setDefault("title", "StaffChat Editor");
            gui.setPathPrefix("symbol");
            gui.setDefault("prompt", "Enter a new Symbol");
            gui.setDefault("button", "Change Symbol");
            gui.setDefault("success", "&aSuccessfully set new symbol!");
            gui.setPathPrefix("format");
            gui.setDefault("prompt", "Enter a new Format");
            gui.setDefault("button", "Change Format");
            gui.setDefault("success", "&aSuccessfully set new format!");
            messages.setPathPrefix("placeholder");
            messages.setDefault("on", "&aYes");
            messages.setDefault("off", "&cNo");
        }
        conf.setDefault("chatPrefix.enabled", true);
    }

}
