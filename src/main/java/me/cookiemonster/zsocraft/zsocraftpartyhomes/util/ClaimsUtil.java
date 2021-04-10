package me.cookiemonster.zsocraft.zsocraftpartyhomes.util;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ClaimsUtil {
    public static boolean isPlayerInHisClaim(Player p) {
        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(p.getUniqueId());
        Location playerLocation = p.getLocation();

        for (Claim claim : playerData.getClaims()) {
            if(claim.isNear(playerLocation, 0)) return true;
        }
        return false;
    }
}
