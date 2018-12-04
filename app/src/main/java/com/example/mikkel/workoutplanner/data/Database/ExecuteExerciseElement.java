package com.example.mikkel.workoutplanner.data.Database;

public class ExecuteExerciseElement extends FirebaseData
{
    private int maxReps;
    private float maxTime;
    private int currentReps;
    private float currentTime;

    public int getMaxReps() {
        return maxReps;
    }

    public void setMaxReps(int maxReps) {
        this.maxReps = maxReps;
    }

    public float getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(float maxTime) {
        this.maxTime = maxTime;
    }

    public int getCurrentReps() {
        return currentReps;
    }

    public void setCurrentReps(int currentReps) {
        this.currentReps = currentReps;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }

    public ExecuteExerciseElement(int maxReps, float maxTime, int currentReps, float currentTime) {
        this.maxReps = maxReps;
        this.maxTime = maxTime;
        this.currentReps = currentReps;
        this.currentTime = currentTime;
    }

    public ExecuteExerciseElement() {
    }
}
