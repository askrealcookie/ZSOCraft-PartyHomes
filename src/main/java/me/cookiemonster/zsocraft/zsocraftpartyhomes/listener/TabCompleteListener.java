package me.cookiemonster.zsocraft.zsocraftpartyhomes.listener;

import me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabcompletionvalidators.DelHomeValidator;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabcompletionvalidators.HomeValidator;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabcompletionvalidators.SetHomeValidator;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabcompletionvalidators.Validator;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.ArrayUtil;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.*;

public class TabCompleteListener implements Listener {

    private final HashMap<String, Validator> commandsValidators = new HashMap<>();

    public TabCompleteListener(){
        commandsValidators.put("home", new HomeValidator());
        commandsValidators.put("sethome", new SetHomeValidator());
        commandsValidators.put("delhome", new DelHomeValidator());
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

        for(Map.Entry<String, Validator> entry : commandsValidators.entrySet()){
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

        //to do: only set it to lowercase in argument 1(arg 0 is command), because it also makes player names lowercase
        setCompletionsToLowerCaseOnTabCompleteEvent(e, completions);
    }

    private void setCompletionsToLowerCaseOnTabCompleteEvent(TabCompleteEvent e, List<String> completions){
        ArrayUtil.replaceToLowerCase(completions);
        e.setCompletions(completions);
    }

}
