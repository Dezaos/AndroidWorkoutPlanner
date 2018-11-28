package com.example.mikkel.workoutplanner.utils;

public class MuscleInfo
{
    private String name;
    private int amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public MuscleInfo(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }
}
