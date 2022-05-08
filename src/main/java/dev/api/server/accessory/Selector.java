package dev.api.server.accessory;

import dev.api.Hub;
import dev.api.server.ItemsHandler;
import dev.api.utils.Bungee;
import dev.api.utils.Format;
import dev.api.utils.ItemBuilder;
import me.joeleoli.portal.shared.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.regex.Pattern;

public class Selector implements Listener {

    private Pattern status;


    public void SelectorInventory(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9 * 3, Format.Style("&dServer Selector"));

        List<String> HCF = Arrays.asList("&7this modality is based in","&7original hardcore factions","","&a● &fTeam Size: &d8","&a● &fMap Info: &dProt 2, Sharp 2","&a● &fPlayers: &d" + Hub.getInst().getServerHandler().getServers().get(Bungee.ServerType.HCF));
        inv.setItem(11 - 1, new ItemBuilder(Material.DIAMOND_SWORD).title("&2&lHCF").lores(HCF).build());
        List<String> Kits = Arrays.asList("&7Practice your hcf skills","&7Free kits, Daily koths","","&a● &fTeam Size: &d25","&a● &fMap Info: &dProt 2, Sharp 2","&a● &fPlayers: &d" + Hub.getInst().getServerHandler().getServers().get(Bungee.ServerType.Kits));
        inv.setItem(12 - 1, new ItemBuilder(Material.ENDER_CHEST).title("&d&lKits").lores(Kits).build());
        p.openInventory(inv);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals((Object) Action.RIGHT_CLICK_AIR) || event.getAction().equals((Object) Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if (player.getItemInHand().equals((Object) ItemsHandler.Selector())) {
                this.SelectorInventory(player);
            }
        }
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Queue queue = Queue.getByPlayer(player.getUniqueId());

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta()) {
            return;
        }
        if (event.getCurrentItem().getItemMeta() == null) {
            return;
        }
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(Format.Style("&2Server Selector"))) {
            if (event.getSlot() == 11 - 1) {
                Bukkit.dispatchCommand((CommandSender) player, "joinqueue hcf");
                player.closeInventory();
            } else if (event.getSlot() == 12 - 1) {
                Bukkit.dispatchCommand((CommandSender) player, "joinqueue kits");
                player.closeInventory();
            }
        }
    }
}