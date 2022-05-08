package dev.api.utils;

import dev.api.Hub;
import org.bukkit.plugin.messaging.*;
import java.util.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.net.*;
import java.io.*;

public class Bungee implements PluginMessageListener
{
    private Hub plugin;
    private HashMap<ServerType, Integer> servers;

    public Bungee(final Hub plugin) {
        this.plugin = plugin;
        this.load();
    }

    public void load() {
        this.servers = new HashMap<ServerType, Integer>();
        this.plugin.getServer().getMessenger().registerOutgoingPluginChannel((Plugin)this.plugin, "BungeeCord");
        this.plugin.getServer().getMessenger().registerIncomingPluginChannel((Plugin)this.plugin, "BungeeCord", (PluginMessageListener)this);
        for (final ServerType server : ServerType.values()) {
            this.servers.put(server, 0);
        }
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, (Runnable)new Runnable() {
            @Override
            public void run() {
                for (final ServerType server : ServerType.values()) {
                    try {
                        final ByteArrayOutputStream b = new ByteArrayOutputStream();
                        final DataOutputStream out = new DataOutputStream(b);
                        out.writeUTF("PlayerCount");
                        out.writeUTF(server.getBungeeName());
                    }
                    catch (Exception ex) {}
                }
            }
        }, 20L, 100L);
    }

    public void onPluginMessageReceived(final String channel, final Player player, final byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        try {
            final DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            final String command = in.readUTF();
            if (command.equals("PlayerCount")) {
                final String server = in.readUTF();
                final int playerCount = in.readInt();
                this.servers.put(ServerType.fromBungeeName(server), playerCount);
            }
        }
        catch (Exception ex) {}
    }

    public boolean serverStatus(final String server, final ServerType serverType) {
        try {
            final SocketAddress servers = new InetSocketAddress("127.0.0.1", serverType.getPort());
            final Socket socket = new Socket();
            socket.connect(servers, 1000);
            socket.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Hub getPlugin() {
        return this.plugin;
    }

    public HashMap<ServerType, Integer> getServers() {
        return this.servers;
    }

    public void setPlugin(final Hub plugin) {
        this.plugin = plugin;
    }

    public void setServers(final HashMap<ServerType, Integer> servers) {
        this.servers = servers;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Bungee)) {
            return false;
        }
        final Bungee other = (Bungee)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$plugin = this.getPlugin();
        final Object other$plugin = other.getPlugin();
        Label_0068: {
            if (this$plugin == null) {
                if (other$plugin == null) {
                    break Label_0068;
                }
            }
            else if (this$plugin.equals(other$plugin)) {
                break Label_0068;
            }
            return false;
        }
        final Object this$servers = this.getServers();
        final Object other$servers = other.getServers();
        if (this$servers == null) {
            if (other$servers == null) {
                return true;
            }
        }
        else if (this$servers.equals(other$servers)) {
            return true;
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Bungee;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $plugin = this.getPlugin();
        result = result * 59 + (($plugin == null) ? 43 : $plugin.hashCode());
        final Object $servers = this.getServers();
        result = result * 59 + (($servers == null) ? 43 : $servers.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Bungee(plugin=" + this.getPlugin() + ", servers=" + this.getServers() + ")";
    }

    public enum ServerType
    {
        GLOBAL("GLOBAL", "ALL", 0),
        Kits("Kits", "Kits", 4),
        HCF("HCF", "HCF", 5),
        Practice("Practice", "Practice", 16);

        private String name;
        private String bungeeName;
        private int port;

        private ServerType(final String name, final String bungeeName, final int port) {
            this.name = name;
            this.bungeeName = bungeeName;
            this.port = port;
        }

        public static ServerType fromBungeeName(final String string) {
            for (final ServerType serverType : values()) {
                if (serverType.getBungeeName().equalsIgnoreCase(string)) {
                    return serverType;
                }
            }
            return null;
        }

        public static ServerType fromName(final String string) {
            for (final ServerType serverType : values()) {
                if (serverType.getName().equalsIgnoreCase(string)) {
                    return serverType;
                }
            }
            return null;
        }

        public static boolean isOnline(final ServerType serverType) {
            try {
                final Socket s = new Socket("127.0.0.1", serverType.getPort());
                s.close();
                return true;
            }
            catch (UnknownHostException e) {
                return false;
            }
            catch (IOException e2) {
                return false;
            }
        }

        public static boolean isWhitelisted(final ServerType type) {
            return false;
        }

        public String getName() {
            return this.name;
        }

        public String getBungeeName() {
            return this.bungeeName;
        }

        public int getPort() {
            return this.port;
        }
    }
}
