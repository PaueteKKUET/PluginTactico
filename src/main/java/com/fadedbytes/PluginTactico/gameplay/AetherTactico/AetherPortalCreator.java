package com.fadedbytes.PluginTactico.gameplay.AetherTactico;

import com.fadedbytes.PluginTactico.Tags.TagTactica;
import com.fadedbytes.PluginTactico.util.BlockUtil;
import com.fadedbytes.PluginTactico.util.playerutils.PlayerUtils;
import net.minecraft.world.level.block.BlockStainedGlass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.craftbukkit.v1_18_R2.block.impl.CraftStainedGlassPane;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.util.Vector;

public class AetherPortalCreator implements Listener {

    private static final int MAX_CHECKING_DISTANCE = 8;
    private static final Material PORTAL_BLOCK = Material.GOLD_BLOCK;

    AetherPortalCreator() {}

    @EventHandler
    public static void onPlayerPlacingWater(PlayerBucketEmptyEvent event) {

        if (!event.getBlockClicked().getType().equals(Material.GLOWSTONE)) {
            Block block = event.getBlock();
            boolean limitsWithGlowstone = false;
            for (BlockFace face : TagTactica.COLLINDANT_BLOCK_FACES) {
                if (block.getRelative(face, 1).getType().equals(Material.GLOWSTONE)) {
                    limitsWithGlowstone = true;
                }
            }

            if (!limitsWithGlowstone) return;
        }
        if (event.getBucket().equals(Material.LAVA_BUCKET)) return;

        event.setCancelled(checkAetherPortal(event.getBlock().getLocation()));
    }

    private static boolean checkAetherPortal(final Location location) {

        final Location[] portalEdges = new Location[2];

        Location[] xLoc = expand(location, Axis.X);
        if (xLoc != null) {
            Location[] yLoc1 = expand(xLoc[0], Axis.Y);
            Location[] yLoc2 = expand(xLoc[1], Axis.Y);

            if (yLoc1 != null && yLoc2 != null) {
                if (yLoc1[0].getBlockY() - yLoc1[1].getBlockY() == yLoc2[0].getBlockY() - yLoc2[1].getBlockY()) {
                    portalEdges[0] = yLoc1[0];
                    portalEdges[1] = yLoc2[1];
                }
            }
        }

        if (portalEdges[0] != null && portalEdges[1] != null) {
            if (tryToCreatePortal(portalEdges[0], portalEdges[1])) {
                return true;
            }
            else {
                portalEdges[0] = null;
                portalEdges[1] = null;
            }
        }

        Location[] zLoc = expand(location, Axis.Z);
        if (zLoc != null) {
            Location[] yLoc1 = expand(zLoc[0], Axis.Y);
            Location[] yLoc2 = expand(zLoc[1], Axis.Y);

            if (yLoc1 != null && yLoc2 != null) {
                if (yLoc1[0].getBlockY() - yLoc1[1].getBlockY() == yLoc2[0].getBlockY() - yLoc2[1].getBlockY()) {
                    portalEdges[0] = yLoc1[0];
                    portalEdges[1] = yLoc2[1];
                }
            }
        }

        if (portalEdges[0] != null && portalEdges[1] != null) {
            return tryToCreatePortal(portalEdges[0], portalEdges[1]);
        }
        return false;
    }

    private static Location[] expand(final Location loc, Axis axis) {
        if (loc == null || axis == null) return null;

        Vector vec = axis.vec.clone();
        Vector negativeVec = axis.vec.clone().multiply(-1);

        Location checkingLocation = loc.clone();
        int iterations = 0;

        Location p1 = null;
        while (iterations <= MAX_CHECKING_DISTANCE && p1 == null) {
            if (checkingLocation.add(vec).getBlock().getType().equals(Material.GLOWSTONE)) {
                p1 = checkingLocation.add(negativeVec);
            }
            iterations++;
        }
        if (p1 == null) return null;

        // Reset
        //iterations = 0;
        checkingLocation = loc.clone();

        Location p2 = null;
        while (iterations <= MAX_CHECKING_DISTANCE && p2 == null) {
            if (checkingLocation.add(negativeVec).getBlock().getType().equals(Material.GLOWSTONE)) {
                p2 = checkingLocation.add(vec);
            }
            iterations++;
        }
        if (p2 == null) return null;

        return new Location[] { p1, p2 };
    }

    private static boolean tryToCreatePortal(Location loc1, Location loc2) {
        if (!BlockUtil.isFullOfAir(loc1, loc2)) {
            return false;
        }
        //BlockUtil.fill(loc1, loc2, PORTAL_BLOCK);
        for (Location block : BlockUtil.selectArea(loc1, loc2)) {
            block.getBlock().setType(PORTAL_BLOCK);
            PlayerUtils.getPlayer().byName("PaueteKKUET").sendMessage(block.getBlock().getState().getClass().getSimpleName());
        }
        return true;
    }

    enum Axis {
        X(new Vector(1, 0, 0)),
        Y(new Vector(0, 1, 0)),
        Z(new Vector(0, 0, 1));

        private final Vector vec;

        Axis(Vector vec) {
            this.vec = vec;
        }
    }

}
