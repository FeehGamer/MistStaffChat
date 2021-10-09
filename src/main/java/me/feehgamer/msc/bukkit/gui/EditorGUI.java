package me.feehgamer.msc.bukkit.gui;

import com.songoda.core.compatibility.CompatibleMaterial;
import com.songoda.core.gui.Gui;
import com.songoda.core.gui.GuiUtils;
import com.songoda.core.input.ChatPrompt;
import com.songoda.core.utils.TextUtils;
import de.leonhard.storage.Json;
import me.feehgamer.msc.bukkit.Main;
import org.bukkit.entity.Player;

public class EditorGUI extends Gui {
    private final Player p;

    private final Main plugin;
    private Json gui = new Json("gui", "plugins/MistStaffChat");
    private Json messages = new Json("messages", "plugins/MistStaffChat");
    public EditorGUI(Player p, Main plugin) {
        this.p = p;
        this.plugin = plugin;
        setRows(3);
        setTitle(TextUtils.formatText(gui.getString("title")));
        setDefaultItem(GuiUtils.getBorderItem(CompatibleMaterial.GRAY_STAINED_GLASS_PANE));
        setButton(1, 3, GuiUtils.createButtonItem(CompatibleMaterial.FEATHER, TextUtils.formatText(gui.getString("symbol.button"))),
                (event) -> {
                    ChatPrompt.showPrompt(plugin, p,
                            TextUtils.formatText(gui.getString("symbol.prompt")),
                            response -> messages.set("staffchat.symbol", response.getMessage()))
                            .setOnClose(() -> {
                                p.sendMessage(TextUtils.formatText(gui.getString("symbol.success")));
                            });
                });
        setButton(1, 5, GuiUtils.createButtonItem(CompatibleMaterial.FEATHER, TextUtils.formatText(gui.getString("format.button"))),
                (event) -> {
                    ChatPrompt.showPrompt(plugin, p,
                            TextUtils.formatText(gui.getString("symbol.prompt")),
                            response -> messages.set("staffchat.format", response.getMessage()))
                            .setOnClose(() -> {
                                p.sendMessage(TextUtils.formatText(gui.getString("format.success")));
                            });
                });
    }
}
