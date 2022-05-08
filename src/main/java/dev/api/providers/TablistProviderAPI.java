package dev.api.providers;

import dev.api.Hub;
import dev.api.tablist.skin.Skin;
import dev.api.utils.Format;
import dev.api.tablist.TabColumn;
import dev.api.tablist.TabLayout;
import dev.api.tablist.TablistProvider;
import dev.api.utils.config.TablistConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TablistProviderAPI implements TablistProvider {

    @Override
    public List<String> getHeader(Player player) {

        TabLayout object = new TabLayout();
        String text = "";

        PlaceholderAPI.setPlaceholders(player, text);

        return Format.translate(TablistConfig.getConfig().getStringList("tab.header"));

    }

    @Override
    public List<String> getFooter(Player player) {

        TabLayout object = new TabLayout();
        String text = "";

        PlaceholderAPI.setPlaceholders(player, text);

        return Format.translate(TablistConfig.getConfig().getStringList("tab.footer"));
    }

    public String replaceAll(String text, Player player) {
        if (Hub.getPlugin().isPlaceholderAPI) {
            return PlaceholderAPI.setPlaceholders(player, Format.translate(text
                    .replaceAll("%online_total%", "" + Bukkit.getOnlinePlayers().size())
                    .replaceAll("%player%", player.getName())
                    .replaceAll("%kills%", "" + player.getStatistic(Statistic.PLAYER_KILLS))
                    .replaceAll("%deaths%", "" + player.getStatistic(Statistic.DEATHS)))
            );
        }
        return Format.translate(text
                .replaceAll("%online_total%", "" + Bukkit.getOnlinePlayers().size())
                .replaceAll("%player%", player.getName())
                .replaceAll("%kills%", "" + player.getStatistic(Statistic.PLAYER_KILLS))
                .replaceAll("%deaths%", "" + player.getStatistic(Statistic.DEATHS))
        );
    }

    @Override
    public Set<TabLayout> getProvider(Player player) {
        Set<TabLayout> layoutSet = new HashSet<>();

        for (int i = 1; i < 21; ++i) {
            layoutSet.add(this.getInfo(i, TabColumn.LEFT, player));
        }
        for (int i = 1; i < 21; ++i) {
            layoutSet.add(this.getInfo(i, TabColumn.MIDDLE, player));
        }
        for (int i = 1; i < 21; ++i) {
            layoutSet.add(this.getInfo(i, TabColumn.RIGHT, player));
        }
        for (int i = 1; i < 21; ++i) {
            layoutSet.add(this.getInfo(i, TabColumn.FAR_RIGHT, player));
        }

        return layoutSet;
    }

    private TabLayout getInfo(int slot, TabColumn place, Player p) {
        TabLayout object = new TabLayout();

        String text = "";
        switch (place) {
            case LEFT: {
                if (TablistConfig.getConfig().getString("tab.left." + slot+ ".text") == null ||
                        Objects.equals(TablistConfig.getConfig().getString("tab.left." + slot + ".text"), " ") ||
                        Objects.equals(TablistConfig.getConfig().getString("tab.left." + slot + ".text"), "")) {
                    text = " ";
                    break;
                }
                text = TablistConfig.getConfig().getString("tab.left." + slot+ ".text");
                break;
            }
            case RIGHT: {
                if (TablistConfig.getConfig().getString("tab.right." + slot+ ".text") == null ||
                        Objects.equals(TablistConfig.getConfig().getString("tab.right." + slot + ".text"), " ") ||
                        Objects.equals(TablistConfig.getConfig().getString("tab.right." + slot + ".text"), "")) {
                    text = " ";
                    break;
                }
                text = TablistConfig.getConfig().getString("tab.right." + slot+ ".text");
                break;
            }
            case MIDDLE: {
                if (TablistConfig.getConfig().getString("tab.center." + slot+ ".text") == null ||
                        Objects.equals(TablistConfig.getConfig().getString("tab.center." + slot + ".text"), " ") ||
                        Objects.equals(TablistConfig.getConfig().getString("tab.center." + slot + ".text"), "")) {
                    text = " ";
                    break;
                }
                text = TablistConfig.getConfig().getString("tab.center." + slot+ ".text");
                break;
            }
            case FAR_RIGHT: {
                if (TablistConfig.getConfig().getString("tab.farright." + slot+ ".text") == null ||
                        Objects.equals(TablistConfig.getConfig().getString("tab.farright." + slot + ".text"), " ") ||
                        Objects.equals(TablistConfig.getConfig().getString("tab.farright." + slot + ".text"), "")) {
                    text = " ";
                    break;
                }
                text = TablistConfig.getConfig().getString("tab.farright." + slot+ ".text");
                break;
            }

        }
        object.slot(slot);
        object.column(place);
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {

            object.text(PlaceholderAPI.setPlaceholders(p, text));
        } else {
            object.text(text);

        }
        String skin = "";
        switch (place) {
            case LEFT: {
                if (TablistConfig.getConfig().getString("tab.left." + slot + ".skin") == null || TablistConfig.getConfig().getString("tab.left." + slot + ".skin") == " " || TablistConfig.getConfig().getString("tab.left." + slot + ".skin") == "") {
                    skin = " ";
                    break;
                }
                skin = TablistConfig.getConfig().getString("tab.left." + slot + ".skin");
                break;
            }
            case RIGHT: {
                if (TablistConfig.getConfig().getString("tab.right." + slot + ".skin") == null || TablistConfig.getConfig().getString("tab.right." + slot + ".skin") == " " || TablistConfig.getConfig().getString("tab.right." + slot + ".skin") == "") {
                    skin = " ";
                    break;
                }
                skin = TablistConfig.getConfig().getString("tab.right." + slot + ".skin");
                break;
            }
            case MIDDLE: {
                if (TablistConfig.getConfig().getString("tab.center." + slot + ".skin") == null || TablistConfig.getConfig().getString("tab.center." + slot + ".skin") == " " || TablistConfig.getConfig().getString("tab.center." + slot + ".skin") == "") {
                    skin = " ";
                    break;
                }
                skin = TablistConfig.getConfig().getString("tab.center." + slot + ".skin");
                break;
            }
            case FAR_RIGHT: {
                if (TablistConfig.getConfig().getString("tab.farright." + slot + ".skin") == null || TablistConfig.getConfig().getString("tab.farright." + slot + ".skin") == " " || TablistConfig.getConfig().getString("tab.farright." + slot + ".skin") == "") {
                    skin = " ";
                    break;
                }
                skin = TablistConfig.getConfig().getString("tab.farright." + slot + ".skin");
                break;
            }
        }
        object.skin(skins(p, skin));
        return object;
    }


    public Skin skins(Player player, String skinTab) {
        Skin skinDefault = Skin.DEFAULT;

        if (skinTab.contains("%player%")) {
            skinDefault = Skin.getSkin(player);
        }
        if (skinTab.contains("%discord%")) {
            skinDefault = Skin.DISCORD_SKIN;
        }
        if (skinTab.contains("%youtube%")) {
            skinDefault = Skin.YOUTUBE_SKIN;
        }
        if (skinTab.contains("%twitter%")) {
            skinDefault = Skin.TWITTER_SKIN;
        }
        if (skinTab.contains("%facebook%")) {
            skinDefault = Skin.FACEBOOK_SKIN;
        }
        if (skinTab.contains("%store%")) {
            skinDefault = Skin.STORE_SKIN;
        }
        if (skinTab.contains("%online%")) {
            skinDefault = Skin.ONLINE_SKIN;
        }
        if (skinTab.contains("%offline%")) {
            skinDefault = Skin.OFFLINE_SKIN;
        }
        if (skinTab.contains("%web%")) {
            skinDefault = Skin.WEBSITE_SKIN;
        }
        if (skinTab.contains("%stats%")) {
            skinDefault = Skin.STATS_SKIN;
        }
        if (skinTab.contains("%enderchest%")) {
            skinDefault = Skin.ENDERCHEST_SKIN;
        }
        if (skinTab.contains("%coin%")) {
            skinDefault = Skin.COIN_SKIN;
        }
        if (skinTab.contains("%queue%")) {
            skinDefault = Skin.QUEUE_SKIN;
        }
        if (skinTab.contains("%warning%")) {
            skinDefault = Skin.WARNING_SKIN;
        }
        if (skinTab.contains("%bow%")) {
            skinDefault = Skin.BOW_SKIN;
        }
        if (skinTab.contains("%telegram%")) {
            skinDefault = Skin.TELEGRAM_SKIN;
        }
        if (skinTab.contains("%potion%")) {
            skinDefault = Skin.POTION_SKIN;
        }
        if (skinTab.contains("%earth%")) {
            skinDefault = Skin.EARTH_SKIN;
        }
        if (skinTab.contains("%information%")) {
            skinDefault = Skin.INFORMATION_SKIN;
        }
        if (skinTab.contains("%server%")) {
            skinDefault = Skin.SERVER_SKIN;
        }
        return skinDefault;
    }
}
