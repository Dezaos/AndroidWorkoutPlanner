package com.example.mikkel.workoutplanner.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mikkel.workoutplanner.R;

public class ExerciseHolder extends RecyclerView.ViewHolder
{
    public TextView name;

    public ExerciseHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.RoutineItemName);
    }
}
