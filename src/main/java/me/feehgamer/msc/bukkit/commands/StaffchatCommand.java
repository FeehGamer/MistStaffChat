package me.feehgamer.msc.bukkit.commands;

import com.songoda.core.commands.AbstractCommand;
import com.songoda.core.utils.TextUtils;
import de.leonhard.storage.Json;
import me.feehgamer.msc.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StaffchatCommand extends AbstractCommand {
    private Main plugin;
    public StaffchatCommand(Main plugin){
        super(CommandType.PLAYER_ONLY, "staffchat");
        this.plugin = plugin;
    }
    private static String convert(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }
    @Override
    protected ReturnType runCommand(CommandSender commandSender, String... strings) {
        Json messages = new Json("messages", "plugins/MistStaffChat");
        Player player = (Player)commandSender;
        if(strings.length < 1){
            if(Main.getInstance().toggle.contains(player.getUniqueId())) {
                Main.getInstance().toggle.remove(player.getUniqueId());
                player.sendMessage(TextUtils.formatText(messages.getString("staffchat.off")));
                return ReturnType.SUCCESS;
            } else {
                Main.getInstance().toggle.add(player.getUniqueId());
                player.sendMessage(TextUtils.formatText(messages.getString("staffchat.on")));
                return ReturnType.SUCCESS;
            }
        } else {
            String message = convert(strings, " ");
            for(Player online_Player : Bukkit.getOnlinePlayers()){
                if(online_Player.hasPermission("miststaffchat.use")){
                    online_Player.sendMessage(messages.getString("staffchat.format").replace("{USERNAME}", player.getDisplayName()).replace("{MESSAGE}", TextUtils.formatText(message)).replaceAll("&", "§"));
                }
            }
            return ReturnType.SUCCESS;
        }
    }

    @Override
    protected List<String> onTab(CommandSender commandSender, String... strings) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "miststaffchat.use";
    }

    @Override
    public String getSyntax() {
        return "/staffchat [message]";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
