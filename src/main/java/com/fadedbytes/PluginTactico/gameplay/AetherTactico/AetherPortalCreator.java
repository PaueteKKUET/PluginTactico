package com.fadedbytes.PluginTactico.gameplay.AetherTactico;

import com.fadedbytes.PluginTactico.Tags.TagTactica;
import com.fadedbytes.PluginTactico.util.playerutils.PlayerUtils;
import com.fadedbytes.PluginTactico.util.worldutils.BlockUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.EndGateway;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.util.Vector;

import static com.fadedbytes.PluginTactico.gameplay.AetherTactico.Aether.AETHER;

public class AetherPortalCreator implements Listener {

    private static final int MAX_CHECKING_DISTANCE = 8;

    AetherPortalCreator() {}

    @EventHandler
    public static void onPlayerPlacingWater(PlayerBucketEmptyEvent event) {

        if (!event.getPlayer().getWorld().getName().equals("world")) return;

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
        Location exitLocation = getNearestSpawn(loc1.getBlockX(), loc1.getBlockZ());
        for (Location block : BlockUtil.selectArea(loc1, loc2)) {
            setPortalBlock(block.getBlock(), exitLocation);
        }
        return true;
    }


    private static final int RADIUS = 16;
    public static Location getNearestSpawn(int x, int z) {
        Location exitLocation = null;
        int by = AETHER.getHighestBlockAt(x, z).getY();

        if (by > AetherAmbients.MIN_AETHER_BLOCK) {
            exitLocation = new Location(AETHER, x, by, z);
            return exitLocation;
        }

        for(int bx = x - RADIUS; bx <= x + RADIUS; bx++)
        {
            for(int bz = z - RADIUS; bz <= z + RADIUS; bz++)
            {
                by = AETHER.getHighestBlockAt(bx, bz).getLocation().getBlockY();
                if (by > AetherAmbients.MIN_AETHER_BLOCK) {
                    exitLocation = new Location(AETHER, bx, by, bz);
                    return exitLocation;
                }
            }
        }

        exitLocation = new Location(AETHER, x, AETHER.getHighestBlockYAt(x, z), z);

        for (int bx = exitLocation.getBlockX() - 2; bx <= exitLocation.getBlockX() + 2; bx ++) {
            for (int bz = exitLocation.getBlockZ() - 2; bz <= exitLocation.getBlockZ() + 2; bz ++) {
                AETHER.getBlockAt(bx, 100, bz).setType(Material.GLOWSTONE);
            }
        }

        return exitLocation;


    }

    public static int PORTAL_TELEPORT_Y = 10000;
    private static void setPortalBlock(Block block, Location exitLocation) {
        block.setType(Material.END_GATEWAY);
        EndGateway portal = (EndGateway) block.getState();
        exitLocation.setY(PORTAL_TELEPORT_Y);
        exitLocation.setWorld(Bukkit.getWorld("world"));
        portal.setExitLocation(exitLocation);
        portal.setExactTeleport(true);
        portal.update();
    }


    @EventHandler
    public static void onGatewayUpdate(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType().equals(Material.END_GATEWAY) || block.getType().equals(Material.GLOWSTONE)){
            breakCollindantGateway(block);
        }
    }

    private static void breakCollindantGateway(Block block) {
        for (BlockFace face : TagTactica.COLLINDANT_BLOCK_FACES) {
            if (block.getRelative(face).getType().equals(Material.END_GATEWAY)) {
                block.getRelative(face).breakNaturally();
                breakCollindantGateway(block.getRelative(face));
            }
        }
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
