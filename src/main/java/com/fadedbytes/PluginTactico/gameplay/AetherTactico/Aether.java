package com.fadedbytes.PluginTactico.gameplay.AetherTactico;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Aether {

    public static World AETHER;

    public Aether(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new AetherPortalCreator(), plugin);
        new AetherWorld();
        AETHER = Bukkit.getWorld(AetherWorld.WORLD_NAME);
    }

}
