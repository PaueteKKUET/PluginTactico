package com.fadedbytes.PluginTactico.gameplay.AetherTactico;

import com.fadedbytes.PluginTactico.PluginTactico;
import com.fadedbytes.PluginTactico.Tags.TagTactica;
import com.fadedbytes.PluginTactico.util.playerutils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AetherAmbients {

    public static final int LOOP_PERIOD = 20;

    private static final PotionEffect GRAVITY_EFFECT = new PotionEffect(PotionEffectType.SLOW_FALLING, 25, 0, false, false, false);
    private static final PotionEffect LOW_GRAVITY_JUMP_EFFECT = new PotionEffect(PotionEffectType.JUMP, 25, 0, false, false, false);
    public static final int MAX_OVERWORLD_BLOCK = 750;
    public static final int MIN_AETHER_BLOCK = 0;

    public static void loop() {
        applyLowGravity();
        teleportIntoAether();
        teleportIntoOverworld();
    }

    private static void applyLowGravity() {
        Bukkit.getWorld(AetherWorld.WORLD_NAME).getPlayers().forEach((player) -> {
            if (!player.isGliding() && player.getWorld().getName().equals(AetherWorld.WORLD_NAME)) {
                player.addPotionEffect(GRAVITY_EFFECT);
                player.addPotionEffect(LOW_GRAVITY_JUMP_EFFECT);
            }
        });
    }

    private static final World OVERWORLD    = Bukkit.getWorld("world");
    private static final World AETHER       = Bukkit.getWorld(AetherWorld.WORLD_NAME);

    private static final int TELEPORT_OFFSET = 20;

    private static final PotionEffect TELEPORT_LEVITATION = new PotionEffect(PotionEffectType.LEVITATION, 300, 1, false, false, false);
    private static final PotionEffect PORTAL_INVISIBILITY = new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, false, false, false);
    private static final PotionEffect PORTAL_GLOWING = new PotionEffect(PotionEffectType.GLOWING, 100, 0, false, false, false);

    private static void teleportIntoAether() {
        OVERWORLD.getPlayers().forEach((player -> {
            Location loc = player.getLocation();
            if (loc.getY() > MAX_OVERWORLD_BLOCK + TELEPORT_OFFSET) {
                if (loc.getY() > AetherPortalCreator.PORTAL_TELEPORT_Y - 100) {
                    player.teleport(AetherPortalCreator.getNearestSpawn(player.getLocation().getBlockX(), player.getLocation().getBlockZ()));
                    player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 1.0f, 2.0f);
                    player.addPotionEffect(PORTAL_INVISIBILITY);
                    player.addPotionEffect(PORTAL_GLOWING);
                    player.setGliding(true);
                } else {
                    player.teleport(new Location(AETHER, loc.getX(), MIN_AETHER_BLOCK, loc.getBlockZ(), loc.getYaw(), loc.getPitch()));
                    player.addPotionEffect(TELEPORT_LEVITATION);
                }
            }
        }));
    }

    private static void teleportIntoOverworld() {
        AETHER.getPlayers().forEach((player -> {
            Location loc = player.getLocation();
            if (loc.getY() < MIN_AETHER_BLOCK - TELEPORT_OFFSET) {
                player.teleport(new Location(OVERWORLD, loc.getX(), MAX_OVERWORLD_BLOCK, loc.getBlockZ(), loc.getYaw(), loc.getPitch()));
            }
        }));
    }

}
