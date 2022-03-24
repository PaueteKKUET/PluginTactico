package com.fadedbytes.PluginTactico.DEV;

import com.fadedbytes.PluginTactico.PluginTactico;
import com.fadedbytes.PluginTactico.gameplay.AetherTactico.AetherWorld;
import com.fadedbytes.PluginTactico.misc.DiveoTactico.DiveoTactico;
import com.fadedbytes.PluginTactico.misc.TrollCheats.TrollCheats;
import com.fadedbytes.PluginTactico.util.playerutils.PlayerActionBarAPI.ActionBar;
import com.fadedbytes.PluginTactico.util.playerutils.PlayerActionBarAPI.ActionBarPriority;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;

public class ActionBarTests implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            try {
                p.teleport(new Location(Bukkit.getWorld(AetherWorld.WORLD_NAME), 0, 100, 0));
            } catch (Exception e) {
                p.sendMessage(e.getMessage());
            }
        }

        return true;
    }
}
