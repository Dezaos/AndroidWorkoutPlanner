package com.example.mikkel.workoutplanner.utils;

import android.content.DialogInterface;

import com.example.mikkel.workoutplanner.data.Database.ExecuteRoutine;

import java.util.HashMap;

/**
 * Click listener that contains a hashmap with string as key and ExecuteRoutine as value
 */
public class OnClickListenerWithExeRoutineHashmap implements DialogInterface.OnClickListener {

    private HashMap<String,ExecuteRoutine> routines = new HashMap<>();

    public HashMap<String, ExecuteRoutine> getRoutines() {
        return routines;
    }

    public OnClickListenerWithExeRoutineHashmap(HashMap<String, ExecuteRoutine> routines) {
        this.routines = routines;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
