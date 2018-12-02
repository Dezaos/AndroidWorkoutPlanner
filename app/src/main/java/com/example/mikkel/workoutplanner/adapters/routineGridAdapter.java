package com.example.mikkel.workoutplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.utils.MuscleInfo;
import com.example.mikkel.workoutplanner.viewholders.RoutineGridElementHolder;

import java.util.ArrayList;

public class routineGridAdapter extends RecyclerView.Adapter<RoutineGridElementHolder> {

    private LayoutInflater inflater;
    private ArrayList<MuscleInfo> data = new ArrayList<>();
    private int maxTitleLenght = 9;

    public routineGridAdapter(Context context, ArrayList<MuscleInfo> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RoutineGridElementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.routine_grid_element,parent,false);
        return new RoutineGridElementHolder(view) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineGridElementHolder holder, int position) {
        MuscleInfo info = data.get(position);

        String title = info.getMuscle();
//        if(title.length() > maxTitleLenght)
//            title = title.substring(0,maxTitleLenght) + "...";
        holder.title.setText(title);
        holder.muscleAmount.setText(String.valueOf(info.getExercises().size()));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
