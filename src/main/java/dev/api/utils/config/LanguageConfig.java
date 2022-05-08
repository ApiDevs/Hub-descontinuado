package dev.api.utils.config;

import dev.api.Hub;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LanguageConfig extends YamlConfiguration {

    private File file;
    private static LanguageConfig config;

    public LanguageConfig() {
        file = new File(Hub.getPlugin().getDataFolder(), "language.yml");
        if(!file.exists()) Hub.getPlugin().saveResource("language.yml", false);
        this.reload();
    }

    public static LanguageConfig getConfig() {
        if(config == null) {
            config = new LanguageConfig();
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
