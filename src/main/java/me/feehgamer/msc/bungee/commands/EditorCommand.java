package me.feehgamer.msc.bungee.commands;

import de.leonhard.storage.Json;
import me.feehgamer.msc.bungee.Main;
import me.feehgamer.msc.bungee.utils.TextUtils;
import me.feehgamer.msc.shared.ErrorEnum;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

public class EditorCommand extends Command {
    private Main plugin;
    public EditorCommand(Main plugin){
        super("sceditor", "miststaffchat.editor", "scedit");
        this.plugin = plugin;
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, this);
    }
    private static String convert(String[] strArr, String delimiter) {
        strArr = Arrays.copyOfRange(strArr, 1, strArr.length);
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        Json messages = new Json("messages", "plugins/MistStaffChat");
            if(args.length < 2){
                for(String s : ErrorEnum.SYNTAX_ERROR.getMessage()){
                    sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', s.replace("{SYNTAX}", getSyntax()))));
                    return;
                }
            }
            switch(args[0]){
                default:
                    for(String s : ErrorEnum.SYNTAX_ERROR.getMessage()){
                        sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', s.replace("{SYNTAX}", getSyntax()))));
                        break;
                    }
                case "symbol":
                    messages.set("staffchat.symbol", args[1]);
                    sender.sendMessage(new TextComponent(TextUtils.formatText(messages.getString("success.symbol").replace("{SYMBOL}", args[1]))));
                    break;
                case "format":
                    messages.set("staffchat.format", convert(args, " "));
                    sender.sendMessage(new TextComponent(TextUtils.formatText(messages.getString("success.format"))));
                    break;
            }
    }
    public String getSyntax(){
        return "/sceditor <symbol|format> <new value>";
    }
}
