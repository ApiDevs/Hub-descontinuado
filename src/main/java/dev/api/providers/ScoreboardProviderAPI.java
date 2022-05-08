package dev.api.providers;

import dev.api.Hub;
import dev.api.board.AssembleAdapter;
import dev.api.utils.Format;
import dev.api.utils.config.ScoreboardConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardProviderAPI implements AssembleAdapter {
    @Override
    public String getTitle(Player player) {
        return Format.translate(ScoreboardConfig.getConfig().getString("scoreboard.title").replace("|", "\u2503"));
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> toReturn = new ArrayList<>();

        if (Hub.getInstance().getQueueHandler().inQueue(player)) {
            ScoreboardConfig.getConfig().getStringList("scoreboard.queue").stream()
                    .map(Format::translate)
                    .map(line -> Format.t(player, PlaceholderAPI.setPlaceholders(player, line)))
                    .map(line -> line.replace("%server%", String.valueOf(Hub.getInstance().getQueueHandler().getQueueIn(player))))
                    .map(line -> line.replace("%position%", String.valueOf(Hub.getInstance().getQueueHandler().getPosition(player))))
                    .map(line -> line.replace("%size%", String.valueOf(Hub.getInstance().getQueueHandler().getInQueue(Hub.getInstance().getQueueHandler().getQueueIn(player)))))
                    .forEach(toReturn::add);
        } else {
            ScoreboardConfig.getConfig().getStringList("scoreboard.default").stream()
                    .map(Format::translate)
                    .map(line -> Format.t(player, PlaceholderAPI.setPlaceholders(player, line)))
                    .forEach(toReturn::add);
        }
        return toReturn;
    }
}
