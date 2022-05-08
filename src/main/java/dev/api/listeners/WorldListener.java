package dev.api.listeners;

import dev.api.Hub;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListener implements Listener {

    public WorldListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);

        player.getInventory().clear();
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    @EventHandler
    private void onWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onFood(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (!event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))
            event.setCancelled(true);
    }

    @EventHandler
    private void onPickup(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent event) {
        if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPermission("stormhub.command.place") || !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPermission("stormhub.command.break") || !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void bucketFill(PlayerBucketEmptyEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) event.setCancelled(true);
    }

    @EventHandler
    private void bucketEmpty(PlayerBucketFillEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) event.setCancelled(true);
    }

    @EventHandler
    private void playerFish(PlayerFishEvent event){
        event.setCancelled(true);

    }

    @EventHandler
    private void entityExplode(EntityExplodeEvent event){
        event.setCancelled(true);
    }

}
