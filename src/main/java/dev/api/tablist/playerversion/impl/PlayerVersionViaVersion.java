package dev.api.tablist.playerversion.impl;

import dev.api.tablist.playerversion.PlayerVersion;
import dev.api.tablist.playerversion.sPlayerVersion;
import org.bukkit.entity.Player;
import us.myles.ViaVersion.api.Via;

public class PlayerVersionViaVersion implements sPlayerVersion {

    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(Via.getAPI().getPlayerVersion(player));
    }
}
