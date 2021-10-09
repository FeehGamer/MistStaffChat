package me.feehgamer.msc.bungee.commands;

import de.leonhard.storage.Json;
import me.feehgamer.msc.bungee.utils.TextUtils;
import me.feehgamer.msc.bungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class StaffchatCommand extends Command {
    private Main plugin;
    public StaffchatCommand(Main plugin){
        super("staffchat", "miststaffchat.use", "sc", "staff");
        this.plugin = plugin;
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)plugin, this);
    }
    private static String convert(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        Json messages = new Json("messages", "plugins/MistStaffChat");
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', messages.getString("errors.console"))));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer)sender;
        if(args.length < 1){
            if(Main.toggle.contains(player.getUniqueId())) {
                Main.toggle.remove(player.getUniqueId());
                player.sendMessage(new TextComponent(TextUtils.formatText(messages.getString("staffchat.off"))));
                return;
            } else {
                Main.toggle.add(player.getUniqueId());
                player.sendMessage(new TextComponent(TextUtils.formatText(messages.getString("staffchat.on"))));
                return;
            }
        } else {
            String message = convert(args, " ");
            for(ProxiedPlayer online_Player : ProxyServer.getInstance().getPlayers()){
                if(online_Player.hasPermission("miststaffchat.use")){
                    online_Player.sendMessage(new TextComponent(messages.getString("staffchat.format").replace("{SERVER}", player.getServer().getInfo().getName()).replace("{USERNAME}", player.getDisplayName()).replace("{MESSAGE}", TextUtils.formatText(message)).replaceAll("&", "§")));
                }
            }
            return;
        }
    }
}
