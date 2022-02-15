package net.azisaba.junkgames.junkgameselector.config;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InvalidObjectException;
import java.util.List;

public class GameServerDetail extends GameDetail{
    public final ByteArrayDataOutput out;
    public final JavaPlugin plugin;

    public GameServerDetail(String name, String serverName, String materialName, List<String> details) throws InvalidObjectException {
        super(name, materialName, details);

        out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);

        plugin = JavaPlugin.getProvidingPlugin(getClass());
    }

    @Override
    public void teleport(Player player) {
        player.sendPluginMessage(this.plugin, "BungeeCord", this.out.toByteArray());
    }
}
