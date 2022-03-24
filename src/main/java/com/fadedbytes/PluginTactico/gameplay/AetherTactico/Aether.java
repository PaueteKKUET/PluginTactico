package com.fadedbytes.PluginTactico.gameplay.AetherTactico;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Aether {

    public Aether(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new AetherPortalCreator(), plugin);
        new AetherWorld();
    }

}
