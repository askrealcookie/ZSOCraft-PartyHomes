package me.cookiemonster.zsocraft.zsocraftpartyhomes.util;

import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.party.PartyManager;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PartyUtil {
    public static boolean isPartyLeader(Player p){
        Party party = PartyManager.getParty(p);
        if(Objects.isNull(party)) return false;
        return party.getLeader().getUniqueId().equals(p.getUniqueId());
    }
}
