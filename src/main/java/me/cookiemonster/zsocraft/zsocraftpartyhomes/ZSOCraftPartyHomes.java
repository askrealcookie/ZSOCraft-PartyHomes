package me.cookiemonster.zsocraft.zsocraftpartyhomes;

import me.cookiemonster.zsocraft.zsocraftpartyhomes.command.PartyHomeCommand;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.command.PartySetHomeCommand;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.listener.PlayerChatListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZSOCraftPartyHomes extends JavaPlugin {

    private static ZSOCraftPartyHomes instance;

    @Override
    public void onEnable() {
        instance = this;

        RegisterCommands();
        RegisterEvents();

        saveDefaultConfig();
    }

    public static ZSOCraftPartyHomes getInstance(){
        return instance;
    }

    private void RegisterCommands() {
        getCommand("partyhome").setExecutor(new PartyHomeCommand());
        getCommand("partysethome").setExecutor(new PartySetHomeCommand());
    }

    private void RegisterEvents(){
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
    }


    @Override
    public void onDisable() {
    }
}
