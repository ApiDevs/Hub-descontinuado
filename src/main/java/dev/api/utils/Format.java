package dev.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Format {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List lore(List<String> input) {
        return (List)input.stream().map(Format::set).collect(Collectors.toList());
    }

    public static List<String> translate(List<String> s) {
        return s.stream().map(Format::translate).collect(Collectors.toList());
    }
    public static String set(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }


    public static void sender(CommandSender sender, String in) {
        Player player = (Player) sender;
        double tps = Bukkit.spigot().getTPS()[0];
        tps = Math.min(tps, 20.0);
        double finalTps = tps;
    }


    public static String t(Player player, String input) {
        double tps = Bukkit.spigot().getTPS()[0];
        tps = Math.min(tps, 20.0);
        double finalTps = tps;
        ChatColor colour = (tps >= 18.0) ? ChatColor.GREEN : ((tps >= 13.0) ? ChatColor.YELLOW : ChatColor.RED);

        return input;
    }

    public static String Style(String s) {
        return s;
    }
}
