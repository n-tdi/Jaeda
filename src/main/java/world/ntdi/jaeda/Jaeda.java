package world.ntdi.jaeda;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.jaeda.command.ReloadCommand;
import world.ntdi.jaeda.config.Config;
import world.ntdi.jaeda.config.ConfigOptions;
import world.ntdi.jaeda.discord.JaedaWebhook;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public final class Jaeda extends JavaPlugin implements Listener {
    private Config m_config;

    @Override
    public void onEnable() {
        m_config = new Config("config.yml", this);

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("jaeda").setExecutor(new ReloadCommand(this));
        getCommand("jaeda").setTabCompleter(new ReloadCommand(this));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent p_inventoryClickEvent) {
        if (!(p_inventoryClickEvent.getWhoClicked() instanceof Player p_player)) {
            return;
        }

        if (p_inventoryClickEvent.getClickedInventory().getType() != InventoryType.CREATIVE) {
            return;
        }

        if (p_inventoryClickEvent.getAction() != InventoryAction.CLONE_STACK) {
            return;
        }

        final List<String> blacklistedItemsList = m_config.getStringList(ConfigOptions.BLACKLISTED_ITEMS.toString());
        final List<Material> blaclistedItemsMaterialList = blacklistedItemsList.stream()
                .map(Material::valueOf)
                .toList();

        final ItemStack cursorItem = p_inventoryClickEvent.getCursor();
        final ItemStack currentItem = p_inventoryClickEvent.getCurrentItem();

        for (final Material material : blaclistedItemsMaterialList) {
            if (currentItem.getType() == material || cursorItem.getType() == material) {
                return;
            }
        }

        final String webhookUrl = m_config.getString(ConfigOptions.DISCORD_WEBHOOK_URL.toString());
        final String username = p_player.getName();
        final String uuid = p_player.getUniqueId().toString();
        final String item = cursorItem.getType().toString();

        final JaedaWebhook jaedaWebhook = new JaedaWebhook(webhookUrl, username, uuid, item);
        try {
            jaedaWebhook.execute();
        } catch (IOException p_e) {
            getLogger().severe("Failed to send embed to webhook with url " + webhookUrl);
            p_e.printStackTrace();
        }
    }

    public Config getConfig() {
        return m_config;
    }
}
