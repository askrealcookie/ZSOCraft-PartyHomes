package me.cookiemonster.zsocraft.zsocraftpartyhomes.command;

import com.gmail.nossr50.api.PartyAPI;
import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.party.PartyManager;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.ZSOCraftPartyHomes;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.ChatUtil;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.DataUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PartyDelHomeCommand implements CommandExecutor {

    //instance
    private final ZSOCraftPartyHomes instance = ZSOCraftPartyHomes.getInstance();

    //cache config
    private final FileConfiguration config = instance.getConfig();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("partydelhome")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command is not executable by console.");
                return false;
            }
            Player p = (Player)sender;
            //player is not in any party
            boolean isInParty = PartyAPI.inParty(p);
            if(!isInParty){
                p.sendMessage(ChatUtil.fixColor(config.getString("messages.not-in-party")));
                return false;
            }

            //get player party
            Party playerParty = PartyManager.getParty(p);
            //get leader
            UUID leader = playerParty.getLeader().getUniqueId();

            //player is not a leader of party
            boolean isPartyLeader = p.getUniqueId() == leader;
            if(!isPartyLeader){
                p.sendMessage(ChatUtil.fixColor(config.getString("messages.you-are-not-leader")));
                return false;
            }

            DataUtil dataUtil = new DataUtil(p);
            if(!dataUtil.hasPath(playerParty.getName() + ".home.location")){
                p.sendMessage(ChatUtil.fixColor(config.getString("messages.no-home-set")));
                return false;
            }

            dataUtil.setNewLeader(null);
            dataUtil.setLocation(playerParty.getName() + ".home.location", null);
            p.sendMessage(ChatUtil.fixColor(config.getString("messages.home-deleted-successful")));

            return true;
        }
        return false;
    }
}
