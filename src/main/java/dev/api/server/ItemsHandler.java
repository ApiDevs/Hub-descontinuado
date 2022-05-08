package dev.api.server;

import dev.api.utils.Format;
import me.joeleoli.portal.shared.queue.Queue;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsHandler implements Listener {

    @EventHandler
    public void GiveItems(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.getInventory().clear();
        addItems(p);
    }

    public static void addItems(final Player p) {
        final Queue queue = Queue.getByPlayer(p.getUniqueId());
        p.getInventory().setItem(4, Selector());
        p.getInventory().setItem(0, EnderPearl());
        p.getInventory().setItem(8, Cosmetic());
    }

    public static ItemStack EnderPearl() {
        final ItemStack enderpearl = new ItemStack(Material.SNOW_BALL);
        final ItemMeta enderpearlm = enderpearl.getItemMeta();
        enderpearlm.setDisplayName(Format.translate("&cSnowButt"));
        enderpearl.setItemMeta(enderpearlm);
        return enderpearl;
    }

    public static ItemStack Cosmetic() {
        ItemStack cosmetic = new ItemStack(Material.ENDER_CHEST);
        ItemMeta cosmeticm = cosmetic.getItemMeta();
        cosmeticm.setDisplayName(Format.translate("&cCosmetic"));
        cosmetic.setItemMeta(cosmeticm);
        return cosmetic;
    }

    public static ItemStack Selector() {
        final ItemStack selector = new ItemStack(Material.BOOK);
        final ItemMeta selectorm = selector.getItemMeta();
        selectorm.setDisplayName(Format.translate("&cServer Selector"));
        selector.setItemMeta(selectorm);
        return selector;
    }

    public static ItemStack LeaveQueue() {
        final ItemStack queue = new ItemStack(Material.RED_ROSE);
        final ItemMeta queuem = queue.getItemMeta();
        queuem.setDisplayName(Format.Style("&cLeave Queue"));
        queue.setItemMeta(queuem);
        return queue;
    }
}
