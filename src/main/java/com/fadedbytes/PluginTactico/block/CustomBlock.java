package com.fadedbytes.PluginTactico.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collection;

public enum CustomBlock {

    WISH_BLOCK("wish_block", Material.BARRIER);

    private final String name;
    private final Material type;
    private final Collection<LivingEntity> faces;

    private CustomBlock(String name, Material type) {
        this.name = name;
        this.type = type;
        faces = new ArrayList<LivingEntity>();
    }

    public static void place(Location pos, CustomBlock block) {
        pos.getBlock().setType(block.type);
        pos.getWorld().spawnEntity(pos.add(0,2,0), EntityType.AXOLOTL);
    }

}
