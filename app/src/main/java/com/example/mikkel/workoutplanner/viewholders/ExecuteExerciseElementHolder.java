package com.example.mikkel.workoutplanner.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mikkel.workoutplanner.R;

//Used for recyclerviews
public class ExecuteExerciseElementHolder extends RecyclerView.ViewHolder
{
    public Button mainButton;


    public ExecuteExerciseElementHolder(View itemView) {
        super(itemView);
        mainButton = itemView.findViewById(R.id.executeExerciseElementButton);

    }
}
