package com.example.mikkel.workoutplanner.data.Database;

import com.google.firebase.database.Exclude;

public class Set
{
    //Fields
    private int sets;
    private int reps;
    private float kg;
    private float time;

    //Properties
    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public float getKg() {
        return kg;
    }

    public void setKg(float kg) {
        this.kg = kg;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }


    //Contructor
    public Set(int sets, int reps, float kg, float time, String planUId) {
        this.sets = sets;
        this.reps = reps;
        this.kg = kg;
        this.time = time;
    }
}
