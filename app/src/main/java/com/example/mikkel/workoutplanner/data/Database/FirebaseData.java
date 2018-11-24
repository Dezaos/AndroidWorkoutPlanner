package com.example.mikkel.workoutplanner.data.Database;

import com.google.firebase.database.DataSnapshot;

public class FirebaseData
{
    String uId;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

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
