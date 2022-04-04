package com.fadedbytes.PluginTactico.worldgen;

import com.fadedbytes.PluginTactico.gameplay.AetherTactico.SimplexNoise;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;

public class NoiseGenerators {

    /**
     * Generates floating islands intersecting two simplex noise maps.
     * @param attitude the general attitude of the islands. The higher value, the more flat.
     * @param softness the softness of the islands. The higher value, the softer surface
     * @param steep The number of blocks under the islands, aka offset
    **/
    public static int[] noiseIntersection(double attitude, double softness, double steep, double airAttitude, double airSoftness, double size, int x, int z, int worldMaxHeight) {
        int bottomY, topY;

        topY = (int) ((SimplexNoise.noise(x / softness, z / softness) * (worldMaxHeight / attitude)) + worldMaxHeight / attitude + steep);
        bottomY = (int) ((SimplexNoise.noise(x / airSoftness, z / airSoftness) * (worldMaxHeight /  airAttitude)) + worldMaxHeight / airAttitude + steep - size);
        return new int[] { bottomY, topY };
    }

    /**
     * Generates a simple noise map
     * @param attitude the general attitude of the islands. The higher value, the more flat.
     * @param softness the softness of the islands. The higher value, the softer surface
     * @param steep The number of blocks under the islands, aka offset
     */
    public static int simpleNoise(double attitude, double softness, double steep, int x, int z, int worldMaxHeight) {

        return (int) ((SimplexNoise.noise(x / softness, z / softness) * (worldMaxHeight / attitude)) + worldMaxHeight / attitude + steep);
    }

}
