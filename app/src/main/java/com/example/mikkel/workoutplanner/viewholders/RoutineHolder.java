package com.example.mikkel.workoutplanner.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mikkel.workoutplanner.R;

//Used for recyclerviews
public class RoutineHolder extends RecyclerView.ViewHolder
{
    public CardView cardView;
    public TextView title;
    public RecyclerView muscles;
    public ImageButton editButton;


    public RoutineHolder(View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.routineCardView);
        title = itemView.findViewById(R.id.routineTitle);
        muscles = itemView.findViewById(R.id.muclesRecyclerView);
        editButton = itemView.findViewById(R.id.editButton);
    }
}
