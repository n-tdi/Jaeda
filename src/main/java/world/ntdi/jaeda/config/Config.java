package world.ntdi.jaeda.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Objects;

public class Config extends YamlConfiguration {

    private final String filename;
    private final JavaPlugin m_javaPlugin;
    private final File file;

    /**
     * Creates a new config with the given filename.
     */
    public Config(final String p_filename, final JavaPlugin p_javaPlugin) {
        this.filename = p_filename;
        m_javaPlugin = p_javaPlugin;
        file = new File(p_javaPlugin.getDataFolder(), p_filename);
        loadDefaults();
        reload();
    }

    private void loadDefaults() {
        final YamlConfiguration defaultConfig = new YamlConfiguration();

        try (final InputStream inputStream = m_javaPlugin.getResource(filename)) {
            if (inputStream != null) {
                try (final Reader reader = new InputStreamReader(Objects.requireNonNull(inputStream))) {
                    defaultConfig.load(reader);
                }
            }
        } catch (final IOException exception) {
            throw new IllegalArgumentException("Could not load included config file " + filename, exception);
        } catch (final InvalidConfigurationException exception) {
            throw new IllegalArgumentException("Invalid default config for " + filename, exception);
        }

        setDefaults(defaultConfig);
    }

    /**
     * Reloads the configuration
     */
    public void reload() {
        saveDefaultConfig();
        try {
            load(file);
        } catch (final IOException exception) {
            new IllegalArgumentException("Could not find or load file " + filename, exception).printStackTrace();
        } catch (final InvalidConfigurationException exception) {
            m_javaPlugin.getLogger().severe("Your config file " + filename + " is invalid, using default values now. Please fix the below mentioned errors and try again:");
            exception.printStackTrace();
        }
    }

    private void saveDefaultConfig() {
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new UncheckedIOException(new IOException("Could not create directory " + parent.getAbsolutePath()));

            }
            m_javaPlugin.saveResource(filename, false);
        }
    }

    /**
     * Saves the configuration under its original file name
     *
     * @throws IOException if the underlying YamlConfiguration throws it
     */
    public void save() throws IOException {
        this.save(file);
    }

}