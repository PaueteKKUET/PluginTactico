package com.fadedbytes.PluginTactico.util.worldutils;

import com.fadedbytes.PluginTactico.util.playerutils.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil {

    static Player PAUETE = PlayerUtils.getPlayer().byName("PaueteKKUET");


    public static boolean isWaterOrWaterlogged(Block block, Player p) {

        boolean isWater = block.getBlockData().getMaterial().equals(Material.WATER);
        if (block.getBlockData() instanceof Waterlogged wl) {
            return wl.isWaterlogged() || isWater;
        }
        return isWater;
    }

    public static void fill(final Location loc1, final Location loc2, final Material block) {
        for (Location position : selectArea(loc1, loc2)) {
            position.getBlock().setType(block);
        }
    }
    public static boolean isFullOfAir(Location loc1, Location loc2) {
        for (Location position : selectArea(loc1, loc2)) {
            if (!position.getBlock().getType().equals(Material.AIR)) {
                return false;
            }
        }
        return true;
    }

    public static List<Location> selectArea(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null) return null;
        int stepX, stepY, stepZ;
        ArrayList<Location> blocks = new ArrayList<>();
        Location varLoc = loc1.clone();


        stepX = Integer.compare(loc2.getBlockX(), varLoc.getBlockX());
        stepY = Integer.compare(loc2.getBlockY(), varLoc.getBlockY());
        stepZ = Integer.compare(loc2.getBlockZ(), varLoc.getBlockZ());
        do {
            do {
                do {
                    blocks.add(varLoc.clone());
                    varLoc.add(stepX, 0, 0);
                } while (varLoc.clone().add(-1 * stepX, 0, 0).getBlockX() != loc2.getBlockX());
                varLoc.setX(loc1.getBlockX());
                varLoc.add(0, stepY, 0);
            } while (varLoc.clone().add(0, -1 * stepY, 0).getBlockY() != loc2.getBlockY());
            varLoc.setY(loc1.getBlockY());
            varLoc.add(0, 0, stepZ);
        } while (varLoc.clone().add(0, 0, -1 * stepZ).getBlockZ() != loc2.getBlockZ());
        return blocks;
    }

}
