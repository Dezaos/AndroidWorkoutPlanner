package com.example.mikkel.workoutplanner.utils;

/**
 * This class is to create and get paths for the firebase database
 */
public class PathUtils
{
    public static String getDatePath(int year, int month, int day)
    {
        return String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
    }

    public static String[] getDateParts(String path)
    {
        return path.split("-");
    }

    public static String getYear(String path)
    {
        return getDateParts(path)[0];
    }

    public static String getMonth(String path)
    {
        return getDateParts(path)[1];
    }

    public static String getDay(String path)
    {
        return getDateParts(path)[2];
    }

}
