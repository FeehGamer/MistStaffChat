package me.feehgamer.msc.bukkit.utils;

import de.leonhard.storage.Json;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.feehgamer.msc.bukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MSCExpansion extends PlaceholderExpansion {
    private static Json messages = new Json("messages", "plugins/MistStaffChat");
    @Override
    public @NotNull String getIdentifier() {
        return "msc";
    }
    //plugin to take plugin.yml data from
    private JavaPlugin plugin;

    public MSCExpansion(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }
    @Override
    public @NotNull String getAuthor() {
        return "FeehGamer";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.6r";
    }
    public boolean checkStaff(UUID playerUUID){
        return Main.getInstance().toggle.contains(playerUUID);
    }
    @Override
    public String onPlaceholderRequest(Player player, String identifier){
        if(player == null) return "";
        if(identifier.startsWith("staff_enabled")){
            if(checkStaff(player.getUniqueId())){
                return messages.getString("placeholder.on");
            } else {
                return messages.getString("placeholder.off");
            }
        }
        return "";
    }
}
