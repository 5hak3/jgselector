package net.azisaba.junkgames.junkgameselector;

import net.azisaba.junkgames.junkgameselector.config.ConfigLoader;
import net.azisaba.junkgames.junkgameselector.config.Reload;
import net.azisaba.junkgames.junkgameselector.selector.OperateGUI;
import net.azisaba.junkgames.junkgameselector.selector.ShowGUI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class JunkGameSelector extends JavaPlugin {

    @Override
    public void onEnable() {
        // loading gamelist and some configs
        getLogger().info("Loading Games...");
        ConfigLoader cl = new ConfigLoader(this);
        getLogger().info(cl.loadGameList() + " Games Loaded.");

        // loading other classes
        ShowGUI sgui = new ShowGUI(cl);
        OperateGUI ogui = new OperateGUI(cl);
        Reload reload = new Reload(cl, sgui);

        Objects.requireNonNull(getCommand("jgselector")).setExecutor(sgui);
        Objects.requireNonNull(getCommand("jgsgive")).setExecutor(sgui);
        Objects.requireNonNull(getCommand("jgsreload")).setExecutor(reload);
        getServer().getPluginManager().registerEvents(sgui, this);
        getServer().getPluginManager().registerEvents(ogui, this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getLogger().info("Load Plugin Success!!!");
    }
}
