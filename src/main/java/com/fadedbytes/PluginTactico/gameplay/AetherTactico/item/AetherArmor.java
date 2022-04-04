package com.fadedbytes.PluginTactico.gameplay.AetherTactico.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class AetherArmor {

    private static ItemStack ESCAFANDRA;
    private static ItemStack BOTAS;


    private static void initEscafandra() {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.randomUUID()));
    }


}
