package me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.tabcompletionvalidators;

import me.cookiemonster.zsocraft.zsocraftpartyhomes.util.PartyUtil;
import org.bukkit.entity.Player;

public class SethomeValidator implements Validator{
    @Override
    public boolean isValid(Player p) {
        return PartyUtil.isPartyLeader(p);
    }
}
