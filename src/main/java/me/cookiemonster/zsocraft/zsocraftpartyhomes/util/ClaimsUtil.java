package me.cookiemonster.zsocraft.zsocraftpartyhomes.util;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.Location;

import java.util.UUID;

public class ClaimsUtil {
    //changing isPlayerInHis claim to doesClaimBelongsToUUID will enable to check offline players for any location in the world.
    public static boolean doesClaimBelongsToUUID(UUID uuid, Location loc) {
        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(uuid);

        for (Claim claim : playerData.getClaims()) {
            if(claim.isNear(loc, 0)) return true;
        }
        return false;
    }
}
