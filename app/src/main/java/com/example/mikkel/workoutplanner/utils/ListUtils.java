package com.example.mikkel.workoutplanner.utils;

import java.util.List;

public class ListUtils
{
    public static <T> int indexOfWithEquals(Class<T> type, List<T> list, Object ob)
    {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).equals(ob))
                return i;
        }
        return -1;
    }
}
