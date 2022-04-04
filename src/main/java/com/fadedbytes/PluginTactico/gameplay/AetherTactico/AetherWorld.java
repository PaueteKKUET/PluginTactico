package com.fadedbytes.PluginTactico.gameplay.AetherTactico;

import com.fadedbytes.PluginTactico.gameplay.AetherTactico.biomes.BiomeNoiseGenerator;
import com.fadedbytes.PluginTactico.gameplay.AetherTactico.biomes.DefaultAetherPopulator;
import com.fadedbytes.PluginTactico.gameplay.AetherTactico.biomes.TestBiomeWorld;
import com.fadedbytes.PluginTactico.worldgen.NoiseGenerators;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AetherWorld {

    public static String WORLD_NAME = "TEST_73"; // Último: 89

    public AetherWorld() {
        World aether = getAetherWorld();
        Bukkit.getServer().getWorlds().add(aether);

        //Bukkit.getServer().getWorlds().add(TestBiomeWorld.getWorld());
    }


    private static World getAetherWorld() {
        WorldCreator creator = new WorldCreator(WORLD_NAME);
        BiomeProvider provider = getBiomeProvider();

        return creator
                .biomeProvider(getBiomeProvider())
                .generateStructures(true)
                .generator(getChunkGenerator())
                .environment(getEnvironment())
                .seed(Bukkit.getServer().getWorlds().get(0).getSeed())
                .createWorld();
    }

    private static BiomeProvider getBiomeProvider() {
        final List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.THE_END);
        return new BiomeProvider() {
            private BiomeNoiseGenerator biomeGenerator = new BiomeNoiseGenerator();
            @NotNull
            @Override
            public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
                return Biome.THE_END;
                // return biomeGenerator.getBiomeAt(x, z, worldInfo.getMaxHeight());
            }

            @NotNull
            @Override
            public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
                return Arrays.asList(BiomeNoiseGenerator.BIOMES.keySet().toArray(new Biome[0]));
            }
        };
    }

    private static ChunkGenerator getChunkGenerator() {

        return new ChunkGenerator() {
            @Override
            public boolean shouldGenerateNoise() {
                return false;
            }

            @Override
            public boolean shouldGenerateSurface() {
                return false;
            }

            @Override
            public boolean shouldGenerateBedrock() {
                return false;
            }

            @Override
            public boolean shouldGenerateCaves() {
                return false;
            }

            @Override
            public boolean shouldGenerateDecorations() {
                return false;
            }

            @Override
            public boolean shouldGenerateMobs() {
                return false;
            }

            @Override
            public boolean shouldGenerateStructures() {
                return false;
            }

            @NotNull
            @Override
            public List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
                return Arrays.asList(
                        new DefaultAetherPopulator()
                );
            }

            @Override
            public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull ChunkGenerator.ChunkData chunkData) {

                for (int bx = 0; bx < 16; bx++) {
                    for (int bz = 0; bz < 16; bz ++) {
                        int[] heightPoints = getAttitudeAt(
                                (x << 4) + bx,
                                (z << 4) + bz,
                                chunkData.getMaxHeight()
                        );

                        if (!isValidHeigth(heightPoints)) continue;

                        chunkData.setRegion(
                                bx,
                                heightPoints[0],
                                bz,
                                bx + 1,
                                heightPoints[1],
                                bz + 1,
                                Material.SNOW_BLOCK
                        );
                    }
                }

            }

            @Override
            public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull ChunkGenerator.ChunkData chunkData) {

                for (int bx = 0; bx < 16; bx++) {
                    for (int bz = 0; bz < 16; bz ++) {
                        int[] heightPoints = getAttitudeAt(
                                (x << 4) + bx,
                                (z << 4) + bz,
                                chunkData.getMaxHeight()
                        );

                        if (!isValidHeigth(heightPoints)) continue;

                        switch (chunkData.getBiome(bx, 100, bz)) {
                            case FOREST -> {
                                chunkData.setBlock(
                                        bx,
                                        heightPoints[1],
                                        bz,
                                        Material.PODZOL
                                );
                                chunkData.setBlock(
                                        bx,
                                        heightPoints[1] - 1,
                                        bz,
                                        Material.DIRT
                                );
                                chunkData.setBlock(
                                        bx,
                                        heightPoints[1] - 2,
                                        bz,
                                        Material.COARSE_DIRT
                                );
                            }
                            case DESERT -> {
                                chunkData.setBlock(
                                        bx,
                                        heightPoints[1],
                                        bz,
                                        Material.BLACK_CONCRETE_POWDER
                                );
                                chunkData.setBlock(
                                        bx,
                                        heightPoints[1] - 1,
                                        bz,
                                        Material.GRAY_CONCRETE_POWDER
                                );
                            }
                            case TAIGA -> {
                                chunkData.setBlock(
                                        bx,
                                        heightPoints[1],
                                        bz,
                                        Material.BLACKSTONE
                                );
                                chunkData.setBlock(
                                        bx,
                                        heightPoints[1] - 1,
                                        bz,
                                        Material.GILDED_BLACKSTONE
                                );
                            }
                            default -> {                            // The end OR the void
                                chunkData.setBlock(
                                        bx,
                                        heightPoints[1],
                                        bz,
                                        Material.WARPED_NYLIUM
                                );
                                chunkData.setBlock(
                                        bx,
                                        heightPoints[1] - 1,
                                        bz,
                                        Material.NETHER_GOLD_ORE
                                );
                                chunkData.setBlock(
                                        bx,
                                        heightPoints[1] - 2,
                                        bz,
                                        Material.NETHERRACK
                                );
                            }
                        }
                    }
                }
            }


        };
    }

    private static final double attitude      = 16.0; // The higher value, the more flat
    private static final double softness      = 96.0; // The higher value, the softer surface
    private static final double steep         = 64.0; // The higher value, the higher number of blocks under the surface
    private static final double airAttitude   = 12.0;
    private static final double airSoftness   = 64.0;
    private static final double size          = 12.0;

    public static int[] getAttitudeAt(int x, int z, int maxHeight) {
        return NoiseGenerators.noiseIntersection(
                attitude, softness, steep,
                airAttitude, airSoftness,
                size,
                x, z, maxHeight
        );
    }

    public static boolean isValidHeigth(int[] heightPoints) {
        return heightPoints[1] > heightPoints[0];
    }

    private static World.Environment getEnvironment() {
        return World.Environment.THE_END;
    }
}
