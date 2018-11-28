package com.example.mikkel.workoutplanner.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mikkel.workoutplanner.R;

public class RoutineGridElementHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView muscleAmount;

    public RoutineGridElementHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.muscleTitleTextView);
        muscleAmount = itemView.findViewById(R.id.muscleAmount);
    }
}
