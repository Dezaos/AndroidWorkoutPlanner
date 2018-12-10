package com.example.mikkel.workoutplanner.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mikkel.workoutplanner.R;

//Used for recyclerviews
public class ExecuteExerciseHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView tip;
    public RecyclerView recyclerView;

    public ExecuteExerciseHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.executeExerciseTitle);
        tip = itemView.findViewById(R.id.executeExerciseTip);
        recyclerView = itemView.findViewById(R.id.executeExercisesRecyclerView);
    }
}
