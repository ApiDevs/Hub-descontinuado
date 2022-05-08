package dev.api.tablist;

import dev.api.tablist.skin.Skin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TabLayout {

    private TabColumn column = TabColumn.LEFT;
    private int ping = 0;
    private int slot = 1;
    private String text = "";
    private Skin skin = Skin.DEFAULT;


    public TabLayout text(String text) {
        this.text = text;
        return this;
    }

    public TabLayout skin(Skin skin) {
        this.skin = skin;
        return this;
    }

    public TabLayout slot(Integer slot) {
        this.slot = slot;
        return this;
    }

    public TabLayout ping(Integer ping) {
        this.ping = ping;
        return this;
    }

    public TabLayout column(TabColumn tabColumn) {
        this.column = tabColumn;
        return this;
    }

}