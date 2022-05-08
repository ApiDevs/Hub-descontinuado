package dev.api.server.accessory;

import dev.api.utils.Format;
import dev.api.utils.ItemBuilder;
import dev.api.server.accessory.cosmetic.outfits.Outfits;
import dev.api.server.accessory.cosmetic.particle.Particles;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Cosmetic implements Listener
{
    public static ItemStack Cosmetic() {
        final ItemStack cosmetic = new ItemStack(Material.ENDER_CHEST);
        final ItemMeta cosmeticm = cosmetic.getItemMeta();
        cosmeticm.setDisplayName(Format.Style("&cCosmetic"));
        cosmetic.setItemMeta(cosmeticm);
        return cosmetic;
    }

    public void CosmeticInventory(final Player p) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, Format.Style("&cCosmetic Menu"));
        final List<String> armors = Arrays.asList("", "&7Change your outfit", "", "&cClick to view outfits");
        final List<String> armorsno = Arrays.asList("", "&7Change your outfit", "", "&cClick to view outfits", "&cPurchase at store.stormh.cf");
        final List<String> hats = Arrays.asList("", "&7Change your tag", "", "&cClick to view tags");
        final List<String> hatsno = Arrays.asList("", "&7Change your tag", "", "&cClick to view tags", "&cPurchase at store.stormh.cf");
        final List<String> Particles = Arrays.asList("", "&7Change your particles", "", "&cClick to view particles");
        final List<String> Particlesno = Arrays.asList("", "&7Change your particles", "", "&cClick to view particles", "&cPurchase at store.stormh.cf");
        if (p.hasPermission("hub.admin") && p.hasPermission("hub.donator")) {
            inv.setItem(15, new ItemBuilder(Material.NAME_TAG).title("&cTags").lores(hats).build());
        }
        else {
            inv.setItem(15, new ItemBuilder(Material.NAME_TAG).title("&cTags").lores(hatsno).build());
        }
        if (p.hasPermission("hub.admin") && p.hasPermission("hub.donator")) {
            inv.setItem(13, new ItemBuilder(Material.DIAMOND_CHESTPLATE).title("&cArmors").lores(armors).build());
        }
        else {
            inv.setItem(13, new ItemBuilder(Material.DIAMOND_CHESTPLATE).title("&cArmors").lores(armorsno).build());
        }
        if (p.hasPermission("hub.admin") && p.hasPermission("hub.donator")) {
            inv.setItem(11, new ItemBuilder(Material.BLAZE_POWDER).title("&cParticles").lores(Particles).build());
        }
        else {
            inv.setItem(11, new ItemBuilder(Material.BLAZE_POWDER).title("&cParticles").lores(Particlesno).build());
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        if (event.getAction().equals((Object)Action.RIGHT_CLICK_AIR) || event.getAction().equals((Object)Action.RIGHT_CLICK_BLOCK)) {
            final Player player = event.getPlayer();
            if (player.getItemInHand().equals((Object)Cosmetic())) {
                this.CosmeticInventory(player);
            }
        }
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
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(Format.Style("&cCosmetic Menu"))) {
            if (event.getSlot() == 13) {
                if (player.hasPermission("hub.donator")) {
                    Outfits.OutfitInv(player);
                    event.setCancelled(true);
                }
                else {
                    player.closeInventory();
                    player.sendMessage(Format.Style("&cPurchase it at store.stormh.cf"));
                }
            }
            else if (event.getSlot() == 11) {
                if (player.hasPermission("hub.donator")) {
                    Particles.ParticleInv(player);
                    event.setCancelled(true);
                }
                else {
                    player.closeInventory();
                    player.sendMessage(Format.Style("&cPurchase it at store.serpentmc.club"));
                }
            }
            else if (event.getSlot() == 15) {
                if (player.hasPermission("hub.donator")) {
                    player.performCommand("tags");
                    event.setCancelled(true);
                }
                else {
                    player.closeInventory();
                    player.sendMessage(Format.Style("&cPurchase it at store.stormh.cf"));
                }
            }
        }
    }
}
