package me.feehgamer.msc.bukkit.commands.subcommands.editor;

import com.songoda.core.commands.AbstractCommand;
import com.songoda.core.utils.TextUtils;
import de.leonhard.storage.Json;
import me.feehgamer.msc.bukkit.Main;
import org.bukkit.command.CommandSender;

import java.util.List;

public class EditorFormat extends AbstractCommand {
    private Main plugin;
    public EditorFormat(Main plugin) {
        super(CommandType.CONSOLE_OK, "format");
        this.plugin = plugin;
    }

    @Override
    protected ReturnType runCommand(CommandSender commandSender, String... strings) {
        Json messages = new Json("messages", "plugins/MistStaffChat");
        if(strings.length < 1){
            return ReturnType.SYNTAX_ERROR;
        } else {
            messages.set("staffchat.format", TextUtils.formatText(strings));
            commandSender.sendMessage(TextUtils.formatText(messages.getString("success.format")));
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
        return "/sceditor format <new value>";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
