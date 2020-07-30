package ru.mishaneyt.pt;

import org.bukkit.plugin.java.*;
import org.bukkit.configuration.file.*;
import java.util.logging.*;
import java.io.*;

public class ConfigWrapper
{
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;
    private final String folderName;
    private final String fileName;
    
    public ConfigWrapper(final JavaPlugin instance, final String folderName, final String fileName) {
        this.plugin = instance;
        this.folderName = folderName;
        this.fileName = fileName;
    }
    
    public void createFile(final String message, final String header) {
        this.reloadConfig();
        this.saveConfig();
        this.loadConfig(header);
        if (message != null) {
            this.plugin.getLogger().info(message);
        }
    }
    
    public FileConfiguration getConfig() {
        if (this.config == null) {
            this.reloadConfig();
        }
        return this.config;
    }
    
    public void loadConfig(final String header) {
        this.config.options().header(header);
        this.config.options().copyDefaults(true);
        this.saveConfig();
    }
    
    public void reloadConfig() {
        if (this.configFile == null) {
            if (this.folderName != null && !this.folderName.isEmpty()) {
                this.configFile = new File(this.plugin.getDataFolder() + File.separator + this.folderName, this.fileName);
            }
            else {
                this.configFile = new File(this.plugin.getDataFolder(), this.fileName);
            }
        }
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
    }
    
    public void saveConfig() {
        if (this.config == null || this.configFile == null) {
            return;
        }
        try {
            this.getConfig().save(this.configFile);
        }
        catch (IOException ex) {
            this.plugin.getLogger().log(Level.SEVERE, "\u041d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u0441\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044e \u0432 " + this.configFile, ex);
        }
    }
}
