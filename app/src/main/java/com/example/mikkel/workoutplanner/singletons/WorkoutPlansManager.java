package com.example.mikkel.workoutplanner.singletons;

import com.example.mikkel.workoutplanner.data.Exercise;

import java.util.ArrayList;

public class WorkoutPlansManager {
    private static final WorkoutPlansManager ourInstance = new WorkoutPlansManager();

    public static WorkoutPlansManager getInstance() {
        return ourInstance;
    }

    private ArrayList<Exercise> exercises = new ArrayList<>();

    private WorkoutPlansManager() {
        //Remove this, only for test purpose
        exercises.add(new Exercise("Leg Day"));
        exercises.add(new Exercise("Guns!!"));
        exercises.add(new Exercise("Cardio"));
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void addWorkoutPlan(Exercise plan)
    {
        exercises.add(plan);
    }
}


