package com.fadedbytes.PluginTactico.minigames.ColorShuffle;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ColorShuffleCommandExecutor implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            switch (command.getName().toLowerCase()) {
                case "colorshuffle" -> {
                    if (args.length == 0) {
                        sender.sendMessage(ChatColor.RED + "Debes usar algún argumento para esto :/");
                    } else {
                        switch (args[0]) {
                            case "create" -> {
                                try {
                                    createArea(player.getWorld(), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), "aaa");
                                } catch (Exception e) {
                                    sender.sendMessage(ChatColor.RED + "Argumentos inválidos :(");
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>();
    }


    private static void createArea(World world, int x1, int z1, int x2, int z2, String id) {

    }
}
