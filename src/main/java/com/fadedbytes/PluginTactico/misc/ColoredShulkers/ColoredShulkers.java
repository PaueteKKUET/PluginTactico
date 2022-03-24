package com.fadedbytes.PluginTactico.misc.ColoredShulkers;

import com.fadedbytes.PluginTactico.util.DyeTools.DyeTools;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ColoredShulkers implements Listener {

    public ColoredShulkers(JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private static final float COLORED_SHULKER_SPAWN_PROBABILITY = 0.15f;

    @EventHandler
    public static void onShulkerClick(PlayerInteractAtEntityEvent event) {
        Entity shulker = event.getRightClicked();
        if (!shulker.getType().equals(EntityType.SHULKER)) return;
        Player player = event.getPlayer();

        if (event.getHand().equals(EquipmentSlot.HAND)) {
            if (!colorizeShulker((Shulker) shulker, player.getInventory().getItem(event.getHand()), player)) {
                colorizeShulker((Shulker) shulker, player.getInventory().getItem(EquipmentSlot.OFF_HAND), player);
            }
        }
    }

    private static boolean colorizeShulker(Shulker shulker, ItemStack item, Player player) {
        if (item == null || shulker == null) return false;

        DyeColor color = DyeTools.getColor().fromItem(item.getType());
        if (color == null) return false;
        if (color.equals(shulker.getColor())) return true;

        Location pos = shulker.getLocation();

        pos.getWorld().playSound(pos, Sound.ITEM_DYE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);

        Particle.DustOptions particleOptions = new Particle.DustOptions(color.getColor(),1);
        for (int i = 0; i < 20; i++) {
            pos.getWorld().spawnParticle(Particle.REDSTONE, pos.add(Math.random() * 3 - 1.5, Math.random() * 3 - 1.5, Math.random() * 3 - 1.5), 1, particleOptions);
        }

        shulker.setColor(color);
        if (player.getGameMode() != GameMode.CREATIVE) {
            item.setAmount(item.getAmount() - 1);
        }
        return true;
    }

    @EventHandler
    public static void onShulkerSpawn(EntitySpawnEvent event) {

        if (event.getEntity() instanceof Shulker shulker) {
            if (Math.random() <= COLORED_SHULKER_SPAWN_PROBABILITY) {
                shulker.setColor(DyeTools.getColor().randomly());
            }
        }
    }

}
