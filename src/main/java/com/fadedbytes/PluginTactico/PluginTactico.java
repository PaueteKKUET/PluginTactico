package com.fadedbytes.PluginTactico;

import com.fadedbytes.PluginTactico.DEV.ActionBarTests;
import com.fadedbytes.PluginTactico.gameplay.AetherTactico.Aether;
import com.fadedbytes.PluginTactico.gameplay.AetherTactico.AetherAmbients;
import com.fadedbytes.PluginTactico.gameplay.HandTool.HandTool;
import com.fadedbytes.PluginTactico.gameplay.NaturalMobVariety.HostileMobs;
import com.fadedbytes.PluginTactico.misc.ChatFeatures.ChatUtilities;
import com.fadedbytes.PluginTactico.misc.ColoredShulkers.ColoredShulkers;
import com.fadedbytes.PluginTactico.gameplay.DiveoTactico.DiveoTactico;
import com.fadedbytes.PluginTactico.misc.GamemodeKingbdogz.GamemodeKingbdogz;
import com.fadedbytes.PluginTactico.misc.TrollCheats.TrollCheats;
import com.fadedbytes.PluginTactico.util.playerutils.PlayerActionBarAPI.ActionBarManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginTactico extends JavaPlugin {

    private static JavaPlugin PLUGIN = null;
    private static Server SERVER = null;

    public static JavaPlugin getPlugin() {
        return PLUGIN;
    }

    @Override
    public void onDisable() {
        super.onDisable();

    }

    @Override
    public void onEnable() {
        super.onEnable();
        PLUGIN = this;

        // Enable plugin modules
        ColoredShulkers     COLORED_SHULKERS    = new ColoredShulkers(this);
        HandTool            HAND_TOOL           = new HandTool(this);
        HostileMobs         HOSTILE_MOBS        = new HostileMobs(this);
        ChatUtilities       CHAT_UTILITIES      = new ChatUtilities(this);
        TrollCheats         TROLL_CHEATS        = new TrollCheats(this);
        Aether              AETHER              = new Aether(this);
        GamemodeKingbdogz   KINGBDOGZ           = new GamemodeKingbdogz(this);

        // Setup command executors
        getCommand("uwu").setExecutor(new ActionBarTests());
        getCommand("handtool").setExecutor(HAND_TOOL);
        getCommand("gamemode").setExecutor(KINGBDOGZ);;

        getCommand("gamemode").setTabCompleter(KINGBDOGZ);
        initModules();
    }

    private void initModules() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginTactico.getPlugin(), DiveoTactico::loop, 0, DiveoTactico.LOOP_PERIOD);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginTactico.getPlugin(), ActionBarManager::loop, 0, ActionBarManager.LOOP_PERIOD);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginTactico.getPlugin(), AetherAmbients::loop, 0, AetherAmbients.LOOP_PERIOD);
    }
}
