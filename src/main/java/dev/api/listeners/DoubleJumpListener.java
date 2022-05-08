package dev.api.listeners;

import dev.api.Hub;
import dev.api.utils.config.ConfigFile;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJumpListener implements Listener {

    public DoubleJumpListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
    }

    @EventHandler
    public void onJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (ConfigFile.getConfig().getBoolean("doublejump.enabled")) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }
            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setVelocity(player.getLocation().getDirection().multiply(ConfigFile.getConfig().getDouble("doublejump.velocity")).setY(1));
            player.playSound(player.getLocation(), Sound.valueOf(ConfigFile.getConfig().getString("doublejump.sound").toUpperCase()), 1.0F, 1.0F);
        }
    }

    @EventHandler
    public void onPlayerGround(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
            player.setAllowFlight(true);
        }
    }
}
