package com.fadedbytes.PluginTactico.minigames.ColorShuffle;

import com.fadedbytes.PluginTactico.util.worldutils.WorldArea;

public class ColorShuffleGame {

    private final WorldArea AREA;

    public ColorShuffleGame(WorldArea area, String gameCode) {
        this.AREA = area;
        ColorShuffle.GAMES.put(gameCode, this);
    }

}
