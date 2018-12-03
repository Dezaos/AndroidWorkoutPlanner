package com.example.mikkel.workoutplanner.data.Database;

import com.example.mikkel.workoutplanner.Enums.ExerciseType;

import java.util.ArrayList;

public class ExecuteExercise extends FirebaseData
{
    private String name;
    private ExerciseType type;
    private String muscle;

    ArrayList<ExetureExerciseElement> elements = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExerciseType getType() {
        return type;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public ArrayList<ExetureExerciseElement> getElements() {
        return elements;
    }

    public void setElements(ArrayList<ExetureExerciseElement> elements) {
        this.elements = elements;
    }

    public ExecuteExercise() {
    }

    public ExecuteExercise(String name, ExerciseType type, String muscle) {
        this.name = name;
        this.type = type;
        this.muscle = muscle;
    }
}
