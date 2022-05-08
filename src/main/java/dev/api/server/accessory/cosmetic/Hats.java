package dev.api.server.accessory.cosmetic;

import dev.api.utils.Format;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.*;

public class Hats implements Listener
{
    public static void HatsInv(final Player p) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, Format.translate("&cHats Menu"));
        final List<String> lore = Arrays.asList("", "&cClick to select Outfit", "");
        final ItemStack mob = new ItemStack(Material.SKULL_ITEM, 1, (short)2);
        final ItemMeta mobm = mob.getItemMeta();
        mobm.setDisplayName(Format.translate("&c&lMobs"));
        mob.setItemMeta(mobm);
        final ItemStack astrounat = new ItemStack(Material.STAINED_GLASS, 1, (short)0);
        final ItemMeta astrounatm = astrounat.getItemMeta();
        astrounatm.setDisplayName(Format.translate("&c&lAstronaut"));
        astrounat.setItemMeta(astrounatm);
        final ItemStack Colour = new ItemStack(Material.WOOL, 1, (short)6);
        final ItemMeta Colourm = Colour.getItemMeta();
        Colourm.setDisplayName(Format.translate("&cColours"));
        Colour.setItemMeta(Colourm);
        inv.setItem(11, mob);
        inv.setItem(13, astrounat);
        inv.setItem(15, Colour);
        p.openInventory(inv);
    }

    @EventHandler
    public void onClickInventory(final InventoryClickEvent event) {
        final Player player = (Player)event.getWhoClicked();
        final ItemStack item = event.getCurrentItem();
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta()) {
            return;
        }
        if (event.getCurrentItem().getItemMeta() == null) {
            return;
        }
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(Format.translate("&cHats Menu"))) {
            if (player.hasPermission("hub.admin") && player.hasPermission("storm.donator")) {
                if (event.getSlot() == 11) {
                    player.sendMessage("");
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 13) {
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 15) {
                    event.setCancelled(true);
                }
            }
            else {
                player.sendMessage(Format.translate("&cPurchase it at store.storm.cf"));
                player.closeInventory();
                event.setCancelled(true);
            }
        }
    }
}
