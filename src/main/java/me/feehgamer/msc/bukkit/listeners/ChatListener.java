package me.feehgamer.msc.bukkit.listeners;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import me.feehgamer.msc.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private Main plugin;
    public ChatListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Json messages = new Json("messages", "plugins/MistStaffChat");
        Yaml config = new Yaml("config", "plugins/MistStaffChat");
        if ((Main.getInstance().toggle.contains(e.getPlayer().getUniqueId()))) {
            e.setCancelled(true);
            for (Player online: Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("miststaffchat.use")) {
                    online.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("staffchat.format").replace("{USERNAME}", e.getPlayer().getDisplayName()).replace("{MESSAGE}", e.getMessage())));
                }
            }
        }
        if((!Main.getInstance().toggle.contains(e.getPlayer().getUniqueId()) &&
                e.getMessage().startsWith(messages.getString("staffchat.symbol")) &&
                config.getBoolean("symbol.enabled") &&
                e.getMessage().substring(1).length() > 0) &&
                e.getPlayer().hasPermission("miststaffchat.use")){
            e.setCancelled(true);
            for (Player online: Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("miststaffchat.use")) {
                    online.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("staffchat.format").replace("{USERNAME}", e.getPlayer().getDisplayName()).replace("{MESSAGE}", e.getMessage().substring(1))));
                }
            }
        }
    }
}
