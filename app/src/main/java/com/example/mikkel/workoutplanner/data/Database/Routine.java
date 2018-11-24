package com.example.mikkel.workoutplanner.data.Database;

public class Routine extends FirebaseData
{
    //Fields
    private String name;

    //Properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Contructors
    public Routine() {
    }

    public Routine(String name, String uId) {
        this.name = name;
        setuId(this.uId);
    }

    @Override
    public boolean equals(Object obj) {


        return super.equals(obj);
    }


}
