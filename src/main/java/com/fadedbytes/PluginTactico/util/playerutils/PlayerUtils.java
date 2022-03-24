package com.fadedbytes.PluginTactico.util.playerutils;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class PlayerUtils {

    private static final PlayerUtils UTILS = new PlayerUtils();

    private PlayerUtils() {}

    public static boolean checkPermission(Player p, String... permissions) {
        if (p.isOp()) return true;

        boolean can = true;
        for (String permission : permissions) {
            if (!p.hasPermission(permission)) {
                can = false;
            }
        }

        return can && permissions.length > 0;
    }

    public static PlayerUtils getPlayer() {
        return UTILS;
    }

    public Player byName(String name) {
        return Bukkit.getServer().getPlayer(name);
    }

}
