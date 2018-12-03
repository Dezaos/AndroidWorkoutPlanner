package com.example.mikkel.workoutplanner.data.Database;

import com.example.mikkel.workoutplanner.Enums.ExerciseType;
import com.example.mikkel.workoutplanner.singletons.DataManager;

import java.util.ArrayList;

public class ExecuteRoutine extends FirebaseData
{
    //Fields
    private String name;
    private String uId;
    private ArrayList<ExecuteExercise> exercises = new ArrayList<>();

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

    @Override
    public String getuId() {
        return uId;
    }

    @Override
    public void setuId(String uId) {
        this.uId = uId;
    }

    public ExecuteRoutine convert(Routine routine)
    {
        name = routine.getName();
        ArrayList<Exercise> exercises = DataManager.getInstance().getExercises().get(routine.getuId());

        for (int i = 0; i < exercises.size(); i++) {
            Exercise exercise = exercises.get(i);
            ExecuteExercise newExercise = new ExecuteExercise(exercise.getName(),exercise.getType(),exercise.getMuscle());

            int size = exercise.getType() == ExerciseType.Weight ? exercise.getSets() : exercise.getReps();

            for (int j = 0; j < size; j++) {

                if(exercise.getType() == ExerciseType.Weight)
                {
                    newExercise.getElements().add(new ExetureExerciseElement(
                            exercise.getSets(),exercise.getTime(),0,0));
                }
                else
                {
                    newExercise.getElements().add(new ExetureExerciseElement(
                            exercise.getReps(),exercise.getTime(),0,0));
                }

            }
            this.exercises.add(newExercise);
        }
        return this;
    }
}
