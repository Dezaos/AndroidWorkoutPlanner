package com.example.mikkel.workoutplanner.data.Database;

import com.google.firebase.database.DataSnapshot;

//This class is super class for data that is supposed to be stored in Firebase realtime database
public class FirebaseData
{
    //Fields
    String uId;

    //Properties
    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    //This method builds the data and sets the uId value in the data class
    public static <T extends FirebaseData> T build(Class<T> type, DataSnapshot snapshot)
    {
        if(snapshot != null)
        {
            T value = snapshot.getValue(type);
            if(value != null)
            {
                value.setuId(snapshot.getKey());
                return value;
            }
        }
        return null;
    }
}
