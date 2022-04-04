package com.fadedbytes.PluginTactico.util.worldutils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.Iterator;

import static org.bukkit.Axis.*;

public class WorldAreaIterator implements Iterator<Block> {

    private final WorldArea AREA;
    private Vector posVec;

    WorldAreaIterator(WorldArea area) {
        this.AREA = area;
        posVec = new Vector(AREA.getMin(X), AREA.getMin(Y), AREA.getMin(Z));
    }

    @Override
    public boolean hasNext() {
        return AREA.isInside(posVec);
    }

    @Override
    public Block next() {
        Block selectedBlock = AREA.getWorld().getBlockAt(posVec.getBlockX(), posVec.getBlockY(), posVec.getBlockZ());

        posVec.setX(posVec.getBlockX() + 1);
        if (posVec.getBlockX() > AREA.getMax(X)) {
            posVec.setX(AREA.getMin(X));
            posVec.setZ(posVec.getBlockZ() + 1);

            if (posVec.getBlockZ() > AREA.getMax(Z)) {
                posVec.setZ(AREA.getMin(Z));
                posVec.setY(posVec.getBlockY() + 1);
            }
        }

        return selectedBlock;

    }
}
