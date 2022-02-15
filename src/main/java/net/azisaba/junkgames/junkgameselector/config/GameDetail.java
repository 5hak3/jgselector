package net.azisaba.junkgames.junkgameselector.config;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Objects;

public abstract class GameDetail {
    public final String name;
    public final ItemStack displayItem;

    public GameDetail(String name, String materialName, List<String> details) throws InvalidObjectException {
        this.name = name;

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

    abstract public void teleport(Player player);

    @Override
    public String toString() {
        return "GameWorldDetail{" +
                "name='" + name + '\'' +
                '}';
    }
}
