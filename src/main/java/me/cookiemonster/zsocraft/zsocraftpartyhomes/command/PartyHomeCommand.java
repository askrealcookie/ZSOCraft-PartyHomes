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
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class PartyHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("partyhome")){
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

            DataUtil dataUtil = new DataUtil(p);
            if(!dataUtil.hasPath(playerParty + ".home.location")){
                p.sendMessage(ChatUtil.fixColor(ZSOCraftPartyHomes.instance.getConfig().getString("messages.no-home-set")));
                return false;
            }
            Location loc = dataUtil.getLocation(playerParty + ".home.location");

            boolean multiWorldTeleport = ZSOCraftPartyHomes.instance.getConfig().getBoolean("allow-multi-world-teleport");
            if(p.getWorld() != loc.getWorld()){
                if(!multiWorldTeleport){
                    p.sendMessage(ChatUtil.fixColor(ZSOCraftPartyHomes.instance.getConfig().getString("messages.multi-world-teleport")));
                    return false;
                }
            }

            Location belowLoc = loc.subtract(new Vector(0, 1, 0));
            List<String> _blocksBlacklist = ZSOCraftPartyHomes.instance.getConfig().getStringList("blocks-blacklist");
            String[] blocksBlacklist = _blocksBlacklist.toArray(new String[0]);

            if(MaterialUtil.isMaterialBlacklisted(belowLoc.getBlock().getType(), blocksBlacklist)){
                p.sendMessage(ChatUtil.fixColor(ZSOCraftPartyHomes.instance.getConfig().getString("messages.home-blacklisted-block")));
                return false;
            }

            p.teleport(loc.add(new Vector(0, 1, 0)), PlayerTeleportEvent.TeleportCause.COMMAND);
            p.sendMessage(ChatUtil.fixColor(ZSOCraftPartyHomes.instance.getConfig().getString("messages.teleport-successful")));
            return true;
        }
        return false;
    }
}
