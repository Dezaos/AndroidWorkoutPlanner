package com.example.mikkel.workoutplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.workoutplanner.R;
import com.example.mikkel.workoutplanner.utils.EventHandler;
import com.example.mikkel.workoutplanner.utils.MuscleInfo;
import com.example.mikkel.workoutplanner.viewholders.RoutineGridElementHolder;

import java.util.ArrayList;

public class RoutineGridAdapter extends RecyclerView.Adapter<RoutineGridElementHolder> {

    private LayoutInflater inflater;
    private ArrayList<MuscleInfo> data = new ArrayList<>();
    private EventHandler onClick = new EventHandler();
    private String routineUid;

    public EventHandler getOnClick() {
        return onClick;
    }

    public RoutineGridAdapter(Context context, ArrayList<MuscleInfo> data, String routineUid) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.routineUid = routineUid;
    }

    @NonNull
    @Override
    public RoutineGridElementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.routine_grid_element,parent,false);

        view.findViewById(R.id.gridElement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.notifyAllListeners(this, routineUid);
            }
        });

        //view.findViewById(R.id.horizontalGridElement).setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        onClick.notifyAllListeners(this, routineUid);
        //    }
        //});

        return new RoutineGridElementHolder(view) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineGridElementHolder holder, int position) {
        MuscleInfo info = data.get(position);

        String title = info.getMuscle();
        holder.title.setText(title);
        holder.muscleAmount.setText(String.valueOf(info.getExercises().size()));

        //holder.title.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        onClick.notifyAllListeners(this, routineUid);
        //
        //    }
        //});
        //
        //holder.muscleAmount.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        onClick.notifyAllListeners(this, routineUid);
        //
        //    }
        //});

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
