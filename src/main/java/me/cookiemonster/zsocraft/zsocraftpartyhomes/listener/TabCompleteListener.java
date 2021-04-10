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
        //spaghetto time
        if(args.length == 2) {
            if ((args[1].toLowerCase().startsWith("h") || args[1].toLowerCase().startsWith("ho") || args[1].toLowerCase().startsWith("hom")) && (!args[1].toLowerCase().startsWith("home")))
                completions.add("home");
            if ((args[1].toLowerCase().startsWith("s") || args[1].toLowerCase().startsWith("se") || args[1].toLowerCase().startsWith("set") || args[1].toLowerCase().startsWith("seth") || args[1].toLowerCase().startsWith("setho") || args[1].toLowerCase().startsWith("sethom")) && (!args[1].toLowerCase().startsWith("sethome")))
                completions.add("sethome");
            if ((args[1].toLowerCase().startsWith("d") || args[1].toLowerCase().startsWith("de") || args[1].toLowerCase().startsWith("del") || args[1].toLowerCase().startsWith("delh") || args[1].toLowerCase().startsWith("delho") || args[1].toLowerCase().startsWith("delhom")) && (!args[1].toLowerCase().startsWith("delhome")))
                completions.add("delhome");
        } else if (args.length == 1){
            completions.add("home");
            completions.add("sethome");
            completions.add("delhome");
        }
        ArrayUtil.replaceToLowerCase(completions);
        e.setCompletions(completions);
    }
}
