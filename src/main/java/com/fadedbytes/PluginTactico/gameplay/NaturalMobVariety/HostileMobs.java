package com.fadedbytes.PluginTactico.gameplay.NaturalMobVariety;

import com.fadedbytes.PluginTactico.util.playerutils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Animals;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HostileMobs implements Listener {

    private static final float ANGER_MOB_PROBABILITY = 1.0f;

    public HostileMobs(JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public static void onPassiveMobSpawn(EntitySpawnEvent event) {
        if (!event.getEntity().getSpawnCategory().equals(SpawnCategory.ANIMAL))
            return;

        PlayerUtils.getPlayer().byName("PaueteKKUET").sendMessage("A mob spawned: " + event.getEntity().getType().getKey().getKey());
        if (Math.random() <= ANGER_MOB_PROBABILITY) {
            if (event.getEntity() instanceof Animals a) {
                // TODO hostilize the mob
            }
        }
    }

}
