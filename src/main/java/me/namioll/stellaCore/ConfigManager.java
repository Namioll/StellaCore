package me.namioll.stellaCore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private final File file;
    private FileConfiguration config;
    private final StellaCore plugin;

    public ConfigManager(StellaCore plugin, String fileName) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), fileName);
    }

    public void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        if (!file.exists()) {
            plugin.saveResource(file.getName(), false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Не удалось сохранить конфиг " + file.getName() + ": " + e.getMessage());
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
