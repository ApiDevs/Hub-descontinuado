package dev.api.tablist.playerversion.impl;

import dev.api.tablist.playerversion.PlayerVersion;
import dev.api.tablist.playerversion.sPlayerVersion;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerVersion1_7 implements sPlayerVersion {

    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(
                ((CraftPlayer) player).getHandle().playerConnection.networkManager.getVersion()
        );
    }
}
