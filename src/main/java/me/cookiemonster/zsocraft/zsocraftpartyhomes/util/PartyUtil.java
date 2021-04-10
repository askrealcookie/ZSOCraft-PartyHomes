package me.cookiemonster.zsocraft.zsocraftpartyhomes.util;

import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.party.PartyManager;
import org.bukkit.entity.Player;

public class PartyUtil {
    public static boolean isPartyLeader(Player p){
        Party party = PartyManager.getParty(p);
        return party.getLeader().getUniqueId().equals(p.getUniqueId());
    }
}
