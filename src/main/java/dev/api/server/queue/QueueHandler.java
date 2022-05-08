package dev.api.server.queue;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.api.Hub;
import dev.api.utils.config.ConfigFile;
import me.signatured.ezqueueshared.QueueInfo;
import me.signatured.ezqueuespigot.EzQueueAPI;
import org.bukkit.entity.Player;

public class QueueHandler {

    public Queue getQueueSupport() {
        switch (ConfigFile.getConfig().getString("system.queue")) {
            case "EZQUEUE":
                return Queue.EZQUEUE;
            default:
                return Queue.NONE;
        }
    }

    public boolean inQueue(Player player) {
        switch (this.getQueueSupport()) {
            case EZQUEUE:
                return QueueInfo.getQueueInfo(EzQueueAPI.getQueue(player)) != null;
            default:
                return false;
        }
    }

    public void sendPlayer(Player player, String server) {
        switch (this.getQueueSupport()) {
            case EZQUEUE:
                EzQueueAPI.addToQueue(player, server);
                break;
            default:
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(server);
                player.getPlayer().sendPluginMessage(Hub.getPlugin(), "BungeeCord", out.toByteArray());
                break;
        }
    }

    public String getQueueIn(Player player) {
        switch (this.getQueueSupport()) {
            case EZQUEUE:
                return EzQueueAPI.getQueue(player);
            default:
                return "NoInQueue";
        }
    }

    public int getPosition(Player player) {
        switch (this.getQueueSupport()) {
            case EZQUEUE:
                return EzQueueAPI.getPosition(player);
            default:
                return 0;
        }
    }

    public int getInQueue(String queue) {
        switch (this.getQueueSupport()) {
            case EZQUEUE:
                return QueueInfo.getQueueInfo(queue).getPlayersInQueue().size();
            default:
                return 0;
        }
    }

}
