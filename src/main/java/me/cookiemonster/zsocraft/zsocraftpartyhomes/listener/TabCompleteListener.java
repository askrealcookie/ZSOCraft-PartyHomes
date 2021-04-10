package me.cookiemonster.zsocraft.zsocraftpartyhomes.listener;

import me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabcompletevalidators.DelhomeValidator;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabcompletevalidators.HomeValidator;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabcompletevalidators.SethomeValidator;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabcompletevalidators.Validator;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.ArrayUtil;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.*;

public class TabCompleteListener implements Listener {

    private final HashMap<String, Validator> commands_validators = new HashMap<>();

    public TabCompleteListener(){
        commands_validators.put("home", new HomeValidator());
        commands_validators.put("sethome", new SethomeValidator());
        commands_validators.put("delhome", new DelhomeValidator());
    }

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

        for(Map.Entry<String, Validator> entry : commands_validators.entrySet()){
            Validator validator = entry.getValue();
            String command = entry.getKey();
            String playerCommandArgument;

            if(!validator.isValid(p)) continue;

            if(args.length == 1) {
                completions.add(command);
            }
            else if(args.length == 2) {
                playerCommandArgument = args[1];

                if (command.startsWith(playerCommandArgument) && !command.equals(playerCommandArgument))
                    completions.add(command);
            }
        }

        setCompletionsToLowerCaseOnTabCompleteEvent(e, completions);
    }

    private void setCompletionsToLowerCaseOnTabCompleteEvent(TabCompleteEvent e, List<String> completions){
        ArrayUtil.replaceToLowerCase(completions);
        e.setCompletions(completions);
    }

}
