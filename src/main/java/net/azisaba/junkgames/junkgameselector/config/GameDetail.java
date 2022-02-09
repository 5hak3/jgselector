package net.azisaba.junkgames.junkgameselector.config;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Objects;

public class GameDetail {
    public final String name;
    public final World world;
    public final ItemStack displayItem;

    public GameDetail(String name, String worldName, String materialName, List<String> details) throws InvalidObjectException {
        this.name = name;

        this.world = Bukkit.getWorld(worldName);
        if (this.world == null) {
            throw new InvalidObjectException("World Name is not found.");
        }

        Material mat = Material.getMaterial(materialName);
        if (mat == null) {
            throw new InvalidObjectException("Material Name is not found.");
        }
        this.displayItem = new ItemStack(mat);

        ItemMeta meta = Objects.requireNonNull(this.displayItem.getItemMeta());
        meta.setDisplayName(name);
        meta.setLore(details);
        this.displayItem.setItemMeta(meta);
    }

    @Override
    public String toString() {
        return "GameDetail{" +
                "name='" + name + '\'' +
                '}';
    }
}
