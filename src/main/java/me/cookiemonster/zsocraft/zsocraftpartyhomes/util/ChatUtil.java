package me.cookiemonster.zsocraft.zsocraftpartyhomes.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public final class ChatUtil {
    public static String fixColor(String text){
        if(text == null) return "";
        return ChatColor.translateAlternateColorCodes('&', text
                .replace("%>", "»")
                .replace("%<", "«"));
    }

    public static String locToCoords(Location loc){
        return "x: " + loc.getBlockX() + ", y: " + loc.getBlockY() + ", z: " + loc.getBlockZ();
    }

    public static String ArrayToString(Object[] array){
        StringBuilder s = new StringBuilder();
        for(Object obj : array){
            s.append(obj).append(" ");
        }
        return s.toString();
    }
}