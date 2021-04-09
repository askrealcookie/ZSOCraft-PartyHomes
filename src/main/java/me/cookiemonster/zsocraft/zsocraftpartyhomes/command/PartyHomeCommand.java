package me.cookiemonster.zsocraft.zsocraftpartyhomes.command;

import com.gmail.nossr50.api.PartyAPI;
import com.sun.org.apache.xpath.internal.operations.Bool;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.ZSOCraftPartyHomes;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.ArrayUtil;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.ChatUtil;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.DataUtil;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.MaterialUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyHomeCommand implements CommandExecutor {

    //instance
    private final ZSOCraftPartyHomes instance = ZSOCraftPartyHomes.getInstance();

    //cache config
    private final FileConfiguration config = instance.getConfig();
    //teleport delay in seconds
    final int TELEPORT_DELAY = config.getInt("teleport-delay");

    //players that are in teleport state
    ArrayList<UUID> playersInTeleportState = new ArrayList<>();
    
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
                p.sendMessage(ChatUtil.fixColor(config.getString("messages.not-in-party")));
                return false;
            }

            //get player party
            String playerParty = PartyAPI.getPartyName(p);

            DataUtil dataUtil = new DataUtil(p);
            if(!dataUtil.hasPath(playerParty + ".home.location")){
                p.sendMessage(ChatUtil.fixColor(config.getString("messages.no-home-set")));
                return false;
            }
            Location loc = dataUtil.getLocation(playerParty + ".home.location");

            boolean multiWorldTeleport = config.getBoolean("allow-multi-world-teleport");
            if(p.getWorld() != loc.getWorld()){
                if(!multiWorldTeleport){
                    p.sendMessage(ChatUtil.fixColor(config.getString("messages.multi-world-teleport")));
                    return false;
                }
            }

            Location belowLoc = loc.getBlock().getRelative(BlockFace.DOWN).getLocation();
            List<String> _blocksBlacklist = config.getStringList("blocks-blacklist");
            String[] blocksBlacklist = _blocksBlacklist.toArray(new String[0]);

            if(MaterialUtil.isMaterialBlacklisted(belowLoc.getBlock().getType(), blocksBlacklist)){
                p.sendMessage(ChatUtil.fixColor(config.getString("messages.home-blacklisted-block")));
                return false;
            }

            Material[] allowedBlocks = { Material.AIR, Material.CAVE_AIR, Material.VOID_AIR, Material.LADDER, Material.SNOW };

            Location blockAtEyePos = loc.getBlock().getRelative(BlockFace.UP).getLocation();

            if(!ArrayUtil.contains(allowedBlocks, loc.getBlock().getType()) || !ArrayUtil.contains(allowedBlocks, blockAtEyePos.getBlock().getType())){
                p.sendMessage(ChatUtil.fixColor(config.getString("messages.home-teleport-in-block")));
                return false;
            }

            Location playerLoc = p.getLocation();

            //delay bypass
            if((config.getBoolean("enable-admin-delay-bypass") && p.hasPermission("PartyHomes.delay.bypass")) || config.getInt("teleport-delay") == 0){
                p.teleport(loc, PlayerTeleportEvent.TeleportCause.COMMAND);
                p.sendMessage(ChatUtil.fixColor(config.getString("messages.teleport-successful")));
                return true;
            }

            UUID uuid = p.getUniqueId();

            if(playersInTeleportState.contains(uuid)) return false;

            //delay for sad bois without perms
            playersInTeleportState.add(uuid);
            new BukkitRunnable(){
                int delay = TELEPORT_DELAY;
                public void run(){
                    if(playerLoc.distance(p.getLocation()) > 0.5d){
                        p.sendMessage(ChatUtil.fixColor(config.getString("messages.teleport-move")));
                        playersInTeleportState.remove(uuid);
                        cancel();
                        return;
                    }
                    if(delay <= 0){
                        //additional check, I may be paranoic, but someone could place blocks as soon someone types command
                        if(!ArrayUtil.contains(allowedBlocks, loc.getBlock().getType()) || !ArrayUtil.contains(allowedBlocks, blockAtEyePos.getBlock().getType())){
                            p.sendMessage(ChatUtil.fixColor(config.getString("messages.home-teleport-in-block")));
                            playersInTeleportState.remove(uuid);
                            cancel();
                            return;
                        }
                        p.teleport(loc, PlayerTeleportEvent.TeleportCause.COMMAND);
                        p.sendMessage(ChatUtil.fixColor(config.getString("messages.teleport-successful")));
                        playersInTeleportState.remove(uuid);
                        cancel();
                        return;
                    }
                    p.sendMessage(ChatUtil.fixColor(config.getString("messages.teleport-in-progress").replace("%time%", String.valueOf(delay))));
                    delay--;
                }
            }.runTaskTimer(ZSOCraftPartyHomes.getInstance(), 0, 20);

            return true;
        }
        return false;
    }
}
