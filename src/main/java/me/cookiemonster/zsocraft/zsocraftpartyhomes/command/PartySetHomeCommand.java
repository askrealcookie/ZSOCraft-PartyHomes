package me.cookiemonster.zsocraft.zsocraftpartyhomes.command;

import com.gmail.nossr50.api.PartyAPI;
import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.party.PartyManager;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.ZSOCraftPartyHomes;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.ChatUtil;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.ClaimsUtil;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.DataUtil;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.MaterialUtil;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

public class PartySetHomeCommand implements CommandExecutor {
    
    //instance
    private final ZSOCraftPartyHomes instance = ZSOCraftPartyHomes.getInstance();
    //cache config
    private final FileConfiguration config = instance.getConfig();

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

            //player is not in his claim
            if(!ClaimsUtil.doesClaimBelongsToUUID(p.getUniqueId(), p.getLocation())){
                p.sendMessage(ChatUtil.fixColor(config.getString("messages.you-are-not-in-your-claim")));
                return false;
            }

            //hacky workaround to avoid pixel perfect tp
            Location loc = p.getLocation().getBlock().getLocation();
            loc.add(new Vector(0.5, 0, 0.5));

            //check if player is standing on safe block
            Location belowLoc = loc.getBlock().getRelative(BlockFace.DOWN).getLocation();
            List<String> _blocksBlacklist = config.getStringList("blocks-blacklist");
            String[] blocksBlacklist = _blocksBlacklist.toArray(new String[0]);

            if(MaterialUtil.isMaterialBlacklisted(belowLoc.getBlock().getType(), blocksBlacklist)){
                p.sendMessage(ChatUtil.fixColor(config.getString("messages.sethome-blacklisted-block")));
                return false;
            }

            DataUtil dataUtil = new DataUtil(p);
            dataUtil.setLocation(playerParty.getName() + ".home.location", loc);
            p.sendMessage(ChatUtil.fixColor(config.getString("messages.sethome-successful")));
            return true;
        }
        return false;
    }
}
