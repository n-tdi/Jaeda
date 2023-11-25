package world.ntdi.jaeda;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.jaeda.config.Config;

public final class Jaeda extends JavaPlugin implements Listener {
    private Config m_config;

    @Override
    public void onEnable() {
        m_config = new Config("config.yml", this);

        getServer().getPluginManager().registerEvents(this, this);
    }
}
