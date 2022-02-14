package net.azisaba.junkgames.junkgameselector.selector;

import net.azisaba.junkgames.junkgameselector.config.ConfigLoader;
import net.azisaba.junkgames.junkgameselector.config.GameDetail;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ShowGUI implements Listener, CommandExecutor {
    private final ConfigLoader cl;
    private final Inventory gui;

    /**
     * GUIを表示するトリガーアイテムの定義
     */
    private static final ItemStack trgItem;
    static {
        trgItem = new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta meta = trgItem.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "JGSelectorを開く！");
        meta.setLore(List.of("右クリックでJunk Game Selectorを開きます．"));
        meta.setCustomModelData(100);
        trgItem.addEnchantment(Enchantment.BINDING_CURSE, 0);
        trgItem.setItemMeta(meta);
    }

    public ShowGUI(ConfigLoader cl) {
        this.cl = cl;
        this.gui = Bukkit.createInventory(new SelectorGUIHolder("jgsgui"), 54,
                ChatColor.GREEN + "Junk Game Selector!");
        this.makeGUI();
    }

    /**
     * GUIを作る．
     * ただし，54個しかゲームが登録できないため，それを超えそうな場合にはマルチページのGUIを作ることにする．
     * ArrayList<Inventory>にしてArrowで行き来すれば良さそう．
     */
    public void makeGUI() {
        int counter = 0;
        for (GameDetail game: cl.getGames()) {
            this.gui.setItem(counter, game.displayItem);
            counter++;
            if (counter > 54) break;
        }
    }

    /**
     * コマンドが発行されたとき，
     * jgselectorならsenderにJGSelectorのGUIを出す．
     * jgsgiveならsenderがtrgItemを持っていなければ渡す．
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤー専用です．");
            return false;
        }
        if (command.getName().equalsIgnoreCase("jgselector")) ((Player) sender).openInventory(this.gui);
        else if (command.getName().equalsIgnoreCase("jgsgive")) {
            if (((Player) sender).getInventory().contains(trgItem)) {
                sender.sendMessage(ChatColor.RED + "あなたは既に起動アイテムを持っています．");
                return true;
            }
            ((Player) sender).getInventory().addItem(trgItem);
            sender.sendMessage(ChatColor.AQUA + "起動アイテムを配布しました．");
        }
        return true;
    }

    /**
     * trgItemをインタラクトした時に
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onUse(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getItem() == null) return;
        if (!event.getItem().equals(trgItem)) return;

        event.getPlayer().openInventory(this.gui);
        event.setCancelled(true);
    }

    /**
     * 参加時にtrgItemを持っていなければ配布する．
     * → 廃止
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
    }

    /**
     * ワールド移動時にtrgItemを持っていなければ配布する．
     * → 廃止
     */
    @EventHandler
    public void onMove(PlayerChangedWorldEvent event) {
    }

    /**
     * GUI用のインベントリホルダー
     */
    public static class SelectorGUIHolder implements InventoryHolder {
        public final String label;

        public SelectorGUIHolder(String label) {
            this.label = label;
        }

        @Override
        public Inventory getInventory() {
            return null;
        }
    }
}
