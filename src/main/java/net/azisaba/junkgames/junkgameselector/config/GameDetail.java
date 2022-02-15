package net.azisaba.junkgames.junkgameselector.config;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        meta.displayName(Component.text(name));
        meta.lore(details.stream().map(Component::text).collect(Collectors.toList()));
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
