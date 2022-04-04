package com.fadedbytes.PluginTactico.misc.GamemodeKingbdogz;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamemodeKingbdogz implements Listener, TabCompleter, CommandExecutor {

    public GamemodeKingbdogz(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGamemodeCommand(PlayerCommandPreprocessEvent event) {
        event.getPlayer().sendMessage(event.getMessage());
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("adventure");
        tabs.add("creative");
        tabs.add("kingbdogz");
        tabs.add("spectator");
        tabs.add("survival");
        return tabs
                .stream()
                .filter((tab) -> tab.startsWith(args[0]))
                .toList();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args[0].toLowerCase() != "kingbdogz") {
                player.performCommand("/minecraft:gamemode " );
            }
        }
        return true;
    }
}
