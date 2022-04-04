package com.fadedbytes.PluginTactico.misc.ChatFeatures;

import com.fadedbytes.PluginTactico.util.ChatUtils;
import com.fadedbytes.PluginTactico.util.playerutils.PlayerUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtilities implements Listener {

    static Player PAUETE = PlayerUtils.getPlayer().byName("PaueteKKUET");

    public ChatUtilities(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private static final Pattern BASIC_HEX_COLOR_CODE   = Pattern.compile("#[a-fA-F0-9]{6}");                       // #ff04aF
    private static final Pattern COMPLEX_COLOR_PATTERN  = Pattern.compile("::#[a-fA-F0-9]{6}");                     // A wild ::#ffc800 creeper appeared!
    private static final Pattern FADED_COLOR_PATTERN    = Pattern.compile("::#[a-fA-F0-9]{6}>.*<#[a-fA-F0-9]{6}");  // ::#ffc800> A wild creeper appeared <#c800ff !
    private static final Pattern START_FADED_PATTERN    = Pattern.compile("::#[a-fA-F0-9]{6}>");
    private static final Pattern END_FADED_PATTERN      = Pattern.compile("<#[a-fA-F0-9]{6}");


    @EventHandler(priority = EventPriority.LOW)
    public static void onPlayer(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        //if (!PlayerUtils.checkPermission(player, "tactico.chat.format")) return;

//        PAUETE.sendMessage("mensaje :D");
        String message = event.getMessage();
        event.setMessage(formatMessage(message));
    }

    private static String formatMessage(final String message) {
        String formattedMessage = message;
        formattedMessage = setRainbow(message);
        formattedMessage = setFadedColors(formattedMessage);
        formattedMessage = setComplexColors(formattedMessage);
        formattedMessage = setSimpleColors(formattedMessage);
        return formattedMessage;
    }

    private static String setSimpleColors(final String message) {
        return message.replace('&', '§');
    }

    private static String setComplexColors(String message) {
        Matcher matcher = COMPLEX_COLOR_PATTERN.matcher(message);
        List<String> colors = new ArrayList<String>();
        while (matcher.find()) {
            colors.add(matcher.group());
        }

        for (String colorCode : colors) {
            String color = findHexColor(colorCode);
            message = message.replace(colorCode, ChatColor.of(color).toString());
        }

        return message;
    }

    private static String findHexColor(String sequence) {
        String color = "#ff00ff";
        try {
            Matcher colorFinder = BASIC_HEX_COLOR_CODE.matcher(sequence);
            while (colorFinder.find()) {
                color = colorFinder.group();
            }
        } catch (IllegalStateException | IndexOutOfBoundsException e) {
            color = "#ff0000";
        }
        return color;
    }

    private static String setFadedColors(String message) {
//        PAUETE.sendMessage("Empezando a formatear: " + message);
        Matcher matcher = FADED_COLOR_PATTERN.matcher(message);
        List<String> fades = new ArrayList<>();
        while (matcher.find()) {
            fades.add(matcher.group());
        }

        for (String fadeElement : fades) {
//            PAUETE.sendMessage("Se ha encontrado un degradado: " + fadeElement);
            String[] fadeElementSlice = new String[] {
                    "",
                    null,
                    ""
            };
            Matcher startColorMatcher = START_FADED_PATTERN.matcher(fadeElement);
            while (startColorMatcher.find()) {
                fadeElementSlice[0] = startColorMatcher.group();
            }
            Matcher endColorMatcher = END_FADED_PATTERN.matcher(fadeElement);
            while (endColorMatcher.find()) {
                fadeElementSlice[2] = endColorMatcher.group();
            }

            fadeElementSlice[1] = fadeElement.replace(fadeElementSlice[0], "").replace(fadeElementSlice[2], "");

//            PAUETE.sendMessage("START: " + ChatColor.BLUE + fadeElementSlice[0]);

//            PAUETE.sendMessage("MESSAGE: " + ChatColor.BLUE + fadeElementSlice[1]);

//            PAUETE.sendMessage("END: " + ChatColor.BLUE + fadeElementSlice[2]);

            char[] messageChars = fadeElementSlice[1].toCharArray();

            String startColorCode = findHexColor(fadeElementSlice[0]);
            String endColorCode = findHexColor(fadeElementSlice[2]);

//            PAUETE.sendMessage(startColorCode);
//            PAUETE.sendMessage(endColorCode);

            Color startColor = Color.decode(startColorCode);
            Color endColor = Color.decode(endColorCode);

            float[] startHSB    = Color.RGBtoHSB(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), null);
            float[] endHSB      = Color.RGBtoHSB(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), null);

            float hue1 = endHSB[0] - startHSB[0];
            float hue2 = 360 - endHSB[0] - startHSB[0];
            float[] colorDiff   = new float[] {
                    Math.abs(hue1) < Math.abs(hue2) ? hue1 : hue2,      // (START -> 328    END -> 10)  -> 10 - 328 = -318      360 - 328 - 10 = 42    # 42 #
                                                                        // (START -> 10     END -> 328) -> 328 - 10 = 318       360 - 10 - 328 = 42
                    endHSB[1] - startHSB[1],
                    endHSB[2] - startHSB[2]
            };
            float[] colorStep = new float[] {
                    colorDiff[0] / messageChars.length,
                    colorDiff[1] / messageChars.length,
                    colorDiff[2] / messageChars.length
            };

            StringBuilder formattedChars = new StringBuilder();

            for (int i = 0; i < messageChars.length; i++) {
                Color stepColor = Color.getHSBColor(startHSB[0] + i * colorStep[0], startHSB[1] + i * colorStep[1], startHSB[2] + i * colorStep[2]);
                String colorCode = String.format("#%02x%02x%02x", stepColor.getRed(), stepColor.getGreen(), stepColor.getBlue());
                formattedChars.append(ChatColor.of(colorCode)).append(messageChars[i]);
            }

//            PAUETE.sendMessage(formattedChars.toString());

            message = message.replace(fadeElement, formattedChars.toString());

        }

        return message;

    }

    private static final String RAINBOW_PREFIX = "!!";
    private static final String COLOR_START = "#0000ff";
    private static final String COLOR_END = "#ff0000";
    public static String setRainbow(String message) {
        if (message.startsWith(RAINBOW_PREFIX)) {
            return "::" + COLOR_START + ">" + message.replaceFirst(RAINBOW_PREFIX, "") + "<" + COLOR_END;
        } else {
            return message;
        }
    }
}
