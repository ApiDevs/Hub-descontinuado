package dev.api.tablist.nms.v1_8_R3;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.mojang.authlib.properties.Property;
import dev.api.tablist.PlayerTablist;
import dev.api.tablist.TabColumn;
import dev.api.tablist.TabEntry;
import dev.api.tablist.nms.TablistNMS;
import dev.api.tablist.playerversion.PlayerVersion;
import dev.api.tablist.playerversion.PlayerVersionHandler;
import dev.api.tablist.skin.Skin;
import dev.api.utils.Format;
import dev.api.utils.LegacyClient;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Tablist_v1_8_R3 implements TablistNMS {
    @Override
    public TabEntry createEntry(PlayerTablist playerTablist, String string, TabColumn column, Integer slot, Integer rawSlot) {
        OfflinePlayer offlinePlayer = new OfflinePlayer() {
            private final UUID uuid = UUID.randomUUID();

            public boolean isOnline() {
                return true;
            }

            public String getName() {
                return string;
            }

            public UUID getUniqueId() {
                return this.uuid;
            }

            public boolean isBanned() {
                return false;
            }

            public void setBanned(boolean b) {
            }

            public boolean isWhitelisted() {
                return false;
            }

            public void setWhitelisted(boolean b) {
            }

            public Player getPlayer() {
                return null;
            }

            public long getFirstPlayed() {
                return 0L;
            }

            public long getLastPlayed() {
                return 0L;
            }

            public long getLastLogin() {
                return 0;
            }

            public long getLastLogout() {
                return 0;
            }


            public boolean hasPlayedBefore() {
                return false;
            }

            public Location getBedSpawnLocation() {
                return null;
            }

            public Map<String, Object> serialize() {
                return null;
            }

            public boolean isOp() {
                return false;
            }

            public void setOp(boolean b) {
            }
        };
        Player player = playerTablist.getPlayer();
        PlayerVersion playerVersion = PlayerVersionHandler.getPlayerVersion(player);
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(1, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        WrappedGameProfile profile = new WrappedGameProfile(offlinePlayer.getUniqueId(), (playerVersion != PlayerVersion.v1_7) ? string : (LegacyClient.ENTRY.get(rawSlot - 1) + ""));
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, 1, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText((playerVersion != PlayerVersion.v1_7) ? "" : profile.getName()));
        if (playerVersion != PlayerVersion.v1_7) {
            playerInfoData.getProfile().getProperties().put("textures", new WrappedSignedProperty("textures", Skin.STEVE.getValue(), Skin.STEVE.getSignature()));
        }
        packet.getPlayerInfoDataLists().write(1, Collections.singletonList(playerInfoData));
        sendPacket(player, packet);
        return new TabEntry(string, offlinePlayer, "", playerTablist, Skin.DEFAULT, column, slot, rawSlot,0);
    }

    @Override
    public TabEntry createFakePlayer(PlayerTablist playerTablist, String string, TabColumn column, Integer slot, Integer rawSlot) {
        return null;
    }


    @Override
    public void updateFakeName(PlayerTablist playerTablist, TabEntry tabEntry, String text) {
        if (tabEntry.getText().equals(text)) return;
        Player player = playerTablist.getPlayer();
        PlayerVersion playerVersion = PlayerVersionHandler.getPlayerVersion(player);
        String[] newStrings = PlayerTablist.splitStrings(text, tabEntry.getRawSlot());
        if (playerVersion == PlayerVersion.v1_7) {
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
        }
        else {
            PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
            packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME);
            WrappedGameProfile profile = new WrappedGameProfile(tabEntry.getOfflinePlayer().getUniqueId(), tabEntry.getId());
            PlayerInfoData playerInfoData = new PlayerInfoData(profile, 1, EnumWrappers.NativeGameMode.NOT_SET,
                    WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', newStrings.length > 1 ? newStrings[0] + newStrings[1] : newStrings[0])));
            packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
            sendPacket(player, packet);
        }
        tabEntry.setText(text);
    }


    @Override
    public void updateLatency(PlayerTablist playerTablist, TabEntry tabEntry, Integer latency) {
        if (tabEntry.getLatency() == latency) return;
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_LATENCY);
        WrappedGameProfile profile = new WrappedGameProfile(tabEntry.getOfflinePlayer().getUniqueId(), tabEntry.getId());
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, latency, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText(Format.translate(tabEntry.getText())));
        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
        sendPacket(playerTablist.getPlayer(), packet);
        tabEntry.setLatency(latency);
    }

    @Override
    public void updateSkin(PlayerTablist playerTablist, TabEntry tabEntry, Skin skin) {

        if (skin == null || tabEntry.getSkin().equals(skin)) return;
        Player player = playerTablist.getPlayer();
        WrappedGameProfile profile = new WrappedGameProfile(tabEntry.getOfflinePlayer().getUniqueId(), tabEntry.getId());
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, 1, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText(Format.translate(tabEntry.getText())));

        playerInfoData.getProfile().getProperties().put("textures", new WrappedSignedProperty("textures", skin.getValue(), skin.getSignature()));

        PacketContainer remove = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        remove.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        remove.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
        PacketContainer add = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        add.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        add.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
        sendPacket(player, remove);
        sendPacket(player, add);
        tabEntry.setSkin(skin);
    }

    @Override
    public void updateHeaderAndFooter(Player player, List<String> header, List<String> footer) {
        PacketContainer headerAndFooter = new PacketContainer(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        headerAndFooter.getChatComponents().write(0, WrappedChatComponent.fromText(this.getListFromString(Format.translate(header))));
        headerAndFooter.getChatComponents().write(1, WrappedChatComponent.fromText(this.getListFromString(Format.translate(footer))));
        sendPacket(player, headerAndFooter);

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

    private static void sendPacket(Player player, PacketContainer packetContainer) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}