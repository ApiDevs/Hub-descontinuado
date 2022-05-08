package dev.api.tablist;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public interface TablistProvider {

    Set<TabLayout> getProvider(Player player);

    List<String> getFooter(Player player);

    List<String> getHeader(Player player);

}
