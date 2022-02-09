package net.azisaba.junkgames.junkgameselector.config;

import net.azisaba.junkgames.junkgameselector.selector.ShowGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reload implements CommandExecutor {
    private final ConfigLoader cl;
    private final ShowGUI sgui;

    public Reload(ConfigLoader cl, ShowGUI sgui) {
        this.cl = cl;
        this.sgui = sgui;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        cl.loadGameList();
        sgui.makeGUI();
        sender.sendMessage(ChatColor.AQUA + "configの再読み込みが完了しました！");
        return true;
    }
}
