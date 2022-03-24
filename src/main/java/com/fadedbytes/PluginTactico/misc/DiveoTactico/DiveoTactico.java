package com.fadedbytes.PluginTactico.misc.DiveoTactico;

import com.fadedbytes.PluginTactico.PluginTactico;
import com.fadedbytes.PluginTactico.util.ChatUtils;
import com.fadedbytes.PluginTactico.util.CustomItemUtil;
import com.fadedbytes.PluginTactico.util.playerutils.PlayerActionBarAPI.ActionBar;
import com.fadedbytes.PluginTactico.util.playerutils.PlayerActionBarAPI.ActionBarPriority;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

/**
 * This class defines the custom diving mechanics
 */
public class DiveoTactico {

    /**
     * The ticks the {@link #loop()} function will be executed after.
     */
    public static final int LOOP_PERIOD = 20;

    /**
     * Show the status actionbar while diving
     */
    private static final boolean SHOW_ACTIONBAR = true;
    /**
     * The color of the actionbars
     */
    private static final String ACTIONBAR_COLOR = ChatColor.of("#3fbf6a").toString();

    private static final NamespacedKey PLAYER_DIVING_COUNTER = new NamespacedKey(PluginTactico.getPlugin(), "player_diving_counter");

    // MASK
    private static final String MASK_ID = "diving_mask";
    private static final String MASK_NAME = ChatUtils.CustomColor.BASIC + "Escafandra de buceo";
    private static final String MASK_LORE = """
            
            """;


    private static final ItemStack MASK = initMask();

    private static ItemStack initMask() {
        ItemStack mask = new ItemStack(Material.CHAINMAIL_HELMET, 1);
        ItemMeta meta = mask.getItemMeta();

        assert meta != null;
        meta.setDisplayName(MASK_NAME);

        ArrayList<String> lore = new ArrayList<>();
        lore.add(MASK_LORE);
        meta.setLore(lore);

        meta.setCustomModelData(10);

        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(CustomItemUtil.ITEM_NAME, PersistentDataType.STRING, MASK_ID);
        mask.setItemMeta(meta);

        return mask;
    }

    public static ItemStack getMask() {
        return MASK;
    }

    public static void loop() {

        for (Player p: Bukkit.getServer().getOnlinePlayers()) {
            ItemStack item = p.getInventory().getItem(EquipmentSlot.HEAD);
            if (item == null) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            if (!container.has(CustomItemUtil.ITEM_NAME, PersistentDataType.STRING)) {
                return;
            }
            if (container.get(CustomItemUtil.ITEM_NAME, PersistentDataType.STRING).equals(MASK_ID)) {
                if (p.isInWater()) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 40, 1, false, false, false));
                    if (SHOW_ACTIONBAR) {
                        ActionBar.addActionBar(ActionBarPriority.LOWEST, LOOP_PERIOD / 2 + LOOP_PERIOD / 3, ACTIONBAR_COLOR + "§lBuceando...", p);
                    }
                    p.playSound(p.getLocation(), Sound.BLOCK_CONDUIT_AMBIENT_SHORT, 1.0f, 1.0f);
                }
            }
        }
    }

}
