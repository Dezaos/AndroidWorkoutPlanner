package com.example.mikkel.workoutplanner.utils;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtils
{
    /**
     * Find and remove a object in a collection
     * @param collection
     * @param ob
     * @return
     */
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

    /**
     * Remove a object that is equal t another object
     * @param collection
     * @param ob
     * @return
     */
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

    /**
     * Find a object that is equal to another object
     * @param type
     * @param collection
     * @param ob
     * @param <T>
     * @return
     */
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
