package com.example.mikkel.workoutplanner.data.Database;

import com.example.mikkel.workoutplanner.Enums.ExerciseType;
import com.google.firebase.database.Exclude;

public class Exercise extends FirebaseData
{
    //Fields
    private String name;
    private String muscle;
    private ExerciseType type;
    private int sets;
    private int reps;
    private float kg;
    private float time;
    private float km;

    @Exclude
    private String routineUId;

    //Properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public ExerciseType getType() {
        return type;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public String getRoutineUId() {
        return routineUId;
    }

    public void setRoutineUId(String routineUId) {
        this.routineUId = routineUId;
    }

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

    public float getKm() {
        return km;
    }

    public void setKm(float km) {
        this.km = km;
    }

    //Constructor
    public Exercise() {
    }

    public Exercise(String name, String muscle, ExerciseType type) {
        this.name = name;
        this.muscle = muscle;
        this.type = type;
    }

    public boolean valid()
    {
        boolean sharedValuesValid = name != null && name != "" &&
                muscle != null && muscle != "";

        if(type == ExerciseType.Weight)
        {
            return sharedValuesValid;

        }
        else if(type == ExerciseType.Time)
        {
            return sharedValuesValid;

        }
        return false;
    }

    public boolean weigthValid()
    {
        return getSets() > 0 && getReps() > 0;
    }

    public boolean timeValid()
    {
        return getTime() > 0 || getKm() > 0;
    }


}
