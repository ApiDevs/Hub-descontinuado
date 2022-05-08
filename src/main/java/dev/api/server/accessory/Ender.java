package dev.api.server.accessory;

import dev.api.utils.Format;
import dev.api.utils.ItemStackUtil;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.spigotmc.event.entity.*;

public class Ender implements Listener
{
    public static ItemStack EnderPearl() {
        final ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL);
        final ItemMeta enderpearlm = enderpearl.getItemMeta();
        enderpearlm.setDisplayName(Format.translate("&cEnderButt"));
        enderpearl.setItemMeta(enderpearlm);
        return enderpearl;
    }

    @EventHandler
    public void onPearl(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_AIR && event.getItem() != null && event.getItem().isSimilar(ItemStackUtil.createItem(Material.ENDER_PEARL, 1, Format.translate("&cEnderButt"), new String[0]))) {
            event.setCancelled(true);
            if (player.isInsideVehicle()) {
                player.getVehicle().remove();
            }
            event.setUseItemInHand(Event.Result.DENY);
            event.setUseInteractedBlock(Event.Result.DENY);
            final EnderPearl pearl = (EnderPearl)player.launchProjectile((Class)EnderPearl.class);
            pearl.setPassenger((Entity)player);
            pearl.setVelocity(player.getLocation().getDirection().normalize().multiply(3.0f));
            player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
            player.spigot().setCollidesWithEntities(false);
            player.getInventory().setItem(0, EnderPearl());
            player.updateInventory();
        }
    }

    @EventHandler
    public void onDismount(final EntityDismountEvent event) {
        event.getDismounted().remove();
    }
}

