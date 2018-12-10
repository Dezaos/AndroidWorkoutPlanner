package com.example.mikkel.workoutplanner.data.Database;

import com.example.mikkel.workoutplanner.Enums.ExerciseType;

import java.util.ArrayList;

public class ExecuteExercise extends FirebaseData
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

    //Properties
    private ArrayList<ExecuteExerciseElement> elements = new ArrayList<>();

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

    public ArrayList<ExecuteExerciseElement> getElements() {
        return elements;
    }

    public void setElements(ArrayList<ExecuteExerciseElement> elements) {
        this.elements = elements;
    }

    //Contructors
    public ExecuteExercise() {
    }


    public ExecuteExercise(String name, String muscle, ExerciseType type, int sets, int reps, float kg, float time, float km) {
        this.name = name;
        this.muscle = muscle;
        this.type = type;
        this.sets = sets;
        this.reps = reps;
        this.kg = kg;
        this.time = time;
        this.km = km;
    }
}
