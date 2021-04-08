package me.cookiemonster.zsocraft.zsocraftpartyhomes.util;

public class ArrayUtil {
    public static boolean contains(Object[] objects, Object object){
        for (int i = 0; i < objects.length; i++){
            if(objects[i] == object) return true;
        }
        return false;
    }
}
