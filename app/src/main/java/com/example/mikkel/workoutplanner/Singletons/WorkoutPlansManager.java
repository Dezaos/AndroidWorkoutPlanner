package com.example.mikkel.workoutplanner.Singletons;

import com.example.mikkel.workoutplanner.data.WorkoutPlan;

import java.util.ArrayList;

public class WorkoutPlansManager {
    private static final WorkoutPlansManager ourInstance = new WorkoutPlansManager();

    public static WorkoutPlansManager getInstance() {
        return ourInstance;
    }

    private ArrayList<WorkoutPlan> workoutPlans = new ArrayList<>();

    private WorkoutPlansManager() {
        //Remove this, only for test purpose
        workoutPlans.add(new WorkoutPlan("Leg Day"));
        workoutPlans.add(new WorkoutPlan("Guns!!"));
        workoutPlans.add(new WorkoutPlan("Cardio"));
    }

    public ArrayList<WorkoutPlan> getWorkoutPlans() {
        return workoutPlans;
    }

    public void addWorkoutPlan(WorkoutPlan plan)
    {
        workoutPlans.add(plan);
    }
}


