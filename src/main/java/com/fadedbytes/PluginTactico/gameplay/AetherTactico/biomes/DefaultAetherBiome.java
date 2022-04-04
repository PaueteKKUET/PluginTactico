package com.fadedbytes.PluginTactico.gameplay.AetherTactico.biomes;

import net.minecraft.core.IRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.biome.BiomeBase;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;

public class DefaultAetherBiome {


    public static void getBiome() {
        Server server = Bukkit.getServer();
        CraftServer craftserver = (CraftServer)server;
        DedicatedServer dedicatedserver = craftserver.getServer();
        ResourceKey<BiomeBase> newKey = ResourceKey.a(IRegistry.aP, new MinecraftKey("test", "fancybiome"));


    }

}
