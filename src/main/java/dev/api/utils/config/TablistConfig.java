package dev.api.utils.config;

import dev.api.Hub;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TablistConfig extends YamlConfiguration {

    private File file;
    private static TablistConfig config;

    public TablistConfig() {
        file = new File(Hub.getPlugin().getDataFolder(), "tablist.yml");
        if(!file.exists()) Hub.getPlugin().saveResource("tablist.yml", false);
        this.reload();
    }

    public static TablistConfig getConfig() {
        if(config == null) {
            config = new TablistConfig();
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
