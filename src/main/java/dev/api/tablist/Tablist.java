package dev.api.tablist;

import dev.api.tablist.playerversion.PlayerVersionHandler;
import dev.api.tablist.thread.TablistThread;
import dev.api.tablist.nms.TablistNMS;
import dev.api.tablist.nms.v1_7_R4.Tablist_v1_7_R4;
import dev.api.tablist.nms.v1_8_R3.Tablist_v1_8_R3;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Tablist {


    @Getter
    private static Tablist instance;

    private final JavaPlugin plugin;
    private final TablistProvider provider;
    private final Map<UUID, PlayerTablist> playerTablist;
    private TablistThread tabThread;
    private TablistNMS tabNMS;
    private PlayerVersionHandler playerVersionHandler;

    public Tablist(JavaPlugin plugin, TablistProvider provider) {
        if (plugin == null) {
            throw new RuntimeException("NULL!!!!1");
        }

        instance = this;
        this.plugin = plugin;
        this.provider = provider;
        this.playerTablist = new ConcurrentHashMap<>();
        this.playerVersionHandler = new PlayerVersionHandler();

        this.registerNMS();
        this.setup();
    }

    private void setup() {
        this.plugin.getServer().getPluginManager().registerEvents(new TablistListener(), this.plugin);

        if (this.tabThread != null) {
            this.tabThread.stop();
            this.tabThread = null;
        }

        this.tabThread = new TablistThread(this);
    }

    public void disable() {
        if (this.tabThread != null) {
            this.tabThread.stop();
            this.tabThread = null;
        }
        for (UUID uuid : getPlayerTablist().keySet()) {
            getPlayerTablist().remove(uuid);
        }
    }


    private void registerNMS() {
        String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        if (serverVersion.equalsIgnoreCase("v1_7_R4")) {
            this.tabNMS = new Tablist_v1_7_R4();
            System.out.println("[Tab] Registered NMS with v1.7R4 Tab");
        }else
        if (serverVersion.equalsIgnoreCase("v1_8_R3")) {
            if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {

                this.tabNMS = new Tablist_v1_8_R3();
                System.out.println("[Tab] Registered NMS with 1.8R3 Tab (ProtocolLib)");
            }else{
                System.out.println("[Tab] Unable to register 1.8R3 Tab! Please add ProtocolLib ");
            }
        }
    }
}

