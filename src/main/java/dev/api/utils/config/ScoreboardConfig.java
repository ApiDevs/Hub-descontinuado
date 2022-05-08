package dev.api.utils.config;

import dev.api.Hub;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ScoreboardConfig extends YamlConfiguration {

    private File file;
    private static ScoreboardConfig config;

    public ScoreboardConfig() {
        file = new File(Hub.getPlugin().getDataFolder(), "scoreboard.yml");
        if(!file.exists()) Hub.getPlugin().saveResource("scoreboard.yml", false);
        this.reload();
    }

    public static ScoreboardConfig getConfig() {
        if(config == null) {
            config = new ScoreboardConfig();
        }
        return config;
    }

    public void save() {
        try {
            super.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            super.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}