package com.fadedbytes.PluginTactico.util.worldutils;

import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

import static org.bukkit.Axis.*;

public class WorldArea implements Iterable<Block> {

    private final Location loc1;
    private final Location loc2;

    public WorldArea(Location loc1, Location loc2) {
        if (!loc1.getWorld().equals(loc2.getWorld()))
            throw new IllegalArgumentException("Areas can not be created through different worlds");
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public int getMin(Axis axis) {
        switch (axis) {
            case X -> {
                return Math.min(loc1.getBlockX(), loc2.getBlockX());
            }
            case Y -> {
                return Math.min(loc1.getBlockY(), loc2.getBlockY());
            }
            case Z -> {
                return Math.min(loc1.getBlockZ(), loc2.getBlockZ());
            }
        }
        return 0;
    }

    public int getMax(Axis axis) {
        switch (axis) {
            case X -> {
                return Math.max(loc1.getBlockX(), loc2.getBlockX());
            }
            case Y -> {
                return Math.max(loc1.getBlockY(), loc2.getBlockY());
            }
            case Z -> {
                return Math.max(loc1.getBlockZ(), loc2.getBlockZ());
            }
        }
        return 0;
    }

    public World getWorld() {
        return loc1.getWorld();
    }

    public boolean isInside(Vector vector) {
        return isInside(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public boolean isInside(int x, int y, int z) {
        return
                (x <= getMax(X) && x >= getMin(X))
                && (y <= getMax(Y) && y >= getMin(Y))
                && (z <= getMax(Z) && z >= getMin(Z));
    }

    public boolean isInside(Location loc) {
        return loc.getWorld().equals(loc1.getWorld()) && isInside(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }


    @NotNull
    @Override
    public Iterator<Block> iterator() {
        return new WorldAreaIterator(this);
    }
}
