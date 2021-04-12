package me.cookiemonster.zsocraft.zsocraftpartyhomes.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e){
        String[] args = e.getMessage().split(" ");
        String cmd = args[0];
        if(cmd.equalsIgnoreCase("/party")){
            //fixes #6
            if(args.length < 2) return;
            Player p = e.getPlayer();
            if(args[1].equalsIgnoreCase("sethome")){
                p.performCommand("partysethome");
                e.setCancelled(true);
            }
            if(args[1].equalsIgnoreCase("home")){
                p.performCommand("partyhome");
                e.setCancelled(true);
            }
            if(args[1].equalsIgnoreCase("delhome")){
                p.performCommand("partydelhome");
                e.setCancelled(true);
            }
        }
    }
}
