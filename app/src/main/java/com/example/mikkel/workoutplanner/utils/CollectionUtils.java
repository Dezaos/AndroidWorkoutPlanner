package com.example.mikkel.workoutplanner.utils;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtils
{
    public static boolean findAndRemove(Collection collection, Object ob)
    {
        if(ob != null)
        {
            Iterator iterator = collection.iterator();
            while (iterator.hasNext())
            {
                Object currentObject = iterator.next();
                if(currentObject == ob)
                {
                    iterator.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean removeByEquals(Collection collection, Object ob)
    {
        if(ob != null)
        {
            Iterator iterator = collection.iterator();
            while (iterator.hasNext())
            {
                Object currentObject = iterator.next();
                if(currentObject.equals(ob))
                {
                    iterator.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> T findByEquals(Class<T> type, Collection collection, Object ob)
    {
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext())
        {
            T value = iterator.next();
            if(value.equals(ob))
                return value;
        }
        return null;
    }
}
