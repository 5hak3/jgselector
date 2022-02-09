package net.azisaba.junkgames.junkgameselector.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigLoader {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private final ArrayList<GameDetail> games;

    public ConfigLoader(JavaPlugin plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();
        this.config = this.plugin.getConfig();
        this.games = new ArrayList<>();
    }

    public void reload() {
        this.plugin.reloadConfig();
        this.config = this.plugin.getConfig();
    }

    public int loadGameList() {
        this.reload();
        this.games.clear();
        ConfigurationSection gamelistcs = this.config.getConfigurationSection("gamelist");
        if (gamelistcs == null) {
            this.plugin.getLogger().warning("Gamelist load failed.");
            return -1;
        }
        for (String key: gamelistcs.getKeys(false)) {
            ConfigurationSection cs = gamelistcs.getConfigurationSection(key);

            assert cs != null;
            String title = cs.getString("title");
            String worldName = cs.getString("worldName");
            String materialName = cs.getString("materialName");
            List<String> details = cs.getStringList("details");

            if (title == null || worldName == null || materialName == null) {
                this.plugin.getLogger().warning("Invalid Keys in " + title);
                continue;
            }

            try {
                this.games.add(new GameDetail(title, worldName, materialName, details));
            } catch (InvalidObjectException e) {
                this.plugin.getLogger().warning("Invalid Values in " + title);
                e.printStackTrace();
            }
        }
        return this.countGames();
    }

    public int countGames() {
        return this.games.size();
    }

    public ArrayList<GameDetail> getGames() {
        return games;
    }
}
