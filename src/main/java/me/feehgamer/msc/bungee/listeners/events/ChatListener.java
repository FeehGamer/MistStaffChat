package me.feehgamer.msc.bungee.listeners.events;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import me.feehgamer.msc.bungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(ChatEvent e){
        if (e.getMessage().startsWith("/"))
            return;
        if (!(e.getSender() instanceof ProxiedPlayer))
            return;
        Json messages = new Json("messages", "plugins/MistStaffChat");
        Yaml config = new Yaml("config", "plugins/MistStaffChat");
        ProxiedPlayer p = (ProxiedPlayer)e.getSender();
        if ((Main.toggle.contains(p.getUniqueId()))) {
            e.setCancelled(true);
            for (ProxiedPlayer online: ProxyServer.getInstance().getPlayers()) {
                if (online.hasPermission("miststaffchat.use")) {
                    online.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', messages.getString("staffchat.format").replace("{SERVER}", p.getServer().getInfo().getName()).replace("{USERNAME}", p.getDisplayName()).replace("{MESSAGE}", e.getMessage()))));
                }
            }
        }
        if((!Main.toggle.contains(p.getUniqueId()) &&
                e.getMessage().startsWith(messages.getString("staffchat.symbol")) &&
                config.getBoolean("symbol.enabled") &&
                e.getMessage().substring(1).length() > 0) &&
                p.hasPermission("miststaffchat.use")){
            e.setCancelled(true);
            for (ProxiedPlayer online: ProxyServer.getInstance().getPlayers()) {
                if (online.hasPermission("miststaffchat.use")) {
                    online.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', messages.getString("staffchat.format").replace("{SERVER}", p.getServer().getInfo().getName()).replace("{USERNAME}", p.getDisplayName()).replace("{MESSAGE}", e.getMessage().substring(1)))));
                }
            }
        }
    }
}
