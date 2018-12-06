package com.example.mikkel.workoutplanner.utils;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ValueEventListenerWithParameter implements ValueEventListener {

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ValueEventListenerWithParameter(Object data) {
        this.data = data;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
