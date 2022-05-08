package dev.api.tablist;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

public class TablistListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Tablist.getInstance().getPlayerTablist().put(player.getUniqueId(), new PlayerTablist(event.getPlayer()));
    }


    private void handleDisconnect(Player player) {
        Team team = player.getScoreboard().getTeam("\\u000181");
        if (team != null) {
            team.unregister();
        }

        Tablist.getInstance().getPlayerTablist().remove(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        handleDisconnect(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerKick(PlayerKickEvent event) {
        handleDisconnect(event.getPlayer());
    }


}