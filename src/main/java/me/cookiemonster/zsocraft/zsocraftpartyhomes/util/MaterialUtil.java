package me.cookiemonster.zsocraft.zsocraftpartyhomes.util;

import org.bukkit.Material;

public class MaterialUtil {
    public static boolean isMaterialBlacklisted(Material mat, String[] blacklistedMaterials){
        for (String blacklistedMaterial : blacklistedMaterials) {
            Material blacklistedMat = Material.valueOf(blacklistedMaterial.toUpperCase());
            if (mat == blacklistedMat) {
                return true;
            }
        }
        return false;
    }
}
