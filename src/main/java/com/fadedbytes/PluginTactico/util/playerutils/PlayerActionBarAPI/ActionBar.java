package com.fadedbytes.PluginTactico.util.playerutils.PlayerActionBarAPI;

import org.bukkit.entity.Player;

public class ActionBar {

    private static final int DEFAULT_TICKS = 100;
    private static final int DEFAULT_TTL = 200;

    final ActionBarPriority PRIORITY;
    int ticksLeft;
    int ttl;

    private final String text;

    ActionBar(ActionBarPriority priority, int time, String text, int ttl) {
        this.PRIORITY = priority;
        this.ticksLeft = time;
        this.text = text;
        this.ttl = time + ttl;
    }

    public static void addActionBar(ActionBarPriority priority, int time, int ttl, String text, Player... players) {
        if (players == null) return;
        if (players.length == 0) return;
        ActionBar actionBar = new ActionBar(priority, time, text, ttl);
        for (Player p : players) {
            ActionBarManager.addActionBar(p, actionBar);
        }
    }

    public static void addActionBar(ActionBarPriority priority, int time, String text, Player... players) {
        addActionBar(priority, time, DEFAULT_TTL, text, players);
    }

    public static void addActionBar(ActionBarPriority priority, String text, Player... players) {
        addActionBar(priority, DEFAULT_TICKS, text, players);
    }

    public static void addActionBar(String text, Player... players) {
        addActionBar(ActionBarPriority.MEDIUM, text, players);
    }

    String getText() {
        return this.text;
    }

}
