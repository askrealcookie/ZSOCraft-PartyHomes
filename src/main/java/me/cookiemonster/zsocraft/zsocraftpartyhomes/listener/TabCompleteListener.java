package me.cookiemonster.zsocraft.zsocraftpartyhomes.listener;

import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.ArrayUtil;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteListener implements Listener {
    @EventHandler
    public void onTabComplete(TabCompleteEvent e){
        if(e.getSender() instanceof ConsoleCommandSender) return;
        Player p = (Player) e.getSender();
        String buffer = e.getBuffer();
        if (!buffer.startsWith("/")) {
            return;
        }
        String[] args = buffer.split(" ");

        String cmd = args[0];
        if (cmd.startsWith("/")) {
            cmd = cmd.substring(1);
        }

        if(!cmd.equalsIgnoreCase("party")) return;

        List<String> completions = new ArrayList<>(e.getCompletions());
        ArrayUtil.replaceToLowerCase(completions);
        completions.add("home");
        completions.add("sethome");
        e.setCompletions(completions);
    }
}
