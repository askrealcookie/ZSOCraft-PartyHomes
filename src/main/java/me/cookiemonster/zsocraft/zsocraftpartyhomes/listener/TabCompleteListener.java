package me.cookiemonster.zsocraft.zsocraftpartyhomes.listener;

import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.party.PartyManager;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabCompleteValidators.HomeValidator;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabCompleteValidators.Validator;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.ArrayUtil;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.*;

public class TabCompleteListener implements Listener {

    private final HashMap<String, Validator> commands_validators = new HashMap<>();

    @EventHandler
    public void onTabComplete(TabCompleteEvent e){

        commands_validators.put("home", new HomeValidator());

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
            if(validator.isValid(p) && command.startsWith(args[1])) completions.add(command);
        }

        setCompletionsToLowerCaseOnTabCompleteEvent(e, completions);
    }

    private void setCompletionsToLowerCaseOnTabCompleteEvent(TabCompleteEvent e, List<String> completions){
        ArrayUtil.replaceToLowerCase(completions);
        e.setCompletions(completions);
    }

}
