package com.fadedbytes.PluginTactico.Tags;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Iterator;

public class TagTactica<T> implements Iterable<T> {

    public static final TagTactica<Material> DYES = new TagTactica<Material>("dyes");
    public static final TagTactica<EntityType> PASSIVE_MOBS = new TagTactica<EntityType>("passive_mobs");
    public static final TagTactica<BlockFace> COLLINDANT_BLOCK_FACES = new TagTactica<BlockFace>("collindant_block_faces");

    static {
        DYES
                .addItem(Material.WHITE_DYE)
                .addItem(Material.ORANGE_DYE)
                .addItem(Material.MAGENTA_DYE)
                .addItem(Material.LIGHT_BLUE_DYE)
                .addItem(Material.YELLOW_DYE)
                .addItem(Material.LIME_DYE)
                .addItem(Material.PINK_DYE)
                .addItem(Material.GRAY_DYE)
                .addItem(Material.LIGHT_GRAY_DYE)
                .addItem(Material.CYAN_DYE)
                .addItem(Material.PURPLE_DYE)
                .addItem(Material.BLUE_DYE)
                .addItem(Material.BROWN_DYE)
                .addItem(Material.GREEN_DYE)
                .addItem(Material.RED_DYE)
                .addItem(Material.BLACK_DYE);

        PASSIVE_MOBS
                .addItem(EntityType.BAT);
                // TODO: add passive mobs

        COLLINDANT_BLOCK_FACES
                .addItem(BlockFace.UP)
                .addItem(BlockFace.DOWN)
                .addItem(BlockFace.NORTH)
                .addItem(BlockFace.SOUTH)
                .addItem(BlockFace.EAST)
                .addItem(BlockFace.WEST);
    }

    final ArrayList<T> ITEMS;

    public TagTactica(String name) {
        this.ITEMS = new ArrayList<T>();
    }

    private TagTactica<T> addItem(T item) {
        if (!this.ITEMS.contains(item)) {
            this.ITEMS.add(item);
        }
        return this;
    }

    public boolean hasItem(T item) {
        return this.ITEMS.contains(item);
    }

    public int size() {
        return ITEMS.size();
    }

    @Override
    public TagIterator<T> iterator() {
        return new TagIterator<T>(this);
    }
}
