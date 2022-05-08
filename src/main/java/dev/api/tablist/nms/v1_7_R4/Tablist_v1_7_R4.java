package dev.api.tablist.nms.v1_7_R4;

import dev.api.tablist.PlayerTablist;
import dev.api.tablist.TabColumn;
import dev.api.tablist.TabEntry;
import dev.api.tablist.nms.TablistNMS;
import dev.api.tablist.playerversion.PlayerVersion;
import dev.api.tablist.playerversion.PlayerVersionHandler;
import dev.api.tablist.skin.Skin;
import dev.api.utils.Format;
import dev.api.utils.LegacyClient;
import net.minecraft.server.v1_7_R4.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.spigotmc.ProtocolInjector;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Tablist_v1_7_R4 implements TablistNMS {

    private static final MinecraftServer server = MinecraftServer.getServer();
    private static final WorldServer world = server.getWorldServer(0);
    private static final PlayerInteractManager manager = new PlayerInteractManager(world);



    @Override
    public TabEntry createEntry(PlayerTablist playerTablist, String string, TabColumn column, Integer slot, Integer rawSlot) {
        final OfflinePlayer offlinePlayer = new OfflinePlayer() {
            private final UUID uuid = UUID.randomUUID();

            @Override
            public boolean isOnline() {
                return true;
            }

            @Override
            public String getName() {
                return string;
            }

            @Override
            public UUID getUniqueId() {
                return uuid;
            }

            @Override
            public boolean isBanned() {
                return false;
            }

            @Override
            public void setBanned(boolean b) {

            }

            @Override
            public boolean isWhitelisted() {
                return false;
            }

            @Override
            public void setWhitelisted(boolean b) {

            }

            @Override
            public Player getPlayer() {
                return null;
            }

            @Override
            public long getFirstPlayed() {
                return 0;
            }

            @Override
            public long getLastPlayed() {
                return 0;
            }


            @Override
            public boolean hasPlayedBefore() {
                return false;
            }

            @Override
            public Location getBedSpawnLocation() {
                return null;
            }

            @Override
            public Map<String, Object> serialize() {
                return null;
            }

            @Override
            public boolean isOp() {
                return false;
            }

            @Override
            public void setOp(boolean b) {

            }
        };
        final Player player = playerTablist.getPlayer();
        final PlayerVersion playerVersion = PlayerVersionHandler.getPlayerVersion(player);

        GameProfile profile = new GameProfile(offlinePlayer.getUniqueId(), LegacyClient.ENTRY.get(rawSlot - 1) + "");
        EntityPlayer entity = new EntityPlayer(server,
                world, profile, manager);

        if (playerVersion != PlayerVersion.v1_7) {
            profile.getProperties().put("textures", new Property("textures", Skin.DEFAULT.getValue(), Skin.DEFAULT.getSignature()));
        }
        entity.ping = 1;

        sendPacket(playerTablist.getPlayer(),

                PacketPlayOutPlayerInfo.addPlayer(entity));

        return new TabEntry(string, offlinePlayer, "", playerTablist, Skin.DEFAULT, column, slot, rawSlot, 1);
    }

    @Override
    public TabEntry createFakePlayer(PlayerTablist playerTablist, String string, TabColumn column, Integer slot, Integer rawSlot) {
        return null;
    }

    @Override
    public void updateFakeName(PlayerTablist playerTablist, TabEntry tabEntry, String text) {
        if (tabEntry.getText().equals(text)) return;


        Player player = playerTablist.getPlayer();
        String[] newStrings = PlayerTablist.splitStrings(text, tabEntry.getRawSlot());
        Team team = player.getScoreboard().getTeam(LegacyClient.NAMES.get(tabEntry.getRawSlot() - 1));
        if (team == null) {
            team = player.getScoreboard().registerNewTeam(LegacyClient.NAMES.get(tabEntry.getRawSlot() - 1));
        }
        team.setPrefix(ChatColor.translateAlternateColorCodes('&', newStrings[0]));
        if (newStrings.length > 1) {
            team.setSuffix(ChatColor.translateAlternateColorCodes('&', newStrings[1]));
        }
        else {
            team.setSuffix("");
        }

        tabEntry.setText(text);
    }

    @Override
    public void updateLatency(PlayerTablist playerTablist, TabEntry tabEntry, Integer latency) {
        if (tabEntry.getLatency() == latency) return;


        GameProfile profile = new GameProfile(
                tabEntry.getOfflinePlayer().getUniqueId(),
                LegacyClient.ENTRY.get(tabEntry.getRawSlot() - 1) + "");
        EntityPlayer entity = new EntityPlayer(server, world, profile, manager);
        entity.ping = latency;

        sendPacket(playerTablist.getPlayer(), PacketPlayOutPlayerInfo.updatePing(entity));
        tabEntry.setLatency(latency);
    }

    @Override
    public void updateSkin(PlayerTablist playerTablist, TabEntry tabEntry, Skin skin) {
        if (skin == null || tabEntry.getSkin().equals(skin)) return;

        GameProfile profile = new GameProfile(tabEntry.getOfflinePlayer().getUniqueId(),
                LegacyClient.ENTRY.get(tabEntry.getRawSlot() - 1) + "");
        EntityPlayer entity = new EntityPlayer(Tablist_v1_7_R4.server, Tablist_v1_7_R4.world, profile, Tablist_v1_7_R4.manager);

        profile.getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));

        sendPacket(playerTablist.getPlayer(), PacketPlayOutPlayerInfo.removePlayer(entity));
        sendPacket(playerTablist.getPlayer(), PacketPlayOutPlayerInfo.addPlayer(entity));

        tabEntry.setSkin(skin);
    }

    @Override
    public void updateHeaderAndFooter(Player player, List<String> header, List<String> footer) {
        IChatBaseComponent headerComponent = ChatSerializer.a("{text:\"" + StringEscapeUtils.escapeJava(this.getListFromString(Format.translate(header)))  + "\"}");
        IChatBaseComponent footerComponent = ChatSerializer.a("{text:\"" + StringEscapeUtils.escapeJava(this.getListFromString(Format.translate(footer))) + "\"}");
        ProtocolInjector.PacketTabHeader packetTabHeader = new ProtocolInjector.PacketTabHeader(headerComponent, footerComponent);
        sendPacket(player, packetTabHeader);
    }
    public String getListFromString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); ++i) {
            stringBuilder.append(list.get(i));
            if (i != list.size() - 1) {
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public Skin getSkin(Player player) {
        if (Skin.CACHE.containsKey(player.getUniqueId())) return Skin.CACHE.get(player.getUniqueId());

        try {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            EntityPlayer entityPlayer = craftPlayer.getHandle();

            Property property = entityPlayer.getProfile().getProperties().get("textures").stream().findFirst().orElse(null);

            if (property != null) {
                return Skin.CACHE.put(player.getUniqueId(), new Skin(property.getValue(), property.getSignature()));
            }
        } catch (Exception ignored) {
            // ignored
        }

        return Skin.STEVE;
    }


    private void sendPacket(Player player, Packet packet) {
        getEntity(player).playerConnection.sendPacket(packet);
    }

    private EntityPlayer getEntity(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

}

