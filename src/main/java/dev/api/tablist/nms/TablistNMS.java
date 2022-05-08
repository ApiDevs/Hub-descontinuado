package dev.api.tablist.nms;

import dev.api.tablist.PlayerTablist;
import dev.api.tablist.TabColumn;
import dev.api.tablist.TabEntry;
import dev.api.tablist.skin.Skin;
import org.bukkit.entity.Player;

import java.util.List;

public interface TablistNMS {

    TabEntry createEntry(
            PlayerTablist playerTablist, String string, TabColumn column, Integer slot, Integer rawSlot
    );

    TabEntry createFakePlayer(PlayerTablist playerTablist, String string, TabColumn column, Integer slot, Integer rawSlot);

    void updateFakeName(
            PlayerTablist playerTablist, TabEntry tabEntry, String text
    );

    void updateLatency(PlayerTablist playerTablist, TabEntry tabEntry, Integer latency);

    void updateSkin(
            PlayerTablist playerTablist, TabEntry tabEntry, Skin skin
    );

    void updateHeaderAndFooter(
            Player player, List<String> header, List<String> footer
    );

    Skin getSkin(
            Player player);
}
