package net.azisaba.junkgames.junkgameselector.config;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.InvalidObjectException;
import java.util.List;

public class GameWorldDetail extends GameDetail {
    public final World world;

    public GameWorldDetail(String name, String worldName, String materialName, List<String> details) throws InvalidObjectException {
        super(name, materialName, details);

        this.world = Bukkit.getWorld(worldName);
        if (this.world == null) {
            throw new InvalidObjectException("World Name is not found.");
        }
    }

    @Override
    public void teleport(Player player) {
        player.teleport(this.world.getSpawnLocation());
    }
}
