package me.cookiemonster.zsocraft.zsocraftpartyhomes;

import me.cookiemonster.zsocraft.zsocraftpartyhomes.command.PartyHomeCommand;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.command.PartySetHomeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZSOCraftPartyHomes extends JavaPlugin {

    public static ZSOCraftPartyHomes instance;

    @Override
    public void onEnable() {
        instance = this;
        RegisterCommands();
        saveDefaultConfig();
    }

    private void RegisterCommands() {
        getCommand("partyhome").setExecutor(new PartyHomeCommand());
        getCommand("partysethome").setExecutor(new PartySetHomeCommand());
    }


    @Override
    public void onDisable() {
    }
}
