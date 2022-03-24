package com.fadedbytes.PluginTactico.gameplay.HandTool;

import com.fadedbytes.PluginTactico.PluginTactico;
import com.fadedbytes.PluginTactico.util.playerutils.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HandTool implements Listener, CommandExecutor {

    public HandTool(JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private static final PlayerList ACTIVE_PLAYERS = PlayerList.getOrCreateList(NamespacedKey.fromString("players_using_hand_tool", PluginTactico.getPlugin()));

    @EventHandler
    public static void onPlayerBreakingBlock(BlockDamageEvent e) {
        if (!ACTIVE_PLAYERS.includes(e.getPlayer()))
            return;
        if (e.getBlock().getType().getHardness() == -1.0F)
            return;
        e.getPlayer().breakBlock(e.getBlock());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player p) {
            if (!ACTIVE_PLAYERS.includes(p)) {
                p.sendMessage("§6Instamining activado :D");
                ACTIVE_PLAYERS.addPlayer(p);
            } else {
                p.sendMessage("§7Instamining desactivado :D");
                ACTIVE_PLAYERS.removePlayer(p);
            }
        }
        return true;
    }
}
