package com.example.mikkel.workoutplanner.utils;

import java.util.List;

public class ListUtils
{
    /**
     * Get index from element by find it with equal
     * @param type
     * @param list
     * @param ob
     * @param <T>
     * @return
     */
    public static <T> int indexOfWithEquals(Class<T> type, List<T> list, Object ob)
    {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).equals(ob))
                return i;
        }
        return -1;
    }
}
