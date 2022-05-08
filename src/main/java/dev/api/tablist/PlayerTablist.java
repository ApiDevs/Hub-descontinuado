package dev.api.tablist;

import dev.api.tablist.playerversion.PlayerVersion;
import dev.api.tablist.playerversion.PlayerVersionHandler;
import dev.api.tablist.skin.Skin;
import dev.api.utils.LegacyClient;
import dev.api.tablist.nms.v1_7_R4.Tablist_v1_7_R4;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class PlayerTablist {

    private final Player player;
    private Scoreboard scoreboard;
    private int lastHeaderFooter;

    private final Set<TabEntry> currentEntrySet = new HashSet<>();

    public PlayerTablist(Player player) {
        this.player = player;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        if (!this.player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            scoreboard = player.getScoreboard();
        }

        player.setScoreboard(scoreboard);

        this.setup();
        this.setupTeams(player);
    }


    public void setupTeams(Player player){
        Team team1 = player.getScoreboard().getTeam("\\u000181");
        if (team1 == null) {
            team1 = player.getScoreboard().registerNewTeam("\\u000181");
        }
        team1.addEntry(player.getName());
        for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
            Team team = onlinePlayers.getScoreboard().getTeam("\\u000181");
            if (team == null) {
                team = onlinePlayers.getScoreboard().registerNewTeam("\\u000181");
            }
            team.addEntry(player.getName());
            team.addEntry(onlinePlayers.getName());
            team1.addEntry(onlinePlayers.getName());
            team1.addEntry(player.getName());
        }
    }

    public static String[] splitStrings(String text, int rawSlot) {
        if (text.length() > 16) {
            String prefix = text.substring(0, 16);
            String suffix;

            if (prefix.charAt(15) == ChatColor.COLOR_CHAR || prefix.charAt(15) == '&') {
                prefix = prefix.substring(0, 15);
                suffix = text.substring(15);
            } else if (prefix.charAt(14) == ChatColor.COLOR_CHAR || prefix.charAt(14) == '&') {
                prefix = prefix.substring(0, 14);
                suffix = text.substring(14);
            } else {
                suffix = ChatColor.getLastColors(ChatColor.translateAlternateColorCodes('&', prefix)) + text.substring(16);
            }

            if (suffix.length() > 16) {
                suffix = suffix.substring(0, 16);
            }

            return new String[]{
                    prefix,
                    suffix
            };
        } else {
            return new String[]{
                    text
            };
        }
    }

    private void setup() {
        final int possibleSlots = (PlayerVersionHandler.getPlayerVersion(player) == PlayerVersion.v1_7 ? 60 : 80);
        for (int i = 1; i <= possibleSlots; i++) {
            final TabColumn tabColumn = TabColumn.getColumn(player, i);
            if (tabColumn == null) {
                continue;
            }
            TabEntry tabEntry = Tablist.getInstance().getTabNMS().createEntry(
                    this,
                    "0" + (i > 9 ? i : "0" + i) + "|Tab",
                    tabColumn,
                    tabColumn.getSlot(player, i),
                    i
            );
            if (Bukkit.getPluginManager().getPlugin("Featherboard") == null && (PlayerVersionHandler.getPlayerVersion(player) ==
                    PlayerVersion.v1_7 || Tablist.getInstance().getTabNMS() instanceof Tablist_v1_7_R4)) {
                Team team = player.getScoreboard().getTeam(LegacyClient.NAMES.get(i - 1));
                if (team != null) {
                    team.unregister();
                }
                team = player.getScoreboard().registerNewTeam(LegacyClient.NAMES.get(i - 1));
                team.setPrefix("");
                team.setSuffix("");

                team.addEntry(LegacyClient.ENTRY.get(i - 1));

            }
            currentEntrySet.add(tabEntry);
        }
        if (Bukkit.getPluginManager().getPlugin("Featherboard") != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 1; i <= possibleSlots; i++) {
                        if (PlayerVersionHandler.getPlayerVersion(player) == PlayerVersion.v1_7 || Tablist.getInstance().getTabNMS() instanceof Tablist_v1_7_R4) {
                            Team team = player.getScoreboard().getTeam(LegacyClient.NAMES.get(i - 1));
                            if (team != null) {
                                team.unregister();
                            }
                            team = player.getScoreboard().registerNewTeam(LegacyClient.NAMES.get(i - 1));
                            team.setPrefix("");
                            team.setSuffix("");

                            team.addEntry(LegacyClient.ENTRY.get(i - 1));

                        }
                    }
                }
            }.runTaskLater(Tablist.getInstance().getPlugin(), 40);
        }

    }

    public void update() {
        if (PlayerVersionHandler.getPlayerVersion(player) != PlayerVersion.v1_7) {
            List<String> header = Tablist.getInstance().getProvider().getHeader(player);
            List<String> footer = Tablist.getInstance().getProvider().getFooter(player);

            int headerFooter = header.hashCode() + footer.hashCode();

            if (headerFooter != lastHeaderFooter) {
                lastHeaderFooter = headerFooter;
                Tablist.getInstance().getTabNMS().updateHeaderAndFooter(player, header, footer);
            }
        }


        Set<TabEntry> lastSet = new HashSet<>(currentEntrySet);
        for (TabLayout layout : Tablist.getInstance().getProvider().getProvider(player)) {
            TabEntry tabEntry = getEntry(layout.getColumn(), layout.getSlot());
            if (tabEntry != null) {
                lastSet.remove(tabEntry);
                Tablist.getInstance().getTabNMS().updateFakeName(this, tabEntry, layout.getText());
                Tablist.getInstance().getTabNMS().updateLatency(this, tabEntry, layout.getPing());
                if (PlayerVersionHandler.getPlayerVersion(this.player) == PlayerVersion.v1_7) {
                    continue;
                }
                Tablist.getInstance().getTabNMS().updateSkin(this, tabEntry, layout.getSkin());

            }
        }
        for (TabEntry tabEntry : lastSet) {
            Tablist.getInstance().getTabNMS().updateFakeName(this, tabEntry, "");
            Tablist.getInstance().getTabNMS().updateLatency(this, tabEntry, 0);
            if (PlayerVersionHandler.getPlayerVersion(this.player) != PlayerVersion.v1_7) {
                Tablist.getInstance().getTabNMS().updateSkin(this, tabEntry, Skin.DEFAULT);
            }
        }
        lastSet.clear();
    }

    public TabEntry getEntry(TabColumn column, Integer slot) {
        return currentEntrySet.stream().filter(entry -> entry.getColumn() == column && entry.getSlot() == slot).findFirst().orElse(null);
    }
}