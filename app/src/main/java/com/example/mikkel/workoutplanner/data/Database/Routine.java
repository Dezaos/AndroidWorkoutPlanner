package com.example.mikkel.workoutplanner.data.Database;

//This class is used to store routine data
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

    //This overrides the equal method, to check for the routines uId instead of their instance
    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Routine)
        {
            return ((Routine)obj).getuId().equals(getuId());
        }
        return super.equals(obj);
    }


}
