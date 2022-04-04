package com.fadedbytes.PluginTactico.gameplay.AetherTactico.biomes;

import com.fadedbytes.PluginTactico.gameplay.AetherTactico.AetherWorld;
import com.fadedbytes.PluginTactico.gameplay.AetherTactico.SimplexNoise;
import com.fadedbytes.PluginTactico.worldgen.NoiseGenerators;
import org.bukkit.block.Biome;

import java.util.HashMap;

public class BiomeNoiseGenerator {

    public static final HashMap<Biome, Integer> BIOMES;

    static {
        BIOMES = new HashMap<>();
        BIOMES.put(Biome.THE_END, 40);
        BIOMES.put(Biome.FOREST, 20);
        BIOMES.put(Biome.DESERT, 20);
        BIOMES.put(Biome.TAIGA, 20);
    }

    private static int TOTAL_WEIGHT = 100;



    private static final double BIOME_SIZE = 2048.0;
    private static int total_weight = 0;
    public Biome getBiomeAt(int blockX, int blockZ, int maxHeight) {
        int[] heightMap = AetherWorld.getAttitudeAt(blockX, blockZ, maxHeight);
        if (!AetherWorld.isValidHeigth(heightMap)) {
            return Biome.THE_VOID;
        }
        double biomeMap = (SimplexNoise.noise(blockX / BIOME_SIZE, blockZ / BIOME_SIZE) + 1) / 2 * TOTAL_WEIGHT;

        int acc = 0;
        for (Biome biome : BIOMES.keySet()) {
            acc += BIOMES.get(biome);
            if (biomeMap <= acc) {
                return biome;
            }
        }
        return Biome.TAIGA;
    }

}
