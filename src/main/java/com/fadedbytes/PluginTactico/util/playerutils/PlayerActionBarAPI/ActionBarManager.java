package com.fadedbytes.PluginTactico.util.playerutils.PlayerActionBarAPI;
import com.fadedbytes.PluginTactico.PluginTactico;
import com.fadedbytes.PluginTactico.util.playerutils.PlayerUtils;
import jline.internal.Nullable;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActionBarManager {

    public static final int LOOP_PERIOD = 1;

    private static HashMap<Player, ArrayList<ActionBar>> INDEX = new HashMap<>();

    static void addActionBar(Player p, ActionBar actionBar) {
        if (p == null || actionBar == null) return;
        getHolder(p).add(actionBar);
    }

    private static ArrayList<ActionBar> getHolder(Player p) {
        if (INDEX.containsKey(p)) {
            return INDEX.get(p);
        }
        ArrayList<ActionBar> actionBars = new ArrayList<>();
        INDEX.put(p, actionBars);
        return actionBars;
    }

    private static void removeActionbarsOfPlayer(Player p) {
        INDEX.remove(p);
    }

    public static void loop() {
        checkTTL();
        checkForEmptyPlayers();
        showActionbars();
    }

    private static void checkForEmptyPlayers() {
        List<Player> emptyPlayers = INDEX
                .keySet()
                .stream()
                .filter(player -> INDEX.get(player).isEmpty())
                .toList();
        for (Player p : emptyPlayers) {
            removeActionbarsOfPlayer(p);
        }
    }

    private static void showActionbars() {
        INDEX
                .keySet()
                .stream()
                .forEach((player) -> {
                    ActionBar actionBar = chooseActionbar(player);
                    if (actionBar == null) return;

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(actionBar.getText()));
                    actionBar.ticksLeft -= LOOP_PERIOD;


                    if (actionBar.ticksLeft <= 0) {
                        INDEX.get(player).remove(actionBar);
                        Bukkit.getScheduler().runTaskLater(PluginTactico.getPlugin(), () -> tryToRemoveActionBar(player), LOOP_PERIOD - actionBar.ticksLeft);
                    }

                });
    }

    private static void tryToRemoveActionBar(Player p) {
        ArrayList<ActionBar> actionbars = INDEX.get(p);
        if (actionbars != null) {
            if (!INDEX.get(p).isEmpty()) {
                return;
            }
        }
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
    }

    @Nullable
    private static ActionBar chooseActionbar(Player p) {
        final ArrayList<ActionBar> candidates = INDEX.get(p);
        if (candidates.size() == 0) return null;

        for (ActionBar actionBar : candidates) {
            actionBar.ttl --;
        }
        return candidates
                .stream()
                .filter((actionBar -> actionBar.ttl >= 0))
                .sorted((a, b) -> Integer.compare(b.PRIORITY.getPriority(), a.PRIORITY.getPriority()))
                .toList()
                .get(0);
    }

    private static void checkTTL() {
        ArrayList<ActionBar> expiredActionbars;
        for (Player p : INDEX.keySet()) {
            expiredActionbars = new ArrayList<>();
            for (ActionBar actionBar : INDEX.get(p)) {
                if (actionBar.ttl <= 0) {
                    expiredActionbars.add(actionBar);
                }
            }
            INDEX.get(p).removeAll(expiredActionbars);
        }
    }
}
