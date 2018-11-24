package com.example.mikkel.workoutplanner.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mikkel.workoutplanner.R;

public class ExerciseHolder extends RecyclerView.ViewHolder
{
    public TextView name;
    public TextView mucle;
    public TextView firstElment;
    public TextView firstHint;
    public TextView secondElment;
    public TextView secondHint;
    public TextView thirdElment;
    public TextView thirdtHint;
    public ImageButton removeButton;

    public ExerciseHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.RoutineItemName);
        mucle = itemView.findViewById(R.id.routineMuscle);
        firstElment = itemView.findViewById(R.id.firstExerciseElement);
        firstHint = itemView.findViewById(R.id.FirstExerciseHint);
        secondElment = itemView.findViewById(R.id.secondExerciseElement);
        secondHint = itemView.findViewById(R.id.SecondExerciseHint);
        thirdElment = itemView.findViewById(R.id.thirdExerciseElement);
        thirdtHint = itemView.findViewById(R.id.ThirdExerciseHint);
        removeButton = itemView.findViewById(R.id.ExerciseRemoveButtton);
    }
}
