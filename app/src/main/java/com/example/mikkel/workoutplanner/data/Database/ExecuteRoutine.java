package com.example.mikkel.workoutplanner.data.Database;

import android.support.design.widget.Snackbar;

import com.example.mikkel.workoutplanner.Enums.ExerciseType;
import com.example.mikkel.workoutplanner.singletons.DataManager;

import java.util.ArrayList;
import java.util.Timer;

public class ExecuteRoutine extends FirebaseData
{
    //Fields
    private String name;
    private ArrayList<ExecuteExercise> exercises = new ArrayList<>();
    private ArrayList<MuscleInfo> muscleInfos = new ArrayList<>();
    private String date;

    //Properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ExecuteExercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<ExecuteExercise> exercises) {
        this.exercises = exercises;
    }

    public ArrayList<MuscleInfo> getMuscleInfos() {
        return muscleInfos;
    }

    public void setMuscleInfos(ArrayList<MuscleInfo> muscleInfos) {
        this.muscleInfos = muscleInfos;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ExecuteRoutine() {
    }

    public ExecuteRoutine convert(Routine routine)
    {
        name = routine.getName();

        ArrayList<Exercise> exercises = DataManager.getInstance().getExercises().get(routine.getuId());

        for (int i = 0; i < exercises.size(); i++) {
            Exercise exercise = exercises.get(i);

            ExecuteExercise newExercise = new ExecuteExercise(
                    exercise.getName(),exercise.getMuscle(),exercise.getType(),
                    exercise.getSets(),exercise.getReps(),exercise.getKg(),exercise.getTime(),exercise.getKm());

            int size = exercise.getType() == ExerciseType.Weight ? exercise.getSets() : exercise.getReps();

            for (int j = 0; j < size; j++) {

                if(exercise.getType() == ExerciseType.Weight)
                {
                    newExercise.getElements().add(new ExecuteExerciseElement(
                            exercise.getSets(),exercise.getTime(),0,0));
                }
                else
                {
                    newExercise.getElements().add(new ExecuteExerciseElement(
                            exercise.getReps(),exercise.getTime(),0,0));
                }

            }
            this.exercises.add(newExercise);
        }
        return this;
    }
}
