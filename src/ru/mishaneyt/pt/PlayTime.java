package ru.mishaneyt.pt;

import org.bukkit.event.*;
import org.bukkit.configuration.file.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

import java.lang.management.ManagementFactory;
import java.util.*;

public class PlayTime implements CommandExecutor, Listener
{
    Main plugin;
    static ConfigWrapper PlayTimeConfig;
    static long Time;
    
    public PlayTime(final Main instance) {
        this.plugin = instance;
        (PlayTime.PlayTimeConfig = new ConfigWrapper(instance, null, "config.yml")).createFile(null, "# PlayerTime by MishaNeYT \r\n# ==================\r\n#"
        		+ " | \u041a\u041e\u041d\u0424\u0418\u0413 \u041f\u041b\u0410\u0413\u0418\u041d\u0410 "
        		+ "|\r\n# ==================\r\n\r\n# \u041c\u043e\u0436\u043d\u043e \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c Placeholder\r"
        		+ "\n# %player% - \u0418\u043c\u044f \u0438\u0433\u0440\u043e\u043a\u0430\r\n# %time% - \u0441\u043c\u043e\u0442\u0440\u0438\u0442 \u0432\u0440\u0435\u043c\u044f "
        		+ "\u0438\u0433\u0440\u043e\u043a\u0430\r\n# %timesjoined% - \u043f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 "
        		+ "\u043a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u0440\u0430\u0437, \u043a\u043e\u0433\u0434\u0430 \u0438\u0433\u0440\u043e\u043a "
        		+ "\u043f\u0440\u0438\u0441\u043e\u0435\u0434\u0438\u043d\u0438\u043b\u0441\u044f \u043a \u0441\u0435\u0440\u0432\u0435\u0440\u0443\r\n# %serveruptime% - "
        		+ "\u043f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 \u0432\u0440\u0435\u043c\u044f \u0440\u0430\u0431\u043e\u0442\u044b "
        		+ "\u0441\u0435\u0440\u0432\u0435\u0440\u0430\r\n# %prefix% - \u043f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 \u043f\u0440\u0435\u0444\u0438\u043a\u0441 "
        		+ "\u0438\u0433\u0440\u043e\u043a\u0430\r\n");
        
        final FileConfiguration c = PlayTime.PlayTimeConfig.getConfig();
        c.addDefault("prefix", (Object)"&6PlayerTime &8|");
        c.addDefault("time.second.1", (Object)"s" + " 1 секунда");
        c.addDefault("time.second.2", (Object)"s" + " 2-4 секунды");
        c.addDefault("time.second.5", (Object)"s" + " 5-10 секунд");
        c.addDefault("time.minute.1", (Object)"m" + " 1 минута");
        c.addDefault("time.minute.2", (Object)"m" + " 2-4 минуты");
        c.addDefault("time.minute.5", (Object)"m" + " 5-10 минут");
        c.addDefault("time.hour.1", (Object)"h" + " 1 час");
        c.addDefault("time.hour.2", (Object)"h" + " 2-4 часа");
        c.addDefault("time.hour.5", (Object)"h" + " 5-10 часов");
        c.addDefault("time.day.1", (Object)"d" + " 1 день");
        c.addDefault("time.day.2", (Object)"d" + " 2-4 дня");
        c.addDefault("time.day.5", (Object)"d" + " 5-10 дней");
        c.addDefault("messages.no_permission", (Object)Arrays.asList("", "&c\u2716 \u0423 \u0432\u0430\u0441 \u043d\u0435\u0442 \u043f\u0440\u0430\u0432!", ""));
        c.addDefault("messages.not_online", (Object)Arrays.asList("", "&c\u2716 \u0418\u0433\u0440\u043e\u043a %player% \u043d\u0435 \u0432 \u0441\u0435\u0442\u0438!", ""));
        c.addDefault("messages.player", (Object)Arrays.asList("", "&6\u0412\u0440\u0435\u043c\u044f \u0438\u0433\u0440\u043e\u043a\u0430: &7%player%", "&f \u0412\u0440\u0435\u043c\u044f: &e%time%", "&f \u0420\u0430\u0437 \u043f\u0440\u0438\u0441\u043e\u0435\u0434\u0438\u043d\u0438\u043b\u0441\u044f: &e%timesjoined%", ""));
        c.addDefault("messages.other_players", (Object)Arrays.asList("", "&6\u0412\u0440\u0435\u043c\u044f \u0438\u0433\u0440\u043e\u043a\u0430: &7%player%", "&f \u0412\u0440\u0435\u043c\u044f: &e%time%", "&f \u0420\u0430\u0437 \u043f\u0440\u0438\u0441\u043e\u0435\u0434\u0438\u043d\u0438\u043b\u0441\u044f: &e%timesjoined%", ""));
        c.addDefault("messages.server_uptime", (Object)Arrays.asList("", "&fСервер активен: &6%serveruptime%",""));
        c.addDefault("messages.reload", (Object)Arrays.asList("", "&fКонфиг перезагружен!",""));
        c.options().copyDefaults(true);
        PlayTime.PlayTimeConfig.saveConfig();
        PlayTime.PlayTimeConfig.reloadConfig();
    }
    
    public static String format(final String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (sender instanceof Player) {
            final FileConfiguration c = PlayTime.PlayTimeConfig.getConfig();
            final Player p = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("playtime")) {
                if (!sender.hasPermission("playtime.check")) {
                    for (final String NoPermission : c.getStringList("messages.no_permission")) {
                        sender.sendMessage(format(NoPermission).replace("%prefix%", format(c.getString("prefix"))));
                    }
                    return true;
                }
                if (args.length == 0) {
                    for (final String Player : c.getStringList("messages.player")) {
                        sender.sendMessage(format(Player).replace("%player%", sender.getName()).replace("%time%", TimeFormat.getTime(p.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20)).replace("%timesjoined%", String.valueOf(p.getStatistic(Statistic.LEAVE_GAME) + 1)).replace("%prefix%", format(c.getString("prefix"))));
                    }
                }
                else {
                    final Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        for (final String NotOnline : c.getStringList("messages.not_online")) {
                            sender.sendMessage(format(NotOnline.replace("%player%", args[0])).replace("%prefix%", format(c.getString("prefix"))));
                        }
                        return true;
                    }
                    final Player t = Bukkit.getPlayer(args[0]);
                    for (final String OtherPlayers : c.getStringList("messages.other_players")) {
                        sender.sendMessage(format(OtherPlayers).replace("%player%", t.getName()).replace("%time%", TimeFormat.getTime(t.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20)).replace("%timesjoined%", String.valueOf(t.getStatistic(Statistic.LEAVE_GAME) + 1)).replace("%prefix%", format(c.getString("prefix"))));
                    }
                }
                return true;
            }
            else if (cmd.getName().equalsIgnoreCase("serveruptime")) {
                if (!sender.hasPermission("playtime.uptime")) {
                    for (final String NoPermission : c.getStringList("messages.no_permission")) {
                        sender.sendMessage(format(NoPermission).replace("%prefix%", format(c.getString("prefix"))));
                    }
                    return true;
                }
                for (final String ServerUptime : c.getStringList("messages.server_uptime")) {
                    sender.sendMessage(format(ServerUptime).replace("%serveruptime%", TimeFormat.getTime(ManagementFactory.getRuntimeMXBean().getUptime() / 1000)).replace("%prefix%", format(c.getString("prefix"))));
                }
                return true;
            }
            else if (cmd.getName().equalsIgnoreCase("playtimereload")) {
                if (!sender.hasPermission("playtime.reload")) {
                    for (final String NoPermission : c.getStringList("messages.no_permission")) {
                        sender.sendMessage(format(NoPermission).replace("%prefix%", format(c.getString("prefix"))));
                    }
                    return true;
                }
                for (final String reload : c.getStringList("messages.reload")) {
                	PlayTime.PlayTimeConfig.reloadConfig();
                    sender.sendMessage(format(reload).replace("%prefix%", format(c.getString("prefix"))));
                }
                return true;
            }
            
            
        }
        return true;
    }
}
