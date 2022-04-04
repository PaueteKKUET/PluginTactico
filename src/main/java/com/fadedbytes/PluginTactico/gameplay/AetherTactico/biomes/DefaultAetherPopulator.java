package com.fadedbytes.PluginTactico.gameplay.AetherTactico.biomes;

import com.fadedbytes.PluginTactico.gameplay.AetherTactico.AetherWorld;
import net.minecraft.world.level.GeneratorAccessSeed;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_18_R2.generator.CraftLimitedRegion;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DefaultAetherPopulator extends BlockPopulator {
    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull LimitedRegion limitedRegion) {
        if (random.nextBoolean()) {
            return;
        }

        World world = Bukkit.getWorld(worldInfo.getName());

        int bx = (x << 4) + (random.nextInt() % 15);
        int bz = (z << 4) + (random.nextInt() % 15);
        //int by = world.getHighestBlockYAt(bx, bz);
        int[] heightPoints = AetherWorld.getAttitudeAt(bx, bz, worldInfo.getMaxHeight());

        if (AetherWorld.isValidHeigth(heightPoints)) {
            if (random.nextBoolean()) {
                limitedRegion.setType(bx, heightPoints[1] + 1, bz, Material.GLOWSTONE);
            } else {
                limitedRegion.setType(bx, heightPoints[1] + 1, bz, Material.SCULK_SENSOR);
            }
        }
    }
}
