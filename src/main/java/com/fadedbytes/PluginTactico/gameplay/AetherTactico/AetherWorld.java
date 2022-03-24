package com.fadedbytes.PluginTactico.gameplay.AetherTactico;

import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AetherWorld {

    public static String WORLD_NAME = "TEST_20";

    public AetherWorld() {
        Bukkit.getServer().getWorlds().add(getAetherWorld());
    }


    private static World getAetherWorld() {
        WorldCreator creator = new WorldCreator(WORLD_NAME);
        BiomeProvider provider = getBiomeProvider();

        return creator
                .biomeProvider(getBiomeProvider())
                .generateStructures(true)
                .generator(getChunkGenerator())
                .createWorld();
    }

    private static BiomeProvider getBiomeProvider() {
        final List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.THE_END);
        return new BiomeProvider() {
            @NotNull
            @Override
            public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
                return biomes.get(0);
            }

            @NotNull
            @Override
            public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
                return biomes;
            }
        };
    }

    private static ChunkGenerator getChunkGenerator() {

        return generator;
    }
}
