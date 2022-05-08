package dev.api.server.accessory.cosmetic.particle;

import dev.api.Hub;
import dev.api.utils.Format;
import dev.api.utils.ItemBuilder;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.event.inventory.*;
import org.bukkit.plugin.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;

public class Particles implements Listener
{
    public static void ParticleInv(final Player p) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, Format.Style("&cParticle Menu"));
        final List<String> lore = Arrays.asList("", "&6Click to select Outfit", "");
        inv.setItem(11, new ItemBuilder(Material.BLAZE_POWDER).title("&6Flame Particle").build());
        inv.setItem(13, new ItemBuilder(Material.TNT).title("&eExplosion Particle").build());
        inv.setItem(15, new ItemBuilder(Material.REDSTONE).title("&cHeart Particle").build());
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
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(Format.Style("&2Particle Menu"))) {
            if (player.hasPermission("hub.admin") && player.hasPermission("hub.donator")) {
                if (event.getSlot() == 11) {
                    if (ParticleParameter.set.containsKey(player)) {
                        Bukkit.getScheduler().cancelTask((int)ParticleParameter.set.get(player));
                        ParticleParameter.set.replace(player, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) Hub.getInst(), () -> ParticleEffect.FLAME.display(10.0f, 15.0f, 0.0f, 0.0f, 0, player.getLocation().add(-0.0, 2.0, 0.0), 10.0), 0L, 0L));
                        player.sendMessage(Format.Style("&fYou has been Changed your &2Particle!"));
                        player.closeInventory();
                    }
                    else {
                        ParticleParameter.set.put(player, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) Hub.getInst(), () -> ParticleEffect.FLAME.display(10.0f, 15.0f, 0.0f, 0.0f, 0, player.getLocation().add(-0.0, 2.0, 0.0), 10.0), 0L, 0L));
                        player.sendMessage(Format.Style("&fYou has been set &6Flame &fParticle!"));
                        player.closeInventory();
                    }
                }
                else if (event.getSlot() == 13) {
                    if (ParticleParameter.set.containsKey(player)) {
                        Bukkit.getScheduler().cancelTask((int)ParticleParameter.set.get(player));
                        ParticleParameter.set.replace(player, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) Hub.getInst(), () -> ParticleEffect.EXPLOSION_NORMAL.display(10.0f, 15.0f, 0.0f, 0.0f, 0, player.getLocation().add(-0.0, 2.0, 0.0), 10.0), 0L, 0L));
                        player.sendMessage(Format.Style("&fYou has been Changed your &2Particle!"));
                        player.closeInventory();
                    }
                    else {
                        ParticleParameter.set.put(player, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) Hub.getInst(), () -> ParticleEffect.EXPLOSION_NORMAL.display(10.0f, 15.0f, 0.0f, 0.0f, 0, player.getLocation().add(-0.0, 2.0, 0.0), 10.0), 0L, 0L));
                        player.sendMessage(Format.Style("&fYou has been set &eExplosion &fParticle!"));
                        player.closeInventory();
                    }
                }
                else if (event.getSlot() == 15) {
                    if (ParticleParameter.set.containsKey(player)) {
                        Bukkit.getScheduler().cancelTask((int)ParticleParameter.set.get(player));
                        ParticleParameter.set.replace(player, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) Hub.getInst(), () -> ParticleEffect.HEART.display(10.0f, 15.0f, 0.0f, 0.0f, 0, player.getLocation().add(-0.0, 2.0, 0.0), 10.0), 0L, 0L));
                        player.sendMessage(Format.Style("&fYou has been Changed your &2Particle!"));
                        player.closeInventory();
                    }
                    else {
                        ParticleParameter.set.put(player, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) Hub.getInst(), () -> ParticleEffect.HEART.display(10.0f, 15.0f, 0.0f, 0.0f, 0, player.getLocation().add(-0.0, 2.0, 0.0), 10.0), 0L, 0L));
                        player.sendMessage(Format.Style("&fYou has been set &cHeart &fParticle!"));
                        player.closeInventory();
                    }
                }
            }
            else {
                player.sendMessage(Format.Style("&cPurchase it at store.serpentmc.club"));
                player.closeInventory();
                event.setCancelled(true);
            }
        }
    }
}