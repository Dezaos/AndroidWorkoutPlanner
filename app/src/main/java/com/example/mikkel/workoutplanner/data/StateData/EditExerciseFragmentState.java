package com.example.mikkel.workoutplanner.data.StateData;

import com.example.mikkel.workoutplanner.data.Database.Exercise;

public class EditExerciseFragmentState extends StateData
{
    private String routineUId;
    private Exercise currentExercise;
    private int savedMenu;

    public int getSavedMenu() {
        return savedMenu;
    }

    public void setSavedMenu(int savedMenu) {
        this.savedMenu = savedMenu;
    }

    public Exercise getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentExercise(Exercise currentExercise) {
        this.currentExercise = currentExercise;
    }


    public String getRoutineUId() {
        return routineUId;
    }

    public void setRoutineUId(String routineUId) {
        this.routineUId = routineUId;
    }
}
