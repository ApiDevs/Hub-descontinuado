package dev.api.tablist.playerversion;

import java.util.Arrays;

public enum PlayerVersion {

    v1_7(4, 5),
    v1_8(47);

    private final Integer[] rawVersion;

    PlayerVersion(Integer... rawVersionNumbers) {
        this.rawVersion = rawVersionNumbers;
    }

    public static PlayerVersion getVersionFromRaw(Integer input) {
        for (PlayerVersion playerVersion : PlayerVersion.values()) {
            if (Arrays.asList(playerVersion.rawVersion).contains(input)) {
                return playerVersion;
            }
        }
        return v1_8;
    }


}
