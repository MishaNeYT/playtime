package ru.mishaneyt.pt;

import org.bukkit.plugin.java.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import org.bukkit.command.*;

public class Main extends JavaPlugin implements Listener
{
    public static Plugin plugin;
    
    public void onEnable() {
        Main.plugin = (Plugin)this;
        Bukkit.getServer().getPluginManager().registerEvents((Listener)new PlayTime(this), (Plugin)this);
        this.getCommand("playtime").setExecutor((CommandExecutor)new PlayTime(this));
        this.getCommand("serveruptime").setExecutor((CommandExecutor)new PlayTime(this));
        this.getCommand("playtimereload").setExecutor((CommandExecutor)new PlayTime(this));
    
    }
}
