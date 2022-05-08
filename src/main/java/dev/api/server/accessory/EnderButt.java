package dev.api.server.accessory;

import dev.api.utils.Format;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderButt implements Listener
{
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (e.getItem().getItemMeta() == null) {
                return;
            }
            if (e.getItem().getItemMeta().getDisplayName() == null) {
                return;
            }
            if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(Format.translate("&cSnowButt"))) {
                e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(3.5f));
                e.setCancelled(true);
                e.setUseItemInHand(Event.Result.DENY);
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
                e.getPlayer().updateInventory();
            }
        }
    }
}
