package dev.api.server.accessory.cosmetic.outfits;

import dev.api.utils.Format;
import dev.api.utils.ItemBuilder;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;
import org.bukkit.*;

public class Outfits implements Listener
{
    public static void OutfitInv(final Player p) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 18, Format.Style("&cOutfit Menu"));
        final List<String> lore = Arrays.asList("", "&cClick to select Outfit", "");
        final List<String> clearlore = Arrays.asList("", "&7Click to Clear Outfits", "");
        inv.setItem(0, new ItemBuilder(Material.LEATHER_HELMET).title("&eBlack Outfit").lores(lore).color(convert("0")).build());
        inv.setItem(1, new ItemBuilder(Material.LEATHER_HELMET).title("&eBlue Outfit").lores(lore).color(convert("1")).build());
        inv.setItem(2, new ItemBuilder(Material.LEATHER_HELMET).title("&eGreen Outfit").lores(lore).color(convert("2")).build());
        inv.setItem(3, new ItemBuilder(Material.LEATHER_HELMET).title("&eCyan Outfit").lores(lore).color(convert("3")).build());
        inv.setItem(4, new ItemBuilder(Material.LEATHER_HELMET).title("&eRed Outfit").lores(lore).color(convert("4")).build());
        inv.setItem(5, new ItemBuilder(Material.LEATHER_HELMET).title("&ePurple Outfit").lores(lore).color(convert("5")).build());
        inv.setItem(6, new ItemBuilder(Material.LEATHER_HELMET).title("&eOrange Outfit").lores(lore).color(convert("6")).build());
        inv.setItem(7, new ItemBuilder(Material.LEATHER_HELMET).title("&eSilver Outfit").lores(lore).color(convert("7")).build());
        inv.setItem(8, new ItemBuilder(Material.LEATHER_HELMET).title("&eGray Outfit").lores(lore).color(convert("8")).build());
        inv.setItem(9, new ItemBuilder(Material.LEATHER_HELMET).title("&eLime Outfit").lores(lore).color(convert("a")).build());
        inv.setItem(10, new ItemBuilder(Material.LEATHER_HELMET).title("&eYellow Outfit").lores(lore).color(convert("e")).build());
        inv.setItem(11, new ItemBuilder(Material.LEATHER_HELMET).title("&eAqua Outfit").lores(lore).color(convert("b")).build());
        inv.setItem(12, new ItemBuilder(Material.LEATHER_HELMET).title("&eFuchsia Outfit").lores(lore).color(convert("d")).build());
        inv.setItem(13, new ItemBuilder(Material.LEATHER_HELMET).title("&eWhite Outfit").lores(lore).color(convert("f")).build());
        if (p.hasPermission("hub.admin") && p.hasPermission("hub.donator")) {
            inv.setItem(17, new ItemBuilder(Material.FEATHER).title("&cClear Outfit!").lores(clearlore).build());
        }
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
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(Format.Style("&6Outfit Menu"))) {
            if (player.hasPermission("hub.admin") && player.hasPermission("hub.donator")) {
                if (event.getSlot() == 0) {
                    final ItemStack blackh = new ItemBuilder(Material.LEATHER_HELMET).color(convert("0")).build();
                    final ItemStack blackc = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("0")).build();
                    final ItemStack blackl = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("0")).build();
                    final ItemStack blackb = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("0")).build();
                    player.getInventory().setHelmet(blackh);
                    player.getInventory().setChestplate(blackc);
                    player.getInventory().setLeggings(blackl);
                    player.getInventory().setBoots(blackb);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Black&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 1) {
                    final ItemStack blueh = new ItemBuilder(Material.LEATHER_HELMET).color(convert("1")).build();
                    final ItemStack bluec = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("1")).build();
                    final ItemStack bluel = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("1")).build();
                    final ItemStack blueb = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("1")).build();
                    player.getInventory().setHelmet(blueh);
                    player.getInventory().setChestplate(bluec);
                    player.getInventory().setLeggings(bluel);
                    player.getInventory().setBoots(blueb);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Blue&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 2) {
                    final ItemStack greenh = new ItemBuilder(Material.LEATHER_HELMET).color(convert("2")).build();
                    final ItemStack greenc = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("2")).build();
                    final ItemStack greenl = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("2")).build();
                    final ItemStack greenb = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("2")).build();
                    player.getInventory().setHelmet(greenh);
                    player.getInventory().setChestplate(greenc);
                    player.getInventory().setLeggings(greenl);
                    player.getInventory().setBoots(greenb);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Green&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 3) {
                    final ItemStack cyanh = new ItemBuilder(Material.LEATHER_HELMET).color(convert("3")).build();
                    final ItemStack cyanc = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("3")).build();
                    final ItemStack cyanl = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("3")).build();
                    final ItemStack cyanb = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("3")).build();
                    player.getInventory().setHelmet(cyanh);
                    player.getInventory().setChestplate(cyanc);
                    player.getInventory().setLeggings(cyanl);
                    player.getInventory().setBoots(cyanb);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Cyan&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 4) {
                    final ItemStack armor = new ItemBuilder(Material.LEATHER_HELMET).color(convert("4")).build();
                    final ItemStack armor2 = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("4")).build();
                    final ItemStack armor3 = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("4")).build();
                    final ItemStack armor4 = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("4")).build();
                    player.getInventory().setHelmet(armor);
                    player.getInventory().setChestplate(armor2);
                    player.getInventory().setLeggings(armor3);
                    player.getInventory().setBoots(armor4);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Red&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 5) {
                    final ItemStack armor = new ItemBuilder(Material.LEATHER_HELMET).color(convert("5")).build();
                    final ItemStack armor2 = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("5")).build();
                    final ItemStack armor3 = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("5")).build();
                    final ItemStack armor4 = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("5")).build();
                    player.getInventory().setHelmet(armor);
                    player.getInventory().setChestplate(armor2);
                    player.getInventory().setLeggings(armor3);
                    player.getInventory().setBoots(armor4);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Purple&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 6) {
                    final ItemStack armor = new ItemBuilder(Material.LEATHER_HELMET).color(convert("6")).build();
                    final ItemStack armor2 = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("6")).build();
                    final ItemStack armor3 = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("6")).build();
                    final ItemStack armor4 = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("6")).build();
                    player.getInventory().setHelmet(armor);
                    player.getInventory().setChestplate(armor2);
                    player.getInventory().setLeggings(armor3);
                    player.getInventory().setBoots(armor4);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Orange&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 7) {
                    final ItemStack armor = new ItemBuilder(Material.LEATHER_HELMET).color(convert("7")).build();
                    final ItemStack armor2 = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("7")).build();
                    final ItemStack armor3 = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("7")).build();
                    final ItemStack armor4 = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("7")).build();
                    player.getInventory().setHelmet(armor);
                    player.getInventory().setChestplate(armor2);
                    player.getInventory().setLeggings(armor3);
                    player.getInventory().setBoots(armor4);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Silver&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 8) {
                    final ItemStack armor = new ItemBuilder(Material.LEATHER_HELMET).color(convert("8")).build();
                    final ItemStack armor2 = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("8")).build();
                    final ItemStack armor3 = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("8")).build();
                    final ItemStack armor4 = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("8")).build();
                    player.getInventory().setHelmet(armor);
                    player.getInventory().setChestplate(armor2);
                    player.getInventory().setLeggings(armor3);
                    player.getInventory().setBoots(armor4);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Gray&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 9) {
                    final ItemStack armor = new ItemBuilder(Material.LEATHER_HELMET).color(convert("a")).build();
                    final ItemStack armor2 = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("a")).build();
                    final ItemStack armor3 = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("a")).build();
                    final ItemStack armor4 = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("a")).build();
                    player.getInventory().setHelmet(armor);
                    player.getInventory().setChestplate(armor2);
                    player.getInventory().setLeggings(armor3);
                    player.getInventory().setBoots(armor4);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Lime&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 10) {
                    final ItemStack armor = new ItemBuilder(Material.LEATHER_HELMET).color(convert("e")).build();
                    final ItemStack armor2 = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("e")).build();
                    final ItemStack armor3 = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("e")).build();
                    final ItemStack armor4 = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("e")).build();
                    player.getInventory().setHelmet(armor);
                    player.getInventory().setChestplate(armor2);
                    player.getInventory().setLeggings(armor3);
                    player.getInventory().setBoots(armor4);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Yellow&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 11) {
                    final ItemStack armor = new ItemBuilder(Material.LEATHER_HELMET).color(convert("b")).build();
                    final ItemStack armor2 = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("b")).build();
                    final ItemStack armor3 = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("b")).build();
                    final ItemStack armor4 = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("b")).build();
                    player.getInventory().setHelmet(armor);
                    player.getInventory().setChestplate(armor2);
                    player.getInventory().setLeggings(armor3);
                    player.getInventory().setBoots(armor4);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Aqua&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 12) {
                    final ItemStack armor = new ItemBuilder(Material.LEATHER_HELMET).color(convert("d")).build();
                    final ItemStack armor2 = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("d")).build();
                    final ItemStack armor3 = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("d")).build();
                    final ItemStack armor4 = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("d")).build();
                    player.getInventory().setHelmet(armor);
                    player.getInventory().setChestplate(armor2);
                    player.getInventory().setLeggings(armor3);
                    player.getInventory().setBoots(armor4);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6Fuchsia&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 13) {
                    final ItemStack armor = new ItemBuilder(Material.LEATHER_HELMET).color(convert("f")).build();
                    final ItemStack armor2 = new ItemBuilder(Material.LEATHER_CHESTPLATE).color(convert("f")).build();
                    final ItemStack armor3 = new ItemBuilder(Material.LEATHER_LEGGINGS).color(convert("f")).build();
                    final ItemStack armor4 = new ItemBuilder(Material.LEATHER_BOOTS).color(convert("f")).build();
                    player.getInventory().setHelmet(armor);
                    player.getInventory().setChestplate(armor2);
                    player.getInventory().setLeggings(armor3);
                    player.getInventory().setBoots(armor4);
                    player.sendMessage(Format.Style("&fYou has been change your Outfit at &6White&f Outfit!"));
                    player.closeInventory();
                    event.setCancelled(true);
                }
                else if (event.getSlot() == 17) {
                    if (player.getInventory().getHelmet() == null) {
                        player.sendMessage(Format.Style("&cYou do not have any Outfit available, buy them at store.store.stormh.cf"));
                        player.closeInventory();
                        event.setCancelled(true);
                    }
                    else {
                        player.getInventory().setHelmet((ItemStack)null);
                        player.getInventory().setChestplate((ItemStack)null);
                        player.getInventory().setLeggings((ItemStack)null);
                        player.getInventory().setBoots((ItemStack)null);
                        player.sendMessage(Format.Style("&cYou have taken off your outfits, put them back on to look cooler!"));
                        player.closeInventory();
                        event.setCancelled(true);
                    }
                }
            }
            else {
                player.sendMessage(Format.Style("&cPurchase it at store.stormh.cf"));
                player.closeInventory();
                event.setCancelled(true);
            }
        }
    }

    public static Color convert(final String colorString) {
        if (colorString.contains("0")) {
            return Color.BLACK;
        }
        if (colorString.contains("1")) {
            return Color.BLUE;
        }
        if (colorString.contains("2")) {
            return Color.GREEN;
        }
        if (colorString.contains("3")) {
            return Color.TEAL;
        }
        if (colorString.contains("4")) {
            return Color.RED;
        }
        if (colorString.contains("5")) {
            return Color.PURPLE;
        }
        if (colorString.contains("6")) {
            return Color.ORANGE;
        }
        if (colorString.contains("7")) {
            return Color.SILVER;
        }
        if (colorString.contains("8")) {
            return Color.GRAY;
        }
        if (colorString.contains("9")) {
            return Color.BLUE;
        }
        if (colorString.contains("a")) {
            return Color.LIME;
        }
        if (colorString.contains("e")) {
            return Color.YELLOW;
        }
        if (colorString.contains("b")) {
            return Color.AQUA;
        }
        if (colorString.contains("d")) {
            return Color.FUCHSIA;
        }
        if (colorString.contains("f")) {
            return Color.WHITE;
        }
        return Color.BLACK;
    }
}
