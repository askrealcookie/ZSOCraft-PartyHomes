package me.cookiemonster.zsocraft.zsocraftpartyhomes.command;

import com.gmail.nossr50.api.PartyAPI;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.ZSOCraftPartyHomes;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.ChatUtil;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.DataUtil;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.MaterialUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class PartySetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("partysethome")){
            if(!(sender instanceof Player)){
                sender.sendMessage("This command is not executable by console.");
                return false;
            }
            Player p = (Player)sender;
            //player is not in any party
            boolean isInParty = PartyAPI.inParty(p);
            if(!isInParty){
                p.sendMessage(ChatUtil.fixColor(ZSOCraftPartyHomes.instance.getConfig().getString("messages.not-in-party")));
                return false;
            }

            //get player party
            String playerParty = PartyAPI.getPartyName(p);

            //player is not a leader of party
            boolean isPartyLeader = PartyAPI.getPartyLeader(playerParty).equals(p.getName());
            if(!isPartyLeader){
                p.sendMessage(ChatUtil.fixColor(ZSOCraftPartyHomes.instance.getConfig().getString("messages.you-are-not-leader")));
                return false;
            }

            //hacky workaround to avoid pixel perfect tp
            Location loc = p.getLocation().getBlock().getLocation();

            //check if player is standing on safe block
            Location belowLoc = loc.subtract(new Vector(0, 1, 0));
            List<String> _blocksBlacklist = ZSOCraftPartyHomes.instance.getConfig().getStringList("blocks-blacklist");
            String[] blocksBlacklist = _blocksBlacklist.toArray(new String[0]);

            if(MaterialUtil.isMaterialBlacklisted(belowLoc.getBlock().getType(), blocksBlacklist)){
                p.sendMessage(ChatUtil.fixColor(ZSOCraftPartyHomes.instance.getConfig().getString("messages.sethome-blacklisted-block")));
                return false;
            }

            loc.add(new Vector(0.5, 1, 0.5));

            DataUtil dataUtil = new DataUtil(p);
            dataUtil.setLocation(playerParty + ".home.location", loc);
            p.sendMessage(ChatUtil.fixColor(ZSOCraftPartyHomes.instance.getConfig().getString("messages.sethome-successful")));
            return true;
        }
        return false;
    }
}
