package dev.api.server.queue;

import dev.api.utils.Format;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class LeaveQueue implements Listener
{
    public static ItemStack LeaveQueue() {
        final ItemStack queue = new ItemStack(Material.RED_ROSE);
        final ItemMeta queuem = queue.getItemMeta();
        queuem.setDisplayName(Format.Style("&cLeave Queue"));
        queue.setItemMeta(queuem);
        return queue;
    }

    @EventHandler
    public void onClick(final PlayerInteractEvent event) {
        if (event.getAction().equals((Object)Action.RIGHT_CLICK_AIR) || event.getAction().equals((Object)Action.RIGHT_CLICK_BLOCK)) {
            final Player p = event.getPlayer();
            if (p.getItemInHand().equals((Object)LeaveQueue())) {
                p.performCommand("leavequeue");
                p.getInventory().setItem(7, (ItemStack)null);
            }
        }
    }
}
