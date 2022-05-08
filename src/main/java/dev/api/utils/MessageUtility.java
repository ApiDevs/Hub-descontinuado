package dev.api.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public class MessageUtility {
    public static String formatMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> formatMessages(List<String> messages) {
        List<String> buffered = new ArrayList<>();
        for (String message : messages)
            buffered.add(formatMessage("&r" + message));
        return buffered;
    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0)
            return original;
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
