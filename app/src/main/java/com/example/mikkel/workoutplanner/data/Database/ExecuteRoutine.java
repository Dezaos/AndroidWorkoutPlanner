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
    private int year;
    private int month;
    private int day;
    private int hourOfDay;
    private int minutesOfDay;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinutesOfDay() {
        return minutesOfDay;
    }

    public void setMinutesOfDay(int minutesOfDay) {
        this.minutesOfDay = minutesOfDay;
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

                    newExercise.getElements().add(new ExecuteExerciseElement(
                            exercise.getReps(),exercise.getTime(),0,0));
            }
            this.exercises.add(newExercise);
        }
        return this;
    }

    public void setDates(int year, int month, int day, int hour, int minutes)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hourOfDay = hour;
        this.minutesOfDay = minutes;
    }
}
