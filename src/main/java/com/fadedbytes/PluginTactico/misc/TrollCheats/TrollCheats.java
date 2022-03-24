package com.fadedbytes.PluginTactico.misc.TrollCheats;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class TrollCheats implements Listener {

    public TrollCheats(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    private static final ArrayList<Player> INMORTALS = new ArrayList<Player>();

    @EventHandler
    public static void onPlayerDeath(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (!INMORTALS.contains(player)) {
                return;
            }
            if (player.getHealth() - event.getDamage() < 1) {
                event.setCancelled(true);
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                player.getLocation().getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
            }
        }

    }

    public static void setInmortal(Player player) {
        if (!INMORTALS.contains(player)) {
            INMORTALS.add(player);
        }
    }

    public static void unsetInmortal(Player player) {
        INMORTALS.remove(player);
    }

    public static boolean isInmortal(Player p) {
        return INMORTALS.contains(p);
    }

    public static void switchInmortal(Player p) {
        if (isInmortal(p)) {
            unsetInmortal(p);
        } else {
            setInmortal(p);
        }
    }

}
