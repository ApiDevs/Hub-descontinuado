package dev.api.tablist.playerversion;

import dev.api.tablist.playerversion.impl.PlayerVersionProtocolSupport;
import dev.api.tablist.playerversion.impl.PlayerVersionViaVersion;
import dev.api.tablist.playerversion.impl.PlayerVersion1_7;
import dev.api.tablist.playerversion.impl.PlayerVersionProtocolLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class PlayerVersionHandler {

    public static sPlayerVersion version;

    public PlayerVersionHandler() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        if (serverVersion.equalsIgnoreCase("v1_7_R4")) {
            version = new PlayerVersion1_7();
            return;
        }
        if (pluginManager.getPlugin("ProtocolSupport") != null
                || pluginManager.getPlugin("CuckSupport") != null) {
            version = new PlayerVersionProtocolSupport();
            return;
        }
        if (pluginManager.getPlugin("ViaVersion") != null) {
            version = new PlayerVersionViaVersion();
            return;
        }
        if (pluginManager.getPlugin("ProtocolLib") != null) {
            version = new PlayerVersionProtocolLib();
            return;
        }
    }
    public static PlayerVersion getPlayerVersion(Player player) {
        return version.getPlayerVersion(player);
    }
}
