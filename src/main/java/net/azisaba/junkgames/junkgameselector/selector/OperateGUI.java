package net.azisaba.junkgames.junkgameselector.selector;

import net.azisaba.junkgames.junkgameselector.config.ConfigLoader;
import net.azisaba.junkgames.junkgameselector.config.GameDetail;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class OperateGUI implements Listener {
    private final ConfigLoader cl;

    public OperateGUI(ConfigLoader cl) {
        this.cl = cl;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof ShowGUI.SelectorGUIHolder)) return;

        Player player = (Player) event.getView().getPlayer();
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null) return;

        for (GameDetail game: cl.getGames()) {
            if (clicked.equals(game.displayItem)) {
                assert game.world != null;
                player.teleport(game.world.getSpawnLocation());
                return;
            }
        }

        event.setCancelled(true);
    }
}
