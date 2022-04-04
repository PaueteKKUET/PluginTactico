package com.fadedbytes.PluginTactico.DEV;

import com.fadedbytes.PluginTactico.gameplay.AetherTactico.AetherWorld;
import com.fadedbytes.PluginTactico.gameplay.AetherTactico.biomes.TestBiomeWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ActionBarTests implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            try {
                if (args.length > 0) {
                    p.teleport(new Location(Bukkit.getWorld(TestBiomeWorld.WORLD_NAME), 0, 30, 0));
                } else {
                    p.teleport(new Location(Bukkit.getWorld(AetherWorld.WORLD_NAME), 0, 100, 0));
                }
            } catch (Exception e) {
                p.sendMessage(e.getMessage());
            }
        }

        return true;
    }
}
