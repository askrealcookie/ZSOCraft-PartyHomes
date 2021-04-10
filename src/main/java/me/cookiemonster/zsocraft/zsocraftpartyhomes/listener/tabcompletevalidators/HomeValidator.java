package me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabcompletevalidators;

import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.party.PartyManager;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HomeValidator implements Validator{
    @Override
    public boolean isValid(Player p) {
        Party party = PartyManager.getParty(p);
        return !Objects.isNull(party);
    }
}
