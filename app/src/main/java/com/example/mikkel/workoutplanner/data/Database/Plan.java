package com.example.mikkel.workoutplanner.data.Database;

import com.google.firebase.database.Exclude;

public class Plan
{
    //Fields
    private String name;

    @Exclude
    private String uId;

    //Properties
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

    //Contructors
    public Plan() {
    }

    public Plan(String name, String uId) {
        this.name = name;
        this.uId = uId;
    }


}
