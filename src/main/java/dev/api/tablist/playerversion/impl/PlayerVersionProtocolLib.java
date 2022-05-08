package dev.api.tablist.playerversion.impl;

import com.comphenix.protocol.ProtocolLibrary;
import dev.api.tablist.playerversion.PlayerVersion;
import dev.api.tablist.playerversion.sPlayerVersion;
import org.bukkit.entity.Player;

public class PlayerVersionProtocolLib implements sPlayerVersion {

    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(ProtocolLibrary.getProtocolManager().getProtocolVersion(player));
    }
}
