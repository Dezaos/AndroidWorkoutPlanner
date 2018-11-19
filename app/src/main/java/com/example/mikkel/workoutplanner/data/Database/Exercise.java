package com.example.mikkel.workoutplanner.data.Database;

import com.example.mikkel.workoutplanner.Enums.ExerciseType;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Exercise
{
    //Fields
    private String name;
    private String muscle;
    private ExerciseType type;
    private List<Set> sets = new ArrayList<>();

    @Exclude
    private String planUId;

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

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }


    public String getPlanUId() {
        return planUId;
    }

    public void setPlanUId(String planUId) {
        this.planUId = planUId;
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
                muscle != null && muscle != "" &&
                planUId != null && planUId != "";

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
}
