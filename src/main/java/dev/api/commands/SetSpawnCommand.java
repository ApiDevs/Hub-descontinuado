package dev.api.commands;

import dev.api.Hub;
import dev.api.utils.Format;
import dev.api.utils.Util;
import dev.api.utils.config.ConfigFile;
import dev.api.utils.config.LanguageConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    public SetSpawnCommand() {
        Hub.getInstance().getCommand("setspawn").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd  , String label, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("hub.setspawn")) {
            for (String string : LanguageConfig.getConfig().getStringList("no-perms")) {
                Format.sender(player, string);
            }
            return false;
        } else {
            ConfigFile.getConfig().set("spawn.location", Util.locationToString(player.getLocation()));
            ConfigFile.getConfig().save();
            return false;
        }
    }
}
