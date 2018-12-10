package com.example.mikkel.workoutplanner.utils;

/**
 * Used to store name and uId for a tab adapter
 */
public class TabInfo
{
    private String name;
    private String uId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public TabInfo(String name, String uId) {
        this.name = name;
        this.uId = uId;
    }
}
