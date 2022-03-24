package com.fadedbytes.PluginTactico.util.DyeTools;

import com.fadedbytes.PluginTactico.Tags.TagTactica;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import java.util.Random;

public final class DyeTools {

    private DyeTools() {};

    private static final DyeTools TOOL = new DyeTools();

    public static DyeTools getColor() {
        return TOOL;
    }

    public DyeColor fromItem(Material item) {
        if (TagTactica.DYES.hasItem(item)) {
            switch (item) {
                case WHITE_DYE -> {
                    return DyeColor.WHITE;
                }
                case ORANGE_DYE -> {
                    return DyeColor.ORANGE;
                }
                case MAGENTA_DYE -> {
                    return DyeColor.MAGENTA;
                }
                case LIGHT_BLUE_DYE -> {
                    return DyeColor.LIGHT_BLUE;
                }
                case YELLOW_DYE -> {
                    return DyeColor.YELLOW;
                }
                case LIME_DYE -> {
                    return DyeColor.LIME;
                }
                case PINK_DYE -> {
                    return DyeColor.PINK;
                }
                case GRAY_DYE -> {
                    return DyeColor.GRAY;
                }
                case LIGHT_GRAY_DYE -> {
                    return DyeColor.LIGHT_GRAY;
                }
                case CYAN_DYE -> {
                    return DyeColor.CYAN;
                }
                case PURPLE_DYE -> {
                    return DyeColor.PURPLE;
                }
                case BLUE_DYE -> {
                    return DyeColor.BLUE;
                }
                case BROWN_DYE -> {
                    return DyeColor.BROWN;
                }
                case RED_DYE -> {
                    return DyeColor.RED;
                }
                case BLACK_DYE -> {
                    return DyeColor.BLACK;
                }
            }
        }
        return null;
    }

    public DyeColor randomly() {
        return this.randomly(new Random().nextLong());
    }

    public DyeColor randomly(long seed) {
        DyeColor[] colors = DyeColor.class.getEnumConstants();
        Random n = new Random(seed);
        return colors[Math.abs(n.nextInt() % colors.length)];
    }

}
