package me.feehgamer.msc.bungee;

import me.feehgamer.msc.bungee.listeners.events.ChatListener;
import me.feehgamer.msc.bungee.utils.UpdateChecker;
import me.feehgamer.msc.bungee.commands.EditorCommand;
import me.feehgamer.msc.bungee.commands.StaffchatCommand;
import me.feehgamer.msc.shared.PlatformEnum;
import me.feehgamer.msc.shared.Settings;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends Plugin {
    public static ArrayList<UUID> toggle = new ArrayList<UUID>();
    private static Main INSTANCE;
    @Override
    public void onLoad(){
        INSTANCE = this;
    }
    @Override
    public void onEnable() {
        Settings.setupConfig(PlatformEnum.BUNGEE);
        getProxy().getPluginManager().registerListener(this, new ChatListener());
        new StaffchatCommand(this);
        new EditorCommand(this);
        (new UpdateChecker(Main.getInstance(), 95323)).getLatestVersion(version -> {
            if (getDescription().getVersion().equalsIgnoreCase(version)) {
                getLogger().info("is up to date!");
            } else {
                log(Level.INFO, "§chas a new update!", String.format("§cNew version is §7%s§c", version), String.format("§cYou're running §7%s", getDescription().getVersion()));
            }
        });
    }
    @Override
    public void onDisable(){

    }
    public void log(Level lvl, String... messages){
        for(String message : messages){
            getLogger().log(lvl, message);
        }
    }
    public static Main getInstance() {
        return INSTANCE;
    }
}
