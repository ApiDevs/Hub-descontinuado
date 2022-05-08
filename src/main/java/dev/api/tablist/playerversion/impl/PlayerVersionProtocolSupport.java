package dev.api.tablist.playerversion.impl;

import dev.api.tablist.playerversion.PlayerVersion;
import dev.api.tablist.playerversion.sPlayerVersion;
import org.bukkit.entity.Player;
import protocolsupport.api.ProtocolSupportAPI;

public class PlayerVersionProtocolSupport implements sPlayerVersion {

    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(ProtocolSupportAPI.getProtocolVersion(player).getId());
    }
}
