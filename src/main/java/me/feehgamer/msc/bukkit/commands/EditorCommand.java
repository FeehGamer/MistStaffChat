package me.feehgamer.msc.bukkit.commands;

import com.songoda.core.commands.AbstractCommand;
import de.leonhard.storage.Yaml;
import me.feehgamer.msc.bukkit.Main;
import me.feehgamer.msc.bukkit.gui.EditorGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class EditorCommand extends AbstractCommand {
    private Yaml config = new Yaml("config", "plugins/MistStaffChat");
    private Main plugin;
    public EditorCommand(Main plugin) {
        super(CommandType.PLAYER_ONLY, "sceditor");
        this.plugin = plugin;
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        plugin.getGuiManager().showGUI((Player)sender, new EditorGUI((Player)sender, plugin));
        return ReturnType.SUCCESS;
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
        return "/sceditor [symbol|format]";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
