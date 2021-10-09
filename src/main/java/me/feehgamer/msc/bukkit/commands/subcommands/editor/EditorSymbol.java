package me.feehgamer.msc.bukkit.commands.subcommands.editor;

import com.songoda.core.commands.AbstractCommand;
import com.songoda.core.utils.TextUtils;
import de.leonhard.storage.Json;
import me.feehgamer.msc.bukkit.Main;
import org.bukkit.command.CommandSender;

import java.util.List;

public class EditorSymbol extends AbstractCommand {
    private Main plugin;
    public EditorSymbol(Main plugin) {
        super(CommandType.CONSOLE_OK, "symbol");
        this.plugin = plugin;
    }

    @Override
    protected ReturnType runCommand(CommandSender commandSender, String... strings) {
        Json messages = new Json("messages", "plugins/MistStaffChat");
        if(strings.length < 1){
            return ReturnType.SYNTAX_ERROR;
        } else {
            messages.set("staffchat.symbol", strings[0]);
            commandSender.sendMessage(TextUtils.formatText(messages.getString("success.symbol").replace("{SYMBOL}", strings[0])));
            return ReturnType.SUCCESS;
        }
    }

    @Override
    protected List<String> onTab(CommandSender commandSender, String... strings) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "miststaffchat.editor";
    }

    @Override
    public String getSyntax() {
        return "/sceditor symbol <new value>";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
