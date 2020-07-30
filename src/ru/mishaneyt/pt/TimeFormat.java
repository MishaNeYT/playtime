package ru.mishaneyt.pt;

import org.bukkit.configuration.file.FileConfiguration;

public class TimeFormat
{
    public static String getTime(long sec) {
    	final FileConfiguration c = PlayTime.PlayTimeConfig.getConfig();
        if ((System.currentTimeMillis() / 1000)  < 1) {
                    return "1 \u0441\u0435\u043a\u0443\u043d\u0434\u0430";
        }
           


        
        long m = sec / 60L;
        sec %= 60L;
        long h = m / 60L;
        m %= 60L;
        long d = h / 24L;
        h %= 24L;
        d %= 365L;
        String time = "";
        if (d > 0L) {
            time = time + d + " " + formatTime(d, c.getString("time.day.1"), c.getString("time.day.2"), c.getString("time.day.5"));
            if (h > 0L || m > 0L || sec > 0L) {
                time += " ";
            }
        }
        if (h > 0L) {
            time = time + h + " " + formatTime(h, c.getString("time.hour.1"), c.getString("time.hour.2"), c.getString("time.hour.5"));
            if (m > 0L || sec > 0L) {
                time += " ";
            }
        }
        if (m > 0L) {
            time = time + m + " " + formatTime(m, c.getString("time.minute.1"), c.getString("time.minute.2"), c.getString("time.minute.5"));
            if (sec > 0L) {
                time += " ";
            }
        }
        if (sec > 0L) {
            time = time + sec + " " + formatTime(sec, c.getString("time.second.1"), c.getString("time.second.2"), c.getString("time.second.5"));
        }
        return time;
    }
    
   
    
    private static String formatTime(final long num, final String single, final String lessfive, final String others) {
        if (num % 100 > 10 && num % 100 < 15) {
            return others;
        }
        switch ((int)(num % 10)) {
            case 1: {
                return single;
            }
            case 2:
            case 3:
            case 4: {
                return lessfive;
            }
            default: {
                return others;
            }
        }
	}
}
