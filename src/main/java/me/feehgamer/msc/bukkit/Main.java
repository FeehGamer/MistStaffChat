package me.feehgamer.msc.bukkit;

import com.songoda.core.SongodaPlugin;
import com.songoda.core.commands.CommandManager;
import com.songoda.core.configuration.Config;
import com.songoda.core.gui.GuiManager;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;
import me.feehgamer.msc.bukkit.commands.EditorCommand;
import me.feehgamer.msc.bukkit.commands.StaffchatCommand;
import me.feehgamer.msc.bukkit.commands.subcommands.editor.EditorFormat;
import me.feehgamer.msc.bukkit.commands.subcommands.editor.EditorSymbol;
import me.feehgamer.msc.bukkit.listeners.ChatListener;
import me.feehgamer.msc.bukkit.utils.MSCExpansion;
import me.feehgamer.msc.bukkit.utils.UpdateChecker;
import me.feehgamer.msc.shared.PlatformEnum;
import me.feehgamer.msc.shared.Settings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;


public class Main extends SongodaPlugin {
    public ArrayList<UUID> toggle = new ArrayList<UUID>();
    private final GuiManager guiManager = new GuiManager(this);
    private static Main INSTANCE;
    private CommandManager commandManager;

    @Override
    public void onPluginLoad() {
        INSTANCE = this;
    }

    @Override
    public void onPluginEnable(){
        (new UpdateChecker(Main.getInstance(), 95323)).getLatestVersion(version -> {
            if (getDescription().getVersion().equalsIgnoreCase(version)) {
                getLogger().info("is up to date!");
            } else {
                log(Level.INFO, "§chas a new update!", String.format("§cNew version is §7%s§c", version), String.format("§cYou're running §7%s", getDescription().getVersion()));
            }
        });
        this.commandManager = new CommandManager(this);
        loadEverything();
        Settings.setupConfig(PlatformEnum.SPIGOT);
    }
    @Override
    public void onPluginDisable(){

    }

    @Override
    public void onDataLoad() {

    }
    @Override
    public void onConfigReload() {

    }

    @Override
    public List<Config> getExtraConfig() {
        return null;
    }

    public void log(Level lvl, String... messages){
        for(String message : messages){
            getLogger().log(lvl, message);
        }
    }
    public boolean loadPlaceholders(){
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            (new MSCExpansion(this)).register();
            getLogger().log(Level.INFO, "PlaceholderAPI hooked.");
            return true;
        }
        return false;
    }
    public void loadCommands(){
        commandManager.addCommand(new EditorCommand(this))
        .addSubCommand(new EditorSymbol(this))
        .addSubCommand(new EditorFormat(this));
        commandManager.addCommand(new StaffchatCommand(this));
    }
    public void loadEverything(){
        loadCommands();
        loadListeners();
        if (!loadPlaceholders())
            getLogger().log(Level.WARNING, "PlaceholderAPI not found, disabling placeholders!");
    }
    public void loadListeners(){
        new ChatListener(this);
    }
    public static Main getInstance(){
        return INSTANCE;
    }
    public GuiManager getGuiManager() {
        return guiManager;
    }
}
