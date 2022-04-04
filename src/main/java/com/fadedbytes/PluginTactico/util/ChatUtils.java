package com.fadedbytes.PluginTactico.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtils {





    public enum CustomColor {
        DORADO("#ffc800"),

        // Item tier:
        BASIC("#0CFADD"),
        RARE("#48D909"),
        EPIC("#F0C416"),
        LEGENDARY("#DB5314"),
        EXCLUSIVE("#FF08E6");


        ChatColor color;

        CustomColor(String hexValue) {
            color = ChatColor.of(hexValue);
        }


        @Override
        public String toString() {
            return color.toString();
        }
    }

}
