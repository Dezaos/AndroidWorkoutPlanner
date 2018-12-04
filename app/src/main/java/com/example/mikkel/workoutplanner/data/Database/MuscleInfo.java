package com.example.mikkel.workoutplanner.data.Database;

import java.util.ArrayList;

public class MuscleInfo
{
    private String muscle;
    private ArrayList<String> exercises = new ArrayList<>();

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String name) {
        this.muscle = name;
    }

    public ArrayList<String> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<String> exercises) {
        this.exercises = exercises;
    }

    public MuscleInfo() {
    }

    public MuscleInfo(String musle, String firstExercisesUid) {
        this.muscle = musle;
        exercises.add(firstExercisesUid);
    }

    public void addExercise(String exerciseUid){
        exercises.add(exerciseUid);
    }

    public void  removeExercise(String uId)
    {
        int index = -1;
        for (int i = 0; i < exercises.size(); i++) {
            if(exercises.get(i).equals(uId))
            {
                index = i;
                break;
            }
        }

        if(index != -1)
            exercises.remove(index);
    }
}
