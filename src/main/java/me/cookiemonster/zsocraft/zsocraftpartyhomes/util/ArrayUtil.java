package me.cookiemonster.zsocraft.zsocraftpartyhomes.util;

import java.util.List;
import java.util.ListIterator;

public class ArrayUtil {
    public static boolean contains(Object[] objects, Object object){
        for (int i = 0; i < objects.length; i++){
            if(objects[i] == object) return true;
        }
        return false;
    }
    public static void replaceToLowerCase(List<String> strings)
    {
        ListIterator<String> iterator = strings.listIterator();
        while (iterator.hasNext())
        {
            iterator.set(iterator.next().toLowerCase());
        }
    }
}
