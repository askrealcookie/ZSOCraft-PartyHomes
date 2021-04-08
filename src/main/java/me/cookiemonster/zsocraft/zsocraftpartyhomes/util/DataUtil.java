package me.cookiemonster.zsocraft.zsocraftpartyhomes.util;

import com.gmail.nossr50.api.PartyAPI;
import me.cookiemonster.zsocraft.zsocraftpartyhomes.ZSOCraftPartyHomes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

public final class DataUtil {
    private String party;
    private FileConfiguration data;
    private File file;

    private Logger log = ZSOCraftPartyHomes.instance.getLogger();

    public DataUtil(Player player){
        if(!PartyAPI.inParty(player)) return;
        this.party = PartyAPI.getPartyName(player);
        init();
    }

    public String getParty() { return party; }
    public String getLeaderName() { return PartyAPI.getPartyLeader(party); }

    public FileConfiguration getData() { return data; }
    public File getFile() { return file; }

    public boolean exists() { return file.exists(); }

    private synchronized void init() {
        String path = ZSOCraftPartyHomes.instance.getDataFolder() + File.separator + "partydata" + File.separator + party + ".yml";
        this.file = new File(path);

        if(!exists()){
            // debug message
            log.info("No data file found, setting up one now! " + party);
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch(IOException ex) {
                log.severe(ex.getMessage());
            }
            this.data = YamlConfiguration.loadConfiguration(getFile());
            setup();
        }
        this.data = YamlConfiguration.loadConfiguration(getFile());
    }

    private void setup() {
        // set default values here, don't forget to save

        // home location shouldn't be set by default.
        data.set(party + ".leaderName", getLeaderName());
        save();
    }

    public synchronized void save(){
        try {
            data.save(file);
        } catch (IOException ex) {
            log.severe(ex.getMessage());
        }
    }

    //quick api
    public boolean hasPath(String path) { return data.contains(path); }
    public Location getLocation(String path) { if(!hasPath(path)) return null; return data.getLocation(path); }
    public void setLocation(String path, Location loc) {
        data.set(path, loc);
        save();
    }
}
